package com.levo.stuff;

// Use this implementation class to validate config.json works
// see: config.json
public class MeetingServiceAlternative implements IMeetingService {

	@Override
	public String[] getAttendees() {
		return new String[] {"Levent", "Whoever"};
	}
	
}
