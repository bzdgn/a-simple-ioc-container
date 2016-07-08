package com.levo.ioc.container;

/*
 * What is done here is simply wrapping the generic Throwable so that
 * if any exception is thrown, it will be aware of the Container
 * thus, the Exception stack will be easier to track
 * 
 */

public class IoCException extends Throwable {
	
	public IoCException(Exception e) {
		super(e);
	}
	
}
