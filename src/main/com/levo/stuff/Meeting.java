package com.levo.stuff;

public class Meeting implements IMeeting {
	
	private IMeetingService meetingService;
	private String[] attendees;
	
	public Meeting(IMeetingService meetingService) {
		this.meetingService = meetingService;
	}

	@Override
	public String[] getAttendees() {
		return attendees;
	}

	@Override
	public void setAttendees(String[] attendees) {
		this.attendees = attendees;
	}

	@Override
	public void getMeeting() {
		attendees = meetingService.getAttendees();
	}

}
