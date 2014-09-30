package com.vidolima.doco;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.google.common.base.Strings;
import com.googlecode.objectify.Ref;
import com.vidolima.doco.annotation.DocumentField;
import com.vidolima.doco.annotation.DocumentId;
import com.vidolima.doco.annotation.DocumentRef;
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
     * Obtains the {@link java.lang.reflect.Field} annotated with {@link DocumentId} annotation.
     * 
     * @param classOfObj
     *            the class of the object that contains the field
     * @return the {@link java.lang.reflect.Field} that contains the {@link DocumentId} annotation
     * @exception DocumentParseException
     */
    private java.lang.reflect.Field getDocumentIdField(Class<?> classOfObj) throws DocumentParseException {
        List<java.lang.reflect.Field> result = ReflectionUtils.getAnnotatedFields(classOfObj, DocumentId.class);

        if (result.size() > 1) {
            throw new DocumentParseException("More than one occurrence of @DocumentId found in " + classOfObj);
        }

        if (result.isEmpty()) {
            throw new DocumentParseException("No @DocumentId annotation was found in " + classOfObj);
        }

        return result.get(0);
    }

    /**
     * Obtains all the {@link java.lang.reflect.Field}s annotated with the {@link DocumentId}.
     * 
     * @param classOfObj
     *            the class of the object that contains the fields to be search
     * @return all the annotated {@link java.lang.reflect.Field} that contains the {@link DocumentField} annotation.
     */
    List<java.lang.reflect.Field> getAllDocumentField(Class<?> classOfObj) {
        return ReflectionUtils.getAnnotatedFields(classOfObj, DocumentField.class);
    }

    private List<java.lang.reflect.Field> getAllRefFields(Class<?> classOfObj) {
        return ReflectionUtils.getAnnotatedFields(classOfObj, DocumentRef.class);
    }

    /**
     * Returns the {@link DocumentId} value of a given Object.
     * 
     * @param obj
     *            the object base
     * @param classOfObj
     *            the class of object
     * @return the id of the document
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private <T> T getId(Object obj, Class<?> classOfObj, Class classOfT) throws IllegalArgumentException,
        IllegalAccessException {

        java.lang.reflect.Field field = getDocumentIdField(classOfObj);
        T id = (T) ReflectionUtils.getFieldValue(field, obj, classOfT);

        if (id == null) {
            throw new DocumentParseException("No id was set to \"" + field.getName() + "\" field in " + classOfObj);
        }

        return id;
    }

    /**
     * Obtains a {@link com.google.appengine.api.search.Field} given a name, value and type.
     * 
     * @param fieldValue
     *            the value to be set to the returned field
     * @param name
     *            the name of returned field
     * @param field
     *            the {@link java.lang.reflect.Field}
     * @param obj
     *            the object base
     * 
     * @return com.google.appengine.api.search.Field
     */
    private com.google.appengine.api.search.Field getSearchNumberField(String name, java.lang.reflect.Field field,
        Object fieldValue) {

        if (fieldValue == null)
            return null;

        if (Integer.TYPE.equals(field.getType()) || Integer.class.equals(field.getType())) {
            Integer number = (Integer) fieldValue;
            return Field.newBuilder().setName(name).setNumber(number).build();
        }
        if (Long.TYPE.equals(field.getType()) || Long.class.equals(field.getType())) {
            Long number = (Long) fieldValue;
            return Field.newBuilder().setName(name).setNumber(number).build();
        }
        if (Float.TYPE.equals(field.getType()) || Float.class.equals(field.getType())) {
            Float number = (Float) fieldValue;
            return Field.newBuilder().setName(name).setNumber(number).build();
        }
        if (Double.TYPE.equals(field.getType()) || Double.class.equals(field.getType())) {
            Double number = (Double) fieldValue;
            return Field.newBuilder().setName(name).setNumber(number).build();
        }

        throw new DocumentParseException(
            "A DocumentField typed as NUMBER must be Long, Integer, Float or Double. Field '" + field.getName()
                + "' is incompatible.");
    }

    /**
     * Obtains a {@link com.google.appengine.api.search.Field} given a name, value and type.
     * 
     * @param fieldValue
     *            the value to be set to the returned field
     * @param name
     *            the name of returned field
     * @param field
     *            the {@link java.lang.reflect.Field}
     * @param obj
     *            the object base
     * @return the {@link com.google.appengine.api.search.Field}
     * @throws IllegalAccessException
     */
    private com.google.appengine.api.search.Field getSearchFieldByFieldType(String name, java.lang.reflect.Field field,
        Object obj, FieldType fieldType) throws IllegalAccessException {

        Object fieldValue = field.get(obj);

        if (FieldType.TEXT.equals(fieldType)) {
            String text = String.valueOf(fieldValue);
            return Field.newBuilder().setName(name).setText(text).build();
        }
        if (FieldType.HTML.equals(fieldType)) {
            String html = String.valueOf(fieldValue);
            return Field.newBuilder().setName(name).setHTML(html).build();
        }
        if (FieldType.ATOM.equals(fieldType)) {
            String atom = String.valueOf(fieldValue);
            return Field.newBuilder().setName(name).setAtom(atom).build();
        }
        if (FieldType.DATE.equals(fieldType)) {
            if (fieldValue != null) {
                Date date = (Date) fieldValue;
                return Field.newBuilder().setName(name).setDate(date).build();
            }
        }
        if (FieldType.GEO_POINT.equals(fieldType)) {
            if (fieldValue != null) {
                if (fieldValue instanceof GeoPt) {
                    GeoPt geoPt = (GeoPt) fieldValue;
                    GeoPoint geoPoint = new GeoPoint(geoPt.getLatitude(), geoPt.getLongitude());
                    return Field.newBuilder().setName(name).setGeoPoint(geoPoint).build();
                } else {
                    GeoPoint geoPoint = (GeoPoint) fieldValue;
                    return Field.newBuilder().setName(name).setGeoPoint(geoPoint).build();
                }
            }
        }
        if (FieldType.NUMBER.equals(fieldType))
            return getSearchNumberField(name, field, fieldValue);

        // Note: When you create a document you must specify all of its
        // attributes using the Document.Builder class method. You cannot add,
        // remove, or delete fields, nor change the identifier or any other
        // attribute once the document has been created. Date and geopoint
        // fields must be assigned a non-null value. Atom, text, HTML, and
        // number fields can be empty
        return null;
    }

    /**
     * Obtains a list of {@link com.google.appengine.api.search.Field} from {@link FieldType}.
     * 
     * @param obj
     *            the object base
     * @param classOfObj
     *            the class of object
     * @return a list of {@link com.google.appengine.api.search.Field}
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    List<com.google.appengine.api.search.Field> getAllSearchFieldsByType(String fieldNamePrefix, Object obj,
        Class<?> classOfObj, FieldType fieldType) throws IllegalArgumentException, IllegalAccessException {

        List<com.google.appengine.api.search.Field> fields = new ArrayList<Field>(0);

        for (java.lang.reflect.Field f : getAllDocumentField(classOfObj)) {

            // TODO: validate field (declaration of annotation)
            DocumentField annotation = ObjectParser.getDocumentFieldAnnotation(f);

            if (annotation.type().equals(fieldType)) {
                String name = ObjectParser.getFieldNameValue(f, annotation);
                String fullName = Strings.isNullOrEmpty(fieldNamePrefix) ? name : fieldNamePrefix + "_" + name;
                com.google.appengine.api.search.Field field = getSearchFieldByFieldType(fullName, f, obj, fieldType);
                if (field != null) {
                    fields.add(field);
                }
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
    List<com.google.appengine.api.search.Field> getAllSearchFields(String fieldNamePrefix, Object obj,
        Class<?> classOfObj) throws IllegalArgumentException, IllegalAccessException {

        List<com.google.appengine.api.search.Field> fields = new ArrayList<com.google.appengine.api.search.Field>();

        for (FieldType type : FieldType.values()) {
            for (com.google.appengine.api.search.Field f : getAllSearchFieldsByType(fieldNamePrefix, obj, classOfObj,
                type)) {
                fields.add(f);
            }
        }

        return fields;
    }

    /**
     * Parses a object to an {@link Document}.
     * 
     * @param obj
     *            the object to be parsed
     * @param documentId
     *            user defined id of document (e.g. 'Key' of a datastore entity).
     * @param typeOfObj
     *            the base class of the given object
     * @return a {@link Document}
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    Document parseDocument(Object obj, String documentId, Class<?> classOfObj) throws IllegalArgumentException,
        IllegalAccessException {
        String id = documentId;
        if (id == null) {
            java.lang.reflect.Field field = getDocumentIdField(classOfObj);
            id = String.valueOf(getId(obj, classOfObj, field.getType()));
        }

        Document.Builder builder = Document.newBuilder().setId(id);

        for (com.google.appengine.api.search.Field f : getAllFieldsForDocument("", obj, classOfObj)) {
            if (f != null) {
                builder.addField(f);
            }
        }

        return builder.build();
    }

    /**
     * Parses class for presence of @DocumentField, @DocumentRef, etc. annotations and generates {@link Field} from it.
     * 
     * @param fieldNamePrefix
     *            prefix for field names if any. Normally this should be set to empty string or null. When supplied,
     *            name of field becomes <b>prefix_</b>fieldName
     * @param obj
     *            object whcih should be used to get value of document fields.
     * @param classOfObj
     *            class of 'obj' parameter
     * @return All possible fields which are to be added to the search document including fields of Ref entity.
     */
    private List<com.google.appengine.api.search.Field> getAllFieldsForDocument(String fieldNamePrefix, Object obj,
        Class<?> classOfObj) throws IllegalArgumentException, IllegalAccessException {
        List<com.google.appengine.api.search.Field> eligibleFields = new ArrayList<>();
        // get fields annotated with @DocumentField
        for (com.google.appengine.api.search.Field f : getAllSearchFields(fieldNamePrefix, obj, classOfObj)) {
            if (f != null) {
                eligibleFields.add(f);
            }
        }
        // get fields annotated with @DocumentRef
        for (com.google.appengine.api.search.Field f : getAllSearchFieldsFromRefClass(fieldNamePrefix, obj, classOfObj)) {
            if (f != null) {
                eligibleFields.add(f);
            }
        }
        return eligibleFields;
    }

    /**
     * Creates {@link Field} for all annotated fields in the class of field annotated with {@link DocumentRef}
     */
    private List<com.google.appengine.api.search.Field> getAllSearchFieldsFromRefClass(String fieldNamePrefix,
        Object obj, Class<?> classOfObj) throws IllegalArgumentException, IllegalAccessException {
        List<com.google.appengine.api.search.Field> searchFields = new ArrayList<com.google.appengine.api.search.Field>();
        List<java.lang.reflect.Field> declaredFields = getAllRefFields(classOfObj);
        for (java.lang.reflect.Field declaredField : declaredFields) {
            Object fieldValue = declaredField.get(obj);
            // get the class from the annotation on this field (guaranteed to be annotated since we specifically asked
            // for annotated fields only)
            Class<?> refClass = declaredField.getAnnotation(DocumentRef.class).type();
            if (!(fieldValue instanceof Ref<?>)) {
                throw new IllegalStateException("Incorrect mapping found on field: " + declaredField.getName());
            }
            Ref<?> ref = (Ref<?>) fieldValue;
            String newFieldNamePrefix = Strings.isNullOrEmpty(fieldNamePrefix) ? refClass.getSimpleName()
                : fieldNamePrefix + "_" + refClass.getSimpleName();
            searchFields.addAll(getAllFieldsForDocument(newFieldNamePrefix, ref.get(), refClass));
        }
        return searchFields;
    }
}