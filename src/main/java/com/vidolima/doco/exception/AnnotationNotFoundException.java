package com.vidolima.doco.exception;

/**
 * This exception is raised if a given annotation are not found.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since February 9, 2014
 */
public final class AnnotationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2461855898134471355L;

	/**
	 * Creates exception with the default constructor.
	 */
	public AnnotationNotFoundException() {
		super();
	}

	/**
	 * Creates exception with the specified message and cause.
	 * 
	 * @param message
	 *            error message describing what happened.
	 * @param cause
	 *            root exception that caused this exception to be thrown.
	 */
	public AnnotationNotFoundException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates exception with the specified message.
	 * 
	 * @param message
	 *            error message describing what happened.
	 */
	public AnnotationNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Creates exception with the specified cause.
	 * 
	 * @param cause
	 *            root exception that caused this exception to be thrown.
	 */
	public AnnotationNotFoundException(final Throwable cause) {
		super(cause);
	}
}
