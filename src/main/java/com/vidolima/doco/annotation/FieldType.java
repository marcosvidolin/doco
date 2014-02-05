package com.vidolima.doco.annotation;

import com.google.appengine.api.search.Document;

/**
 * Defines all valid field types. Place one of these type in a
 * {@link DocumentField} annotation to indicate the field type of the
 * {@link Document}.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since January 22, 2014
 */
public enum FieldType {
	ATOM, TEXT, HTML, NUMBER, DATE, GEO_POINT
}