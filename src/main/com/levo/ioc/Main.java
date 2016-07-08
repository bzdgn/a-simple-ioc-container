package com.levo.ioc;

public class Main {

	public static void main(String[] args) {
		Container container;
		try {
			container = new Container("config.json");
			com.levo.ioc.IMeeting meeting = container.resolve(com.levo.ioc.IMeeting.class);
			meeting.getMeeting();
			for(String attendee : meeting.getAttendees()) {
				System.out.println(attendee);
			}
		} catch (IoCException e) {
			e.printStackTrace();
		}
	}

}
