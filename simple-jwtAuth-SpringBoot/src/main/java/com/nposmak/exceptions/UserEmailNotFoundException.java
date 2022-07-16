package com.nposmak.exceptions;

public class UserEmailNotFoundException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserEmailNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
