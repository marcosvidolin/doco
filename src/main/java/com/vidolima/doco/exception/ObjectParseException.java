package com.vidolima.doco.exception;

/**
 * This exception is raised if there is a serious issue that occurs during
 * parsing of an Object.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since February 3, 2014
 */
public final class ObjectParseException extends RuntimeException {

	private static final long serialVersionUID = -4323975111034037094L;

	/**
	 * Creates exception with the default constructor.
	 */
	public ObjectParseException() {
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
	public ObjectParseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates exception with the specified message.
	 * 
	 * @param message
	 *            error message describing what happened.
	 */
	public ObjectParseException(final String message) {
		super(message);
	}

	/**
	 * Creates exception with the specified cause.
	 * 
	 * @param cause
	 *            root exception that caused this exception to be thrown.
	 */
	public ObjectParseException(final Throwable cause) {
		super(cause);
	}
}