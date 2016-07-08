package com.levo.ioc.container;

import java.lang.reflect.Constructor;
import java.util.List;

public class Registration {
	
	private String type;
	private String mapTo;
	private boolean singleton;
	
	private List<Constructor> constructorParams;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMapTo() {
		return mapTo;
	}

	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}
	
	public List<Constructor> getConstructorParams() {
		return constructorParams;
	}
	
	public void setConstructorParams(List<Constructor> constructorParams) {
		this.constructorParams = constructorParams;
	}
	
}
