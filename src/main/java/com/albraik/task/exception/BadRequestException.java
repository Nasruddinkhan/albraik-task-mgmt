package com.albraik.task.exception;

public class BadRequestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4173654990602284692L;

	public BadRequestException(String msg)
	{
		super(msg);
	}
	
}
