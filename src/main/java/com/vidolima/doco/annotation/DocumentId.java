package com.vidolima.doco.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.appengine.api.search.Document;

/**
 * Place this annotation on fields of an entity POJO. This field defines the id
 * of the {@link Document}.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since January 22, 2014
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentId {
	/**
	 * Specifies the name of the field.
	 * 
	 * @return name.
	 */
	String name() default "";
}