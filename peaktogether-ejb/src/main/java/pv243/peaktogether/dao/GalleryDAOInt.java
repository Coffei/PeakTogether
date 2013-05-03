package pv243.peaktogether.dao;

import java.util.List;

import pv243.peaktogether.model.Gallery;

public interface GalleryDAOInt {
	
	void create(Gallery gallery);
	void remove(Gallery gallery);
	Gallery update(Gallery gallery);
	Gallery findById(Long id);
	List<Gallery> findAll();

}
