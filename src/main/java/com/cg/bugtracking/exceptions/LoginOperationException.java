package com.cg.bugtracking.exceptions;

public class LoginOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public LoginOperationException(String msg)
	{
		super(msg);
	}
}
