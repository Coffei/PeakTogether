package pv243.peaktogether.web.controller;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.model.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 20.6.13
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class EventFormController implements Serializable {

    @Inject
    private transient EventDAOInt eventDao;

    @ManagedProperty("#{mapBean}")
    private MapBean mapBean;

    @ManagedProperty("#{signedMember}")
    private Member signedMember;

    @Inject
    private transient Logger log;

    private Event event;


    public String submit() {
        this.event.setOwner(signedMember);
        List<Location> locations = getLocationsFromMarkers(mapBean.getMapModel().getMarkers());
        this.event.setLocations(locations);
        if(event.getCapacity() > 0) {
            event.setLimited(true);
        } else {
            event.setLimited(false);
        }

        eventDao.create(event);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New event created!", null));

        return "index";
    }

    private List<Location> getLocationsFromMarkers(List<Marker> markers) {
        if(markers==null || markers.isEmpty())
            return Collections.emptyList();

        GeometryFactory gf = new GeometryFactory();
        List<Location> locations = new ArrayList<Location>(markers.size());
        for(Marker marker : markers) {
            Location loc = new Location();
            loc.setPoint(gf.createPoint(new Coordinate(marker.getLatlng().getLng(), marker.getLatlng().getLat())));
            log.info("creating location at " + loc.getPoint());
            loc.setType((LocationType)marker.getData());
            locations.add(loc);
        }

        return locations;
    }

    @PostConstruct
    private void init() {
        this.event = new Event();
        this.event.setRequirements(new ArrayList<Skill>()); // because of skills component
        this.event.setCapacity(0); // because of spinner component
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setMapBean(MapBean mapBean) {
        this.mapBean = mapBean;
    }

    public void setSignedMember(Member signedMember) {
        this.signedMember = signedMember;
    }
}
