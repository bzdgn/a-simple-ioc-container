package com.levo.testing;

public class SingleCtorWithFixedArgIntfImpl implements SingleCtorWithFixedArgIntf {
	
	private int value;
	
	public SingleCtorWithFixedArgIntfImpl(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}
	
}
