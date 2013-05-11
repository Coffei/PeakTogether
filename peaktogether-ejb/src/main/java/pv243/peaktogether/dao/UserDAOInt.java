package pv243.peaktogether.dao;

import java.util.List;

import pv243.peaktogether.model.User;

public interface UserDAOInt {
	void create(User user);
	void remove(User user);
	User update(User user);
	User findById(Long id);
	List<User> findAll();
}
