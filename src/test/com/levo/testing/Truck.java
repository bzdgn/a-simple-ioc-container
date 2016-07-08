package com.levo.testing;

public class Truck implements Drivable {
	
	private Logger logger;
	
	public Truck(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void drive() {
		System.out.println("Driving: You drive me crazy baby !");
	}
	
}
