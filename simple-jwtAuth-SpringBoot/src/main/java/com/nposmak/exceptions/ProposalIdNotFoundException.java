package com.nposmak.exceptions;

public class ProposalIdNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProposalIdNotFoundException(String errorMessage) {
        super(errorMessage);
	}
}
