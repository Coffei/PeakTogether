package pv243.peaktogether.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import pv243.peaktogether.model.User;

@Stateless
public class UserDAOImpl implements UserDAOInt {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void create(User user) {
		em.persist(user);
	}

	@Override
	public void remove(User user) {
		user = em.merge(user);
		em.remove(user);
	}

	@Override
	public User update(User user) {
		return em.merge(user);
	}

	@Override
	public User findById(Long id) {
		return em.find(User.class, id);
	}

	@Override
	public List<User> findAll() {
		List<User> results = 
				em.createQuery("from "+User.class.getName(), User.class).getResultList();
		return results;
	}

}
