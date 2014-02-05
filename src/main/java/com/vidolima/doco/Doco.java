package com.vidolima.doco;

import com.google.appengine.api.search.Document;
import com.vidolima.doco.exception.DocumentParseException;
import com.vidolima.doco.exception.ObjectParseException;

/**
 * This is the main class to use Doco. Doco (Document Converter) is a
 * lightweight Java library used to converts (from and to) indexed Documents
 * provided by Search API in Google App Engine.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since January 22, 2014
 */
public final class Doco {

	/**
	 * This method converts the specified object, into its equivalent
	 * {@link Document} representation.
	 * 
	 * @param obj
	 *            the object for which {@link Document} representation is to be
	 *            created
	 * @param classOfObj
	 *            the class of the obj
	 * @return {@link Document} representation of obj
	 */
	public Document toDocument(Object obj, Class<?> classOfObj)
			throws DocumentParseException {

		if (obj == null) {
			return null;
		}

		Document document = Document.newBuilder().build();
		DocumentParser parser = new DocumentParser();

		try {
			document = parser.parseDocument(obj, classOfObj);
		} catch (IllegalArgumentException e) {
			throw new DocumentParseException("Conversion failed.", e);
		} catch (IllegalAccessException e) {
			throw new DocumentParseException("Conversion failed.", e);
		}

		return document;
	}

	/**
	 * This method converts the specified object, into its equivalent
	 * {@link Document} representation.
	 * 
	 * @param obj
	 *            the object for which {@link Document} representation is to be
	 *            created
	 * @return {@link Document} representation of obj
	 */
	public Document toDocument(Object obj) {
		return toDocument(obj, obj.getClass());
	}

	/**
	 * This method converts the specified {@link Document}, into an object T.
	 * 
	 * @param doc
	 *            the {@link Document} for which object T representation is to
	 *            be created
	 * @param classOfT
	 *            the class of T
	 * @return T
	 */
	public <T> T fromDocument(Document doc, Class<T> classOfT)
			throws ObjectParseException {

		if (doc == null) {
			return null;
		}

		T instanceOfT = null;
		ObjectParser parser = new ObjectParser();

		try {
			instanceOfT = (T) parser.parseObject(doc, classOfT);
		} catch (InstantiationException e) {
			throw new ObjectParseException("Conversion failed.", e);
		} catch (IllegalAccessException e) {
			throw new ObjectParseException("Conversion failed.", e);
		}

		return instanceOfT;
	}

}