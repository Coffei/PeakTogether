package pv243.peaktogether.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pv243.peaktogether.model.Gallery;


@Stateless
public class GalleryDAOImpl implements GalleryDAOInt
{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Gallery gallery) {

		em.persist(gallery);

	}

	@Override
	public void remove(Gallery gallery) {
		// TODO Auto-generated method stub
		
		gallery = em.merge(gallery);
		em.remove(gallery);

	}

	@Override
	public Gallery findById(Long id) {
		return em.find(Gallery.class, id);
	}

	@Override
	public Gallery update(Gallery gallery) {
		
		gallery = em.merge(gallery);
		em.persist(gallery);
		return gallery;
	}

	@Override
	public List<Gallery> findAll() {
		// TODO Auto-generated method stub
		 return em.createQuery("from " + Gallery.class.getName(), Gallery.class).getResultList();
	}

}
