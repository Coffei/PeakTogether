package pv243.peaktogether.dao;

import java.util.List;

import pv243.peaktogether.model.Location;

public interface LocationDAOInt {
	
	void create (Location location);
	void remove (Location location);
	Location update (Location location);
	Location findById(Long id);
	List<Location> findAll();

}
