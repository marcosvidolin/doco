package com.vidolima.doco.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.appengine.api.search.Document;

/**
 * Place this annotation on fields of an entity POJO. This field defines a field
 * of a {@link Document}.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since January 22, 2014
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentField {

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
	FieldType type() default FieldType.TEXT;
}