package pv243.peaktogether.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import pv243.peaktogether.model.Photo;

@Stateless
public class PhotoDAOImpl implements PhotoDAOInt {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void create(Photo photo) {
		em.persist(photo);
		
	}

	@Override
	public void remove(Photo photo) {
		photo = em.merge(photo);
		em.remove(photo);
	}

	@Override
	public Photo update(Photo photo) {
		return em.merge(photo);
	}

	@Override
	public Photo findById(Long id) {
		return em.find(Photo.class, id);
	}

	@Override
	public List<Photo> findAll() {
		List<Photo> results =
				em.createQuery("from "+Photo.class.getName(), Photo.class).getResultList();
		return results;
	}

}
