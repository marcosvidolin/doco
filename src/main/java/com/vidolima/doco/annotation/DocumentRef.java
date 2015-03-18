package com.vidolima.doco.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.appengine.api.search.Document;

/**
 * Place this annotation on fields of type Ref<?> from objectify. This annotation adds all the annotated fields of the
 * specified class to the same {@link Document} as other fields of this class.
 * 
 * Fields from referenced class follow following naming scheme:
 * 
 * <pre>
 * Class A {
 *   @DocumentRef(type = B.class)
 *   Ref<B> refB;
 * }
 * 
 * Class B {
 *   @DocumentField(type = TEXT)
 *   String text;
 * }
 * </pre>
 * 
 * For above classes, if document is generated for class A, it'll have B's fields prefixed with "B_" (i.e. B_text).
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentRef {
    Class<?> type();
}