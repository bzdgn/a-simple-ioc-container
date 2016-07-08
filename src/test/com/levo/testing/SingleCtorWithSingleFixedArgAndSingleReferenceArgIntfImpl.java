package com.levo.testing;

public class SingleCtorWithSingleFixedArgAndSingleReferenceArgIntfImpl implements SingleCtorWithSingleFixedArgAndSingleReferenceArgIntf {
	
	private String value;
	private Logger logger;
	
	// See: config.json constructorParams
	public SingleCtorWithSingleFixedArgAndSingleReferenceArgIntfImpl(String value, Logger logger) {
		this.value = value;
		this.logger = logger;
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
