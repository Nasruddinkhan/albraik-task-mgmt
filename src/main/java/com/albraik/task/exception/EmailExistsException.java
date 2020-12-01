package com.albraik.task.exception;

public class EmailExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4903754281115553959L;
	public EmailExistsException(final String message) {
        super(message);
    }
}
