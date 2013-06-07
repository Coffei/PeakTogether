package pv243.peaktogether.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import pv243.peaktogether.model.Member;

@Stateless
public class UserDAOImpl implements UserDAOInt {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void create(Member member) {
		em.persist(member);
	}

	@Override
	public void remove(Member member) {
		member = em.merge(member);
		em.remove(member);
	}

	@Override
	public Member update(Member member) {
		return em.merge(member);
	}

	@Override
	public Member findById(Long id) {
		return em.find(Member.class, id);
	}

	@Override
	public List<Member> findAll() {
		List<Member> results =
				em.createQuery("from "+Member.class.getName(), Member.class).getResultList();
		return results;
	}

}
