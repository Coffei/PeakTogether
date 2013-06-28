package pv243.peaktogether.web.controller;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.Member;
import pv243.peaktogether.util.MapUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 24.6.13
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class EventDetails {

    @Inject
    private EventDAOInt eventDao;

    @Inject
    @Named("signedMember")
    private transient Member signedMember;

    private MapModel mapModel;
    private String center;
    private String zoom;


    private Event event;

    private boolean joined;

    public void changedAttendance() {
        if(joined) {
            if(this.event.isLimited()!=null && event.isLimited().booleanValue() && event.getCapacity() <= event.getJoinedMembers().size()) {
                //you can not join the event
                this.joined=false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "The event has reached its maximum capacity!", null));
                return;
            }

            this.event.getJoinedMembers().add(signedMember);
            eventDao.update(event);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "You have successfully joined the event!", null));

        } else {

            this.event.getJoinedMembers().remove(signedMember);
            eventDao.update(this.event);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "You have successfully left the event!", null));
        }
    }

    public boolean isFull() {
        if(event.isLimited() && event.getJoinedMembers()!=null) {
            return this.event.getJoinedMembers().size() >= event.getCapacity();
        }

        return false;
    }

    @PostConstruct
    private void init() {
        String sid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(sid==null) { // if no id param, redirect to index
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
                return;
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            try {
                Long id = Long.parseLong(sid);
                this.event = eventDao.findById(id);
                this.joined = event.getJoinedMembers().contains(signedMember);
                setupMap();
            } catch(NumberFormatException e) {}
        }
    }

    private void setupMap() {
        mapModel = new DefaultMapModel();
        List<Marker> markers = createMarkersFromLoations(event.getLocations());
        for(Marker marker : markers)  {
            mapModel.addOverlay(marker);
        }

        generateMapParams();

    }

    private void generateMapParams() {
        MapUtils mapUtils = new MapUtils();
        mapUtils.computeGeoAverage(mapModel.getMarkers());
        center = mapUtils.getLastAvgLat() + ", " + mapUtils.getLastAvgLon();
        zoom = String.valueOf(mapUtils.computeZoom(mapModel.getMarkers(), 700, 300));
    }

    private List<Marker> createMarkersFromLoations(List<Location> locations) {
        List<Marker> markers = new ArrayList<Marker>(locations.size());
        for(Location loc : locations) {
            Marker marker = new Marker(new LatLng(loc.getPoint().getCoordinate().y, loc.getPoint().getCoordinate().x));
            //marker.setTitle();
            marker.setData(loc);
            String iconUrl;
            if (loc.getType() == LocationType.START) {
                iconUrl = "http://maps.google.com/mapfiles/ms/micons/green-dot.png";
            }
            else if (loc.getType() == LocationType.END) {
                iconUrl = "http://maps.google.com/mapfiles/ms/micons/red-dot.png";
            }
            else {
                iconUrl = "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png";
            }
            marker.setIcon(iconUrl);
            marker.setTitle("Description: "+loc.getTitle()+"\n Type:"+loc.getType().toString());

            markers.add(marker);

        }

        return markers;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
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
}
