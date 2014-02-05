package com.vidolima.doco;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.vidolima.doco.annotation.DocumentField;
import com.vidolima.doco.annotation.DocumentId;
import com.vidolima.doco.annotation.FieldType;
import com.vidolima.doco.exception.DocumentParseException;

/**
 * This class is used to create {@link Document}s instances from Objects.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since January 28, 2014
 */
final class DocumentParser {

	/**
	 * Obtains the {@link java.lang.reflect.Field} annotated with
	 * {@link DocumentId} annotation.
	 * 
	 * @param classOfObj
	 *            the class of the object that contains the field
	 * @return the {@link java.lang.reflect.Field} that contains the
	 *         {@link DocumentId} annotation
	 * @exception DocumentParseException
	 */
	private java.lang.reflect.Field getDocumentIdField(Class<?> classOfObj)
			throws DocumentParseException {

		List<java.lang.reflect.Field> result = ReflectionUtils
				.getAnnotatedFields(classOfObj, DocumentId.class);

		if (result.size() > 1) {
			throw new DocumentParseException(
					"More than one occurrence of @DocumentId found in "
							+ classOfObj);
		}

		if (result.isEmpty()) {
			throw new DocumentParseException(
					"No @DocumentId annotation was found in " + classOfObj);
		}

		return result.get(0);
	}

	/**
	 * Obtains all the {@link java.lang.reflect.Field}s annotated with the
	 * {@link DocumentId}.
	 * 
	 * @param classOfObj
	 *            the class of the object that contains the fields to be search
	 * @return all the annotated {@link java.lang.reflect.Field} that contains
	 *         the {@link DocumentField} annotation.
	 */
	List<java.lang.reflect.Field> getAllDocumentField(Class<?> classOfObj) {
		return ReflectionUtils.getAnnotatedFields(classOfObj,
				DocumentField.class);
	}

	/**
	 * Returns the {@link DocumentId} value of a given Object.
	 * 
	 * @param obj
	 * @param classOfObj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String getId(Object obj, Class<?> classOfObj)
			throws IllegalArgumentException, IllegalAccessException {

		java.lang.reflect.Field field = getDocumentIdField(classOfObj);

		String id = ReflectionUtils.getFieldValue(field, obj, String.class);

		if (id == null || (id.trim().length() == 0)) {
			throw new DocumentParseException("No id was set in "
					+ field.getName());
		}

		return id;
	}

	/**
	 * Obtains a list of {@link com.google.appengine.api.search.Field} from
	 * {@link FieldType}.
	 * 
	 * @param obj
	 *            the object base
	 * @param classOfObj
	 *            the class of object
	 * @return a list of {@link com.google.appengine.api.search.Field}
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	List<com.google.appengine.api.search.Field> getAllSearchFieldsByType(
			Object obj, Class<?> classOfObj, FieldType fieldType)
			throws IllegalArgumentException, IllegalAccessException {

		List<com.google.appengine.api.search.Field> fields = new ArrayList<Field>(
				0);

		for (java.lang.reflect.Field f : getAllDocumentField(classOfObj)) {

			// TODO: validate field (declaration of annotation)

			DocumentField annotation = ObjectParser
					.getDocumentFieldAnnotation(f);

			if (annotation.type().equals(fieldType)) {

				String name = ObjectParser.getFieldNameValue(f, annotation);

				Object fieldValue = f.get(obj);
				com.google.appengine.api.search.Field field = getSearchFieldByFieldType(
						name, fieldValue, fieldType);

				fields.add(field);
			}
		}

		return fields;
	}

	/**
	 * Obtains all fields with Doco annotations.
	 * 
	 * @param obj
	 *            the origin object
	 * @param classOfObj
	 *            the class of obj
	 * @return a list of {@link com.google.appengine.api.search.Field}
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	List<com.google.appengine.api.search.Field> getAllSearchFields(Object obj,
			Class<?> classOfObj) throws IllegalArgumentException,
			IllegalAccessException {

		List<com.google.appengine.api.search.Field> fields = new ArrayList<com.google.appengine.api.search.Field>();

		for (FieldType type : FieldType.values()) {
			for (com.google.appengine.api.search.Field f : getAllSearchFieldsByType(
					obj, classOfObj, type)) {
				fields.add(f);
			}
		}

		return fields;
	}

	/**
	 * Obtains a {@link com.google.appengine.api.search.Field} given a name,
	 * value and type.
	 * 
	 * @param fieldValue
	 *            the value to be set to the returned field
	 * @param name
	 *            the name of returned field
	 * @param fieldType
	 *            the {@link FieldType}
	 * @return the {@link com.google.appengine.api.search.Field}
	 * @throws IllegalAccessException
	 */
	private com.google.appengine.api.search.Field getSearchFieldByFieldType(
			String name, Object fieldValue, FieldType fieldType)
			throws IllegalAccessException {

		if (FieldType.TEXT.equals(fieldType)) {
			String text = (String) fieldValue;
			return Field.newBuilder().setName(name).setText(text).build();
		}
		if (FieldType.HTML.equals(fieldType)) {
			String html = (String) fieldValue;
			return Field.newBuilder().setName(name).setHTML(html).build();
		}
		if (FieldType.ATOM.equals(fieldType)) {
			String atom = (String) fieldValue;
			return Field.newBuilder().setName(name).setAtom(atom).build();
		}
		if (FieldType.DATE.equals(fieldType)) {
			if (fieldValue == null) {
				throw new NullPointerException(
						"Date and geopoint fields must be assigned a non-null value.");
			}
			Date date = (Date) fieldValue;
			return Field.newBuilder().setName(name).setDate(date).build();
		}
		if (FieldType.GEO_POINT.equals(fieldType)) {
			if (fieldValue == null) {
				throw new NullPointerException(
						"Date and geopoint fields must be assigned a non-null value.");
			}
			GeoPoint geoPoint = (GeoPoint) fieldValue;
			return Field.newBuilder().setName(name).setGeoPoint(geoPoint)
					.build();
		}
		if (FieldType.NUMBER.equals(fieldType)) {
			Double number = (Double) fieldValue;
			return Field.newBuilder().setName(name).setNumber(number).build();
		}

		// Note: When you create a document you must specify all of its
		// attributes using the Document.Builder class method. You cannot add,
		// remove, or delete fields, nor change the identifier or any other
		// attribute once the document has been created. Date and geopoint
		// fields must be assigned a non-null value. Atom, text, HTML, and
		// number fields can be empty
		return null;
	}

	/**
	 * Parses a object to an {@link Document}.
	 * 
	 * @param obj
	 *            the object to be parsed
	 * @param typeOfObj
	 *            the base class of the given object
	 * @return a {@link Document}
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	Document parseDocument(Object obj, Class<?> classOfObj)
			throws IllegalArgumentException, IllegalAccessException {

		String id = getId(obj, classOfObj);
		Document.Builder builder = Document.newBuilder().setId(id);

		for (com.google.appengine.api.search.Field f : getAllSearchFields(obj,
				classOfObj)) {
			if (f != null) {
				builder.addField(f);
			}
		}
		return builder.build();
	}
}