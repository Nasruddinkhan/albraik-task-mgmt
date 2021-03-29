package com.albraik.task.exception;

public class UnauthorizedAccessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4173654990602284692L;

	public UnauthorizedAccessException(String msg)
	{
		super(msg);
	}
	
}
