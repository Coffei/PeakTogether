package pv243.peaktogether.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.vividsolutions.jts.geom.*;

import org.primefaces.model.map.*;
import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.util.EventFilter;
import pv243.peaktogether.util.MapUtils;

@ManagedBean
@ViewScoped
public class EventSearch implements Serializable {
    private static final Integer ALL_OPTIONS_SELECTED = 3;

    private Double radius;
    private Double longtitude;
    private Double latitude;

    private String query;

    private List<String> selectedOptions;

    private List<Event> events;

    private List<Event> filteredEvents;

    private MapModel mapModel;
    private String center;
    private String zoom;

    @Inject
    private transient Logger logger;

    @Inject
    private transient EventDAOInt eventDAO;

    @Inject
    private Places places;

    public EventSearch() {
    }

    public String search() {

        return "result?faces-redirect=true&radius=" + this.radius + "&longtitude=" + this.longtitude + "&latitude="
                + this.latitude;
    }

    public void filterChanged() {
        this.filteredEvents = new ArrayList<Event>();
        EventFilter eventFilter = new EventFilter();

        if (selectedOptions.size() == ALL_OPTIONS_SELECTED || selectedOptions.size() == 0) {
            //no filtering is needed
            this.filteredEvents = this.events;
        } else {
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

    public void searchQuery() {
        if(query!=null && !query.isEmpty()) {
            Places.Location loc = places.findByQuery(query);
            if(loc!=null) {
                this.latitude = loc.getLat();
                this.longtitude = loc.getLon();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, query + " found!", null));
                return;
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "place was not found", null));
    }

    @PostConstruct
    public void init() {

        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> params = ctx.getExternalContext().getRequestParameterMap();

        if (params.get("radius") != null)
            this.radius = Double.valueOf(params.get("radius"));
        if (params.get("latitude") != null)
            this.latitude = Double.valueOf(params.get("latitude"));
        if (params.get("longtitude") != null)
            this.longtitude = Double.valueOf(params.get("longtitude"));

        if (this.radius != null & this.latitude != null && this.longtitude != null) {

            logger.info("search began");
            this.events = performSearch();
            this.filteredEvents = events;

            //map stuff
            mapModel = new DefaultMapModel();
            List<Marker> markers = createMarkersFromEvents(this.filteredEvents);
            //search circle
            Circle searchCircle = new Circle(new LatLng(this.latitude, this.longtitude), this.radius * 1000);
            searchCircle.setFillColor("#3AA6D0");
            searchCircle.setStrokeColor("#024C68");
            searchCircle.setFillOpacity(0.3);
            searchCircle.setStrokeOpacity(0.7);

            mapModel.addOverlay(searchCircle);
            for(Marker marker : markers) {
                mapModel.addOverlay(marker);
            }

            generateMapParams();

        }

    }

    public List<Event> performSearch() {
        GeometryFactory gf = new GeometryFactory();

        Point refPoint = gf.createPoint(new Coordinate(this.longtitude, this.latitude));

        return eventDAO.findEventsByDistanceFromStart(refPoint, this.radius.intValue());
    }

    private void generateMapParams() {
        MapUtils mapUtils = new MapUtils();

        center = latitude + ", " +longtitude;

        zoom = String.valueOf(mapUtils.computeZoom(this.latitude, this.longtitude, this.radius, 1000,500));
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

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public List<Event> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(List<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
