package pv243.peaktogether.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pv243.peaktogether.model.Location;

@Stateless
public class LocationDAOImpl implements LocationDAOInt {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Location location) {

		em.persist(location);

	}

	@Override
	public void remove(Location location) {

		location = em.merge(location);
		em.remove(location);

	}

	@Override
	public Location findById(Long id) {
		return em.find(Location.class, id);
	}

	@Override
	public Location update(Location location) {

		location = em.merge(location);
		em.persist(location);
		return location;
	}

	@Override
	public List<Location> findAll() {

		return em.createQuery("from " + Location.class.getName(), Location.class).getResultList();
	}

}
