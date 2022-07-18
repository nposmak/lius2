package com.nposmak.exceptions;

public class StatusNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatusNotFoundException(String errorMessage) {
        super(errorMessage);
}	
}

