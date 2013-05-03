package pv243.peaktogether.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pv243.peaktogether.model.Event;

@Stateless
public class EventDAOImpl implements EventDAOInt {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Event event) {

        em.persist(event);

    }

    @Override
    public void remove(Event event) {

        event = em.merge(event);
        em.remove(event);

    }

    @Override
    public Event findById(Long id) {
        return em.find(Event.class, id);
    }

    @Override
    public Event update(Event event) {

        event = em.merge(event);
        em.persist(event);
        return event;

    }

    @Override
    public List<Event> findAll() {

        return em.createQuery("from " + Event.class.getName(), Event.class).getResultList();
    }

}
