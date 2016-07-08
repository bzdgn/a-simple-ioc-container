package com.levo.ioc;

/*
 * [1] resolve will look up to config.json and find mapped implementation class
 *     and also will check out that other implementation classes for the interfaces
 *     that will be used as dependencies
 *     
 *     Inversion Of Control use-case;
 *     due to the config.json file that which dependencies (as interfaces) are mapped;
 *     
 *     a - Implementation class for IMeeting : Meeting
 *     b - Meeting needs IMeetingService on its constructor
 *     c - Implementation class for IMeetingServices : MeetingService
 */

public class Main {

	public static void main(String[] args) {
		Container container;
		try {
			container = new Container("config.json");
			com.levo.ioc.IMeeting meeting = container.resolve(com.levo.ioc.IMeeting.class);	// [1]
			meeting.getMeeting();
			for(String attendee : meeting.getAttendees()) {
				System.out.println(attendee);
			}
		} catch (IoCException e) {
			e.printStackTrace();
		}
	}

}
