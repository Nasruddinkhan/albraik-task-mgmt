package com.albraik.task.exception;

public class UserNotFound extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3643066267933409028L;

	public UserNotFound(String msg){
		super(msg);	
	}
}
