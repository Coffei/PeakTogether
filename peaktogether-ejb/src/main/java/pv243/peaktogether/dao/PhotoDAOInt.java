package pv243.peaktogether.dao;

import java.util.List;

import pv243.peaktogether.model.Photo;

public interface PhotoDAOInt {
	void create(Photo photo);
	void remove(Photo photo);
	Photo update (Photo photo);
	Photo findById(Long id);
	List<Photo> findAll();
}
