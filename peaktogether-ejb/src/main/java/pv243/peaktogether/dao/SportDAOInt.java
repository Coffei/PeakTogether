package pv243.peaktogether.dao;

import java.util.List;

import pv243.peaktogether.model.Sport;

public interface SportDAOInt {
	void create(Sport sport);
	void remove(Sport sport);
	Sport update(Sport sport);
	Sport findById(Long id);
	List<Sport> findAll();
	
}
