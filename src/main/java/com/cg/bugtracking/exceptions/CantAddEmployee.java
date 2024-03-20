package com.cg.bugtracking.exceptions;

public class CantAddEmployee extends RuntimeException {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CantAddEmployee(String message){
      super(message);
  }
	
}