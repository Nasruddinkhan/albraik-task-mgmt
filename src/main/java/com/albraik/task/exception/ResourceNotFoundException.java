package com.albraik.task.exception;

public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4173654990602284692L;

	public ResourceNotFoundException(String msg)
	{
		super(msg);
	}
	
}
