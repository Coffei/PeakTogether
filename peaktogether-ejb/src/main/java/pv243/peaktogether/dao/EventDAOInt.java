package pv243.peaktogether.dao;

import java.util.List;

import com.vividsolutions.jts.geom.Point;
import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.Member;

public interface EventDAOInt {

    void create(Event event);
    void remove(Event event);
    Event findById(Long id);
    Event update (Event event);
    List<Event> findAll();

    /**
     * Returns Events that have are within specified distance from refPoint.
     * This currently has to be done via native SQL query (hibernate spatial doesn't support the functions needed).
     * @param distance distance in km
     * @return List of events sorted by distance.
     */
    List<Event> findEventsByDistanceFromStart(Point refPoint, int distance);

   /* List<Event> findPastEvents();

    List<Event> findFutureEvents();

    List<Event> findActiveEvents();*/

    List<Event> findAllByOwner(Member owner);
   /* List<Event> findPastEventsByOwner(Member owner);

    List<Event> findFutureEventsByOwner(Member owner);

    List<Event> findActiveEventsByOwner(Member owner);*/
}
