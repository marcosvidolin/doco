package com.vidolima.doco;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;
import com.vidolima.doco.annotation.DocumentIndex;
import com.vidolima.doco.exception.AnnotationNotFoundException;
import com.vidolima.doco.exception.DocumentParseException;
import com.vidolima.doco.exception.ObjectParseException;

/**
 * This is the main class to use Doco. Doco (Document Converter) is a lightweight Java library used to converts (from
 * and to) indexed Documents provided by Search API in Google App Engine.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since January 22, 2014
 */
public final class Doco {

    /**
     * Obtains the Index.
     * 
     * @param clazz
     *            the class
     * @return the {@link Index} specified with the {@link DocumentIndex} annotation.
     */
    public Index getIndex(Class<?> clazz) {
        String indexName;
        try {
            indexName = ObjectParser.getIndexName(clazz);
        } catch (AnnotationNotFoundException e) {
            throw new AnnotationNotFoundException(e.getMessage());
        }

        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

        return index;
    }

    /**
     * This method converts the specified object, into its equivalent {@link Document} representation.
     * 
     * @param obj
     *            the object for which {@link Document} representation is to be created
     * @param classOfObj
     *            the class of the obj
     * @param documentId
     *            user defined id of document (e.g. 'Key' of a datastore entity).
     * @return {@link Document} representation of obj
     */
    public Document toDocument(Object obj, String documentId, Class<?> classOfObj) throws DocumentParseException {

        if (obj == null) {
            return null;
        }

        Document document = Document.newBuilder().build();
        DocumentParser parser = new DocumentParser();

        try {
            document = parser.parseDocument(obj, documentId, classOfObj);
        } catch (IllegalArgumentException e) {
            throw new DocumentParseException("Conversion failed.", e);
        } catch (IllegalAccessException e) {
            throw new DocumentParseException("Conversion failed.", e);
        }

        return document;
    }

    /**
     * This method converts the specified object, into its equivalent {@link Document} representation.
     * 
     * @param obj
     *            the object for which {@link Document} representation is to be created
     * @return {@link Document} representation of obj
     */
    public Document toDocument(Object obj) {
        return toDocument(obj, (String) null);
    }

    /**
     * This method converts the specified object, into its equivalent {@link Document} representation.
     * 
     * @param obj
     *            the object for which {@link Document} representation is to be created
     * @param documentId
     *            user defined id of document (e.g. 'Key' of a datastore entity).
     * @return {@link Document} representation of obj
     */
    public Document toDocument(Object obj, String documentId) {
        return toDocument(obj, documentId, obj.getClass());
    }

    /**
     * This method converts the specified {@link Document}, into an object T.
     * 
     * @param doc
     *            the {@link Document} for which object T representation is to be created
     * @param classOfT
     *            the class of T
     * @return T
     */
    public <T> T fromDocument(Document doc, Class<T> classOfT) throws ObjectParseException {
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