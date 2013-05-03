package pv243.peaktogether.dao;

import java.util.List;

import pv243.peaktogether.model.Event;

public interface EventDAOInt {
	
	void create(Event event);
	void remove(Event event);
	Event findById(Long id);
	Event update (Event event);
	List<Event> findAll();

}
