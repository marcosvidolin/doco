package com.vidolima.doco.exception;

/**
 * This exception is raised if a given type are not a valid type.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since January 28, 2014
 */
public final class FieldTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8500903267390027545L;

	/**
	 * Creates exception with the default constructor.
	 */
	public FieldTypeNotFoundException() {
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
	public FieldTypeNotFoundException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates exception with the specified message.
	 * 
	 * @param message
	 *            error message describing what happened.
	 */
	public FieldTypeNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Creates exception with the specified cause.
	 * 
	 * @param cause
	 *            root exception that caused this exception to be thrown.
	 */
	public FieldTypeNotFoundException(final Throwable cause) {
		super(cause);
	}
}
