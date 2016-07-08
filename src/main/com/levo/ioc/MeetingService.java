package com.levo.ioc;

public class MeetingService implements IMeetingService {

	@Override
	public String[] getAttendees() {
		return new String[] {"Cengiz", "Oguz", "Sibel", "Nurgul"};
	}
	
}
