package com.vidolima.doco.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Place this annotation on fields of an entity POJO which should be used as Facets. Find out more about facets at <a
 * href="https://cloud.google.com/appengine/docs/java/search/faceted_search#adding_facets_to_a_document">here</a>.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FacetField {

    /**
     * Specifies the name of the field.
     * 
     * @return name.
     */
    String name() default "";

    /**
     * Specifies the {@link FieldType} of the field.
     * 
     * @return {@link FieldType}.
     */
    FacetType type();
}