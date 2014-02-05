package com.vidolima.doco.exception;

/**
 * This exception is raised if there is a serious issue that occurs during
 * parsing of a Document.
 * 
 * @author Marcos Alexandre Vidolin de Lima
 * @since February 3, 2014
 */
public final class DocumentParseException extends RuntimeException {

	private static final long serialVersionUID = -5403914693423563493L;

	/**
	 * Creates exception with the default constructor.
	 */
	public DocumentParseException() {
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
	public DocumentParseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates exception with the specified message.
	 * 
	 * @param message
	 *            error message describing what happened.
	 */
	public DocumentParseException(final String message) {
		super(message);
	}

	/**
	 * Creates exception with the specified cause.
	 * 
	 * @param cause
	 *            root exception that caused this exception to be thrown.
	 */
	public DocumentParseException(final Throwable cause) {
		super(cause);
	}
}