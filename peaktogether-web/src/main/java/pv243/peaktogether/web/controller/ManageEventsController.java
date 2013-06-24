package pv243.peaktogether.web.controller;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.*;
import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.Member;
import pv243.peaktogether.util.EventFilter;
import pv243.peaktogether.util.MapUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 20.6.13
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ManageEventsController implements Serializable {

    @Inject
    private transient EventDAOInt eventDAO;

    @Inject
    @Named("signedMember")
    private transient Member signedMember;

    @Inject
    private transient Logger logger;

    private String zoom;
    private String center;

    private MapModel mapModel;

    private List<Event> events;

    private List<Event> filteredEvents;

    private List<String> selectedOptions;

    private static final Integer ALL_OPTIONS_SELECTED = 3;

    private Marker currentMarker;
    private Event currentEvent;

    public void filterChange() {

        this.filteredEvents = new ArrayList<Event>();
        EventFilter eventFilter = new EventFilter();

        logger.info("filter event");

        if (selectedOptions.size() == ALL_OPTIONS_SELECTED || selectedOptions.size() == 0) {

            this.filteredEvents = this.events;
        }

        else {

            for (String filter : selectedOptions) {

                if (filter.equals("past")) {
                    this.filteredEvents.addAll((eventFilter.filterPastEvents(this.events)));

                } else if (filter.equals("upcoming")) {
                    this.filteredEvents.addAll(eventFilter.filterUpcomingEvents(this.events));

                } else {
                    this.filteredEvents.addAll(eventFilter.filterActiveEvents(this.events));
                }
            }
        }

        List<Marker> markers = createMarkersFromEvents(this.filteredEvents);
        mapModel.getMarkers().clear();
        for(Marker marker : markers) {
            mapModel.addOverlay(marker);
        }
    }

    public void delete(Event event) {
        eventDAO.remove(event);
        this.events.remove(event);
        this.filteredEvents.remove(event);
    }

    public void leaveEvent(Event event){
        //TODO
    }


    @PostConstruct
    private void init() {
        mapModel = new DefaultMapModel();
        this.events = eventDAO.findAllByOwner(signedMember);
        this.filteredEvents = events;

        List<Marker> markers = createMarkersFromEvents(this.filteredEvents);
        for(Marker marker : markers) {
            mapModel.addOverlay(marker);
        }

        generateMapParams();

    }

    public void selectCurrentMarker(OverlaySelectEvent event) {
        if(event==null)
            return;//if no event set then NOOP

        this.currentMarker = (Marker)event.getOverlay();
        this.currentEvent = (Event) this.currentMarker.getData();
    }

    private void generateMapParams() {
        MapUtils mapUtils = new MapUtils();
        mapUtils.computeGeoAverage(mapModel.getMarkers());


        center = mapUtils.getLastAvgLat() + ", " + mapUtils.getLastAvgLon();


        zoom = String.valueOf(mapUtils.computeZoom(mapModel.getMarkers(), 1000, 500));
    }

    private List<Marker> createMarkersFromEvents(List<Event> events) {
        List<Marker> markers = new ArrayList<Marker>(events.size());
        for(Event event : events) {
            Location start = getLocationByType(event, LocationType.START);
            if(start!=null) {
                Marker marker = new Marker(new LatLng(start.getPoint().getCoordinate().y, start.getPoint().getCoordinate().x));
                marker.setTitle(event.getName());
                marker.setData(event);
                if(event.getPublicEvent()!=null && event.getPublicEvent().booleanValue()) {
                    marker.setIcon("http://maps.google.com/mapfiles/ms/micons/green-dot.png");
                }

                markers.add(marker);
            }
        }

        return markers;
    }

    private Location getLocationByType(Event event, LocationType type) {
        if(event.getLocations()==null)
            return null;

        for(Location loc : event.getLocations()) {
            if(loc.getType() == type) {
                return loc;
            }
        }

        return null;
    }


    public List<Event> getFilteredEvents() {
        return filteredEvents;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public Marker getCurrentMarker() {
        return currentMarker;
    }

    public void setCurrentMarker(Marker currentMarker) {
        this.currentMarker = currentMarker;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }
    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }


     public void setFilteredEvents(List<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
