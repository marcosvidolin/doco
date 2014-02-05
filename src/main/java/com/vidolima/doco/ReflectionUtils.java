package com.vidolima.doco;

import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.vidolima.doco.annotation.DocumentId;

/**
 * Utility class containing some methods for reflection purpose.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since January 26, 2014
 */
final class ReflectionUtils {

	/**
	 * Obtains all {@link java.lang.reflect.Field}s that contains the specified
	 * annotation class given a class.
	 * 
	 * @param classOfObj
	 *            the class of the object that contains the fields
	 * @param classOfAnnotation
	 *            the annotation class to be search
	 * @return a list of {@link java.lang.reflect.Field}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static List<java.lang.reflect.Field> getAnnotatedFields(
			Class<?> classOfObj, Class classOfAnnotation) {

		List<java.lang.reflect.Field> result = new ArrayList<java.lang.reflect.Field>();

		java.lang.reflect.Field[] fields = classOfObj.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);

		for (java.lang.reflect.Field f : fields) {
			Annotation annotation = f.getAnnotation(classOfAnnotation);
			if (annotation != null) {
				result.add(f);
			}
		}

		return result;
	}

	/**
	 * Obtains the {@link java.lang.reflect.Field} that contains the specified
	 * annotation class given a class.
	 * 
	 * @param classOfObj
	 *            the class of the object that contains the field
	 * @param classOfAnnotation
	 *            the annotation class to be search
	 * @return a {@link java.lang.reflect.Field}
	 */
	@SuppressWarnings("rawtypes")
	static java.lang.reflect.Field getAnnotatedField(Class<?> classOfObj,
			Class classOfAnnotation) {
		List<java.lang.reflect.Field> result = getAnnotatedFields(classOfObj,
				classOfAnnotation);

		if (result.isEmpty())
			// TODO: lencar exception, anotacao nao encontrada
			throw new IncompleteAnnotationException(DocumentId.class, "id");
		if (result.size() > 1)
			// TODO: lancar exception, mais de um campo anotado
			throw new NullPointerException();
		return result.get(0);
	}

	/**
	 * Obtains the value of the field.
	 * 
	 * @param field
	 *            the field to be set to the specified new value.
	 * @param obj
	 *            object from which the represented field's value is to be
	 *            extracted
	 * @param classOfT
	 *            the class of T
	 * @return the value of the represented field in a given type
	 * @throws IllegalArgumentException
	 *             if this Field object is enforcing Java language access
	 *             control and the underlying field is inaccessible.
	 * @throws IllegalAccessException
	 *             if the specified object is not an instance of the class or
	 *             interface declaring the underlying field (or a subclass or
	 *             implementor thereof).
	 */
	static <T> T getFieldValue(Field field, Object obj, Class<T> classOfT)
			throws IllegalArgumentException, IllegalAccessException {
		return classOfT.cast(field.get(obj));
	}

}