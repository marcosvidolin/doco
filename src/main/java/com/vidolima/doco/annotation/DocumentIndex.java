package com.vidolima.doco.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.appengine.api.search.IndexSpec;

/**
 * This annotation is applied to the entity class. This annotation defines the
 * name of the {@link IndexSpec}.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since February 9, 2014
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentIndex {

	/**
	 * Specifies the name of the Index.
	 * 
	 * @return name.
	 */
	String name() default "";
}