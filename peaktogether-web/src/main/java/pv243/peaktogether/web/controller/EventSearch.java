package pv243.peaktogether.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

import org.primefaces.model.map.Marker;
import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.model.Event;
import pv243.peaktogether.util.EventFilter;

@ManagedBean
@ViewScoped
public class EventSearch implements Serializable {
    private static final Integer ALL_OPTIONS_SELECTED = 3;

    private Double radius;
    private Double longtitude;
    private Double latitude;

    private List<String> selectedOptions;

    private List<Event> events;

    private List<Event> filteredEvents;

    @Inject
    private transient Logger logger;

    @Inject
    private transient EventDAOInt eventDAO;

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

      /*  List<Marker> markers = createMarkersFromEvents(this.filteredEvents);
        mapModel.getMarkers().clear();
        for(Marker marker : markers) {
            mapModel.addOverlay(marker);
        }*/
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

        }

    }

    public List<Event> performSearch() {
        GeometryFactory gf = new GeometryFactory();

        Point refPoint = gf.createPoint(new Coordinate(this.longtitude, this.latitude));

        return eventDAO.findEventsByDistanceFromStart(refPoint, this.radius.intValue());
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
}
