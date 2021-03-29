package com.albraik.task.exception;

public class PhoneNumberExistException extends RuntimeException{
	
	private static final long serialVersionUID = -1906284423612242610L;

	public PhoneNumberExistException(String msg)
	{
		super(msg);
	}

}
