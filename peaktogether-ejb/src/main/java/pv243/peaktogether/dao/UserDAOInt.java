package pv243.peaktogether.dao;

import java.util.List;

import pv243.peaktogether.model.Member;

public interface UserDAOInt {
	void create(Member member);
	void remove(Member member);
	Member update(Member member);
	Member findById(Long id);
	List<Member> findAll();
}
