package com.nposmak.exceptions;

public class UserIdNotFoundException extends  Exception{
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserIdNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
