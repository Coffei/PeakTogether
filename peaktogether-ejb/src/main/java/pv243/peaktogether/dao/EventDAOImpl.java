package pv243.peaktogether.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.vividsolutions.jts.geom.Point;
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

    @Override
    public List<Event> findEventsByDistanceStart(Point refPoint, int distance) {
        Query query = em.createNativeQuery("SELECT DISTINCT event.id FROM event inner join event_location ON event.id=event_location.event_id" +
                " inner join location ON event_location.locations_id=location.id WHERE location.type=\"START\" " +
                "AND st_distance_sphere(location.point, :refpoint) <= :distance " +
                "ORDER BY st_distance_sphere(location.point, :refpoint) ASC ");

    query.setParameter("distance", distance);
    query.setParameter("")