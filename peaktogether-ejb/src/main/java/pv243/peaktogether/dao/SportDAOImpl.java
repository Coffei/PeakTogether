package pv243.peaktogether.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import pv243.peaktogether.model.Sport;

@Stateless
public class SportDAOImpl implements SportDAOInt {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void create(Sport sport) {
		em.persist(sport);
	}

	@Override
	public void remove(Sport sport) {
		sport = em.merge(sport);
		em.remove(sport);
	}

	@Override
	public Sport update(Sport sport) {
		return em.merge(sport);
		
	}

	@Override
	public Sport findById(Long id) {
		return em.find(Sport.class, id);
	}

	@Override
	public List<Sport> findAll() {
		List<Sport> results = 
				em.createQuery("from "+Sport.class.getName(), Sport.class).getResultList();
		return results;
	}

}
