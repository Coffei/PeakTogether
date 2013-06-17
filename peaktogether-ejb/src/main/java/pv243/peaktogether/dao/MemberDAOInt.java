package pv243.peaktogether.dao;

import java.util.List;

import pv243.peaktogether.model.Member;

public interface MemberDAOInt {
	void create(Member member);
	void remove(Member member);
	Member update(Member member);
	Member findById(Long id);
    Member findByEmail(String email);
	List<Member> findAll();
}
