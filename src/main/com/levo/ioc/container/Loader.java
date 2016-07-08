package com.levo.ioc.container;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Loader {
	
	public Map<Class, Registration> loadConfiguration(String filename) throws IoCException {
		
		Map<Class, Registration> registrations = new HashMap<>();
		
		try {
			Path path = FileSystems.getDefault().getPath(filename);
			String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			ObjectMapper mapper = new ObjectMapper();
			
			List<Registration> parsedReg = mapper.readValue(contents, mapper.getTypeFactory().constructCollectionLikeType(List.class, Registration.class));
			for(Registration r : parsedReg) {
				Class<?> cls = Class.forName(r.getType());
				registrations.put(cls, r);	// put the class - registration key-value to the HashMap
			}
		} catch(IOException e) {
			throw new IoCException(e);
		} catch(ClassNotFoundException e) {
			throw new IoCException(e);
		}
		
		return registrations;
	}
	
}
