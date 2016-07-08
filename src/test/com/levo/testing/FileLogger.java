package com.levo.testing;

public class FileLogger implements Logger {
	
	private String fileName;
	
	public FileLogger(String fileName) {
		this.fileName = fileName;
	}
	
}
