package com.vidolima.doco.annotation;

/**
 * Defines all valid facet types. Place one of these type in a {@link FacetField} annotation.
 * 
 * All possible facet types are defined at <a
 * href="https://cloud.google.com/appengine/docs/java/javadoc/com/google/appengine/api/search/Facet">here</a>.
 */
public enum FacetType {
    ATOM,
    NUMBER
}