package com.levo.ioc.container;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * [1] Resolve method steps;
 * - Find the type in registrations map
 * - find the biggest constructor
 * - resolve all the constructor parameters
 * - apply any constructor arguments from the map by name
 * - create an instance of the type
 * 
 */
public class Container {
	
	Map<Class, Registration> registrations;
	Map<Class, Converter> converters = new HashMap<>();	// HashMap of Class <-> Parser Method
	private interface Converter<T> {					// We need to store static parser methods in this interface
		T convert(String valueAsString);
	}
	
	/*
	 * Reads the configuration file config.json
	 */
	public Container(String configurationPath) throws IoCException {
		File file = new File(configurationPath);
		if(!file.exists()) {
			throw new IoCException(new FileNotFoundException());	// wrap under IoCException to track the stack trace !
		}
		
		Loader loader = new Loader();
		
		registrations = loader.loadConfiguration(configurationPath);
		
		registerConverters();
	}
	
	// see [1] on class comments
	public <T> T resolve(Class<T> type) throws IoCException {
		//		- Find the type in registrations map
		Registration registration = registrations.get(type);
		List<com.levo.ioc.container.Constructor> constructorParams = registration.getConstructorParams();
		T instance; // inst.ated after resolving constructros and const arg parameter instances initialized
		
		try{
			Class cls = Class.forName(registration.getMapTo());
			
			//		- find the biggest constructor
			java.lang.reflect.Constructor longestConstructor = getLongestConstructor(cls);
			
			//		- resolve all the constructor parameters
			Parameter[] parameters = longestConstructor.getParameters();
			
			//		- apply any constructor arguments from the map by name
			List<Object> parameterInstances = new ArrayList<Object>();
			for(Parameter parameter : parameters) {
				Class parameterClass = parameter.getType();
				if(parameterClass.isPrimitive() || parameterClass.isAssignableFrom(String.class)) {
					getNonReferenceParameters(constructorParams, parameterInstances, parameter, parameterClass); // primitive
				} else {
					getConfiguredParameters(parameterInstances, parameterClass);	// config.json
				}
			}
			
			//		- create an instance of the type
			instance = createInstance(longestConstructor, parameterInstances);
		} catch(Exception e) {
			throw new IoCException(e);
		}

		return instance;
	}
	
	private void getConfiguredParameters(List<Object> parameterInstances, Class parameterClass) throws IoCException {
		Object resolvedInstance = resolve(parameterClass);
		parameterInstances.add(resolvedInstance);
	}
	
	private void getNonReferenceParameters(List<Constructor> constructorParams, List<Object> parameterInstances, Parameter parameter, Class parameterClass) {
		Object value = null;
		for(Constructor ctor : constructorParams) {
			if(ctor.getName().equals(parameter.getName())) {
				value = ctor.getValue();
				break;
			}
		}
		Converter c = converters.get(parameterClass);
		
		parameterInstances.add(c.convert(value.toString()));
	}
	
	private <T> T createInstance(java.lang.reflect.Constructor longestConstructor, List<Object> parameterInstances) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
		T instance;
		Parameter[] parameterTypes = longestConstructor.getParameters();
		Object[] parameters = new Object[parameterTypes.length];
		
//		for(int i = 0; i < parameters.length; i++) {
//			parameters[i] = parameterInstances.get(i);
//		}
		
		for(int i = 0; i < parameterTypes.length; i++) {
			Class argumentClass = parameterInstances.get(i).getClass();
			Class parameterClass = parameterTypes[i].getType();
			
			if(parameterClass.isPrimitive() || argumentClass.isPrimitive()) {
				if(primitivesMatch(argumentClass, parameterClass)) {
					parameters[i] = parameterInstances.get(i);
				}
			}
			
			if(parameterClass.isAssignableFrom(argumentClass)) {
				parameters[i] = parameterInstances.get(i);
			}
		}
		
		instance = (T) longestConstructor.newInstance(parameters);	// invoke with parameters
		return instance;
	}
	
	private java.lang.reflect.Constructor getLongestConstructor(Class cls) {
		java.lang.reflect.Constructor[] constructors = cls.getConstructors();
		
		java.lang.reflect.Constructor longestConstructor = constructors[0];
		for(java.lang.reflect.Constructor ctor : constructors) {
			if(ctor.getParameterCount() > longestConstructor.getParameterCount()) {
				longestConstructor = ctor;
			}
		}
		
		return longestConstructor;
	}
	
	private boolean primitivesMatch(Class argumentClass, Class parameterClass) {
		if( (argumentClass == int.class || argumentClass == Integer.class) && (parameterClass == int.class || parameterClass == Integer.class) ) {
			return true;
		}
		if( (argumentClass == byte.class || argumentClass == Byte.class) && (parameterClass == byte.class || parameterClass == Byte.class) ) {
			return true;
		}
		if( (argumentClass == short.class || argumentClass == Short.class) && (parameterClass == short.class || parameterClass == Short.class) ) {
			return true;
		}
		if( (argumentClass == long.class || argumentClass == Long.class) && (parameterClass == long.class || parameterClass == Long.class) ) {
			return true;
		}
		if( (argumentClass == char.class || argumentClass == Character.class) && (parameterClass == char.class || parameterClass == Character.class) ) {
			return true;
		}
		if( (argumentClass == double.class || argumentClass == Double.class) && (parameterClass == double.class || parameterClass == Double.class) ) {
			return true;
		}
		if( (argumentClass == float.class || argumentClass == Float.class) && (parameterClass == float.class || parameterClass == Float.class) ) {
			return true;
		}
		if( (argumentClass == boolean.class || argumentClass == Boolean.class) && (parameterClass == boolean.class || parameterClass == Boolean.class) ) {
			return true;
		}
		return false;
	}
	
	private void registerConverters() {
		converters.put(int.class, Integer::parseInt);
		converters.put(float.class, Float::parseFloat);
		converters.put(double.class, Double::parseDouble);
		converters.put(byte.class, Byte::parseByte);
		converters.put(long.class, Long::parseLong);
		converters.put(short.class, Short::parseShort);
		converters.put(boolean.class, Boolean::parseBoolean);
		converters.put(String.class, s -> s);				// no need to convert string to string, output = input
		converters.put(Character.class, c -> c);			// no need to convert char   to char  , output = input
	}

}
