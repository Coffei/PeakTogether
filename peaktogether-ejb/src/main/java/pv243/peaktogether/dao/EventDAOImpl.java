package pv243.peaktogether.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTWriter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.Member;

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
    public List<Event> findEventsByDistanceFromStart(Point refPoint, int distance) {
        Query query = em.createNativeQuery("SELECT DISTINCT event.id, st_distance_sphere(location.point, ST_GeometryFromText(:refpoint)) as distance FROM event inner join event_location ON event.id=event_location.event_id" +
                " inner join location ON event_location.locations_id=location.id WHERE location.type='START' " +
                " AND  st_distance_sphere(location.point, ST_GeometryFromText(:refpoint)) <= :distance " +
         " ORDER BY distance ASC;");

        WKTWriter writer =new WKTWriter();
        query.setParameter("distance", distance*1000);
        query.setParameter("refpoint", writer.write(refPoint));

        List<Object> ids = query.getResultList();
        List<Event> events = new ArrayList<Event>(ids.size());

        for(Object objects : ids) {
            Object[] objs = (Object[])objects;
            Event event = findById(((BigInteger)objs[0]).longValue());
            events.add(event);
            System.out.println(objs[1]);
        }

        return events;
    }

    @Override
    public List<Event> findAllByOwner(Member owner) {
        if(owner==null)
            return findAll();

        TypedQuery<Event> query =  em.createQuery("from Event event where event.owner = :owner", Event.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }


}
