package pv243.peaktogether.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pv243.peaktogether.model.Event;

/**
 * 
 * @author osiris
 * 
 */

public class EventFilter {

	public EventFilter() {
	};

	public List<Event> filterPastEvents(List<Event> events) {

		Date now = new Date();

		List<Event> pastEvents = new ArrayList<Event>();

		for (Event event : events) {

			if (event.getEnd().before(now))
				pastEvents.add(event);
		}

		return pastEvents;
	}

	
	public List<Event> filterUpcomingEvents(List<Event> events) {

		Date now = new Date();

		List<Event> upcomingEvents = new ArrayList<Event>();

		for (Event event : events) {

			if (event.getStart().after(now))
				upcomingEvents.add(event);
		}

		return upcomingEvents;
	}
	
	public List<Event> filterActiveEvents(List<Event> events) {
		
		Date now = new Date();
		
		List<Event> activeEvents = new ArrayList<Event>();
		
		for (Event event : events) {
			
			if (event.getStart().before(now) && event.getEnd().after(now)) activeEvents.add(event);

		}
		
		return activeEvents;
	}

}
