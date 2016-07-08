package com.levo.stuff;

public class MeetingService implements IMeetingService {

	@Override
	public String[] getAttendees() {
		return new String[] {"Cengiz", "Oguz", "Sibel", "Nurgul", "Levent"};
	}
	
}
