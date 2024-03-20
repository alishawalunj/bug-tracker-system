package com.cg.bugtracking.exceptions;

public class CantDeleteEmployee extends RuntimeException {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CantDeleteEmployee(String message){
       super(message);
   }
	
}