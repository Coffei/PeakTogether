package pv243.peaktogether.web.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import pv243.peaktogether.model.LocationType;



@ManagedBean
@ViewScoped
public class MapBean implements Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger logger;
	
	private Integer num1;

    private Marker currentMarker;

	private MapModel mapModel;  
    private String title;  
    private double lat;  
    private double lng;  
    private String input;

    public void addMarker(ActionEvent actionEvent) {
    	logger.info("MapBean add marker event ?");
    	
    	LatLng coord = new LatLng(lat,lng); 
    	
    	String iconUrl;
    	
    	if (this.location == LocationType.START) {
    		logger.info("green icon");
    		iconUrl = "http://maps.google.com/mapfiles/ms/micons/green-dot.png";
    	}
    	else if (this.location == LocationType.END) {
    		logger.info("red icon");
    		iconUrl = "http://maps.google.com/mapfiles/ms/micons/red-dot.png";
    	} 
    	else {
    		logger.info("yellow icon");
    		iconUrl = "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png";
    	}
    	
    	Marker marker = new Marker(coord, this.getTitle(),this.location,iconUrl);
    	marker.setDraggable(true);

        mapModel.addOverlay(marker);

        this.title = "";
        this.location = null;
        
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));  
    }  

    public void submit() {
    	for (Marker marker: mapModel.getMarkers()) {
    		
    		logger.info("lat: "+marker.getLatlng().getLat()+", lng: "+marker.getLatlng().getLng());
    	}
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Form submitted", "Amount markers: " + mapModel.getMarkers().size() + ", Input: " + input));
    }

    public void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        currentMarker = (Marker) event.getOverlay();
    }

    public void deleteCurrentMarker() {

        mapModel.getMarkers().remove(currentMarker);
        logger.info("Ater: " + mapModel.getMarkers());
    }

    @PostConstruct
    public void init() {
        logger.info("initted");
        this.locations = Arrays.asList(LocationType.values());
        mapModel = new DefaultMapModel();

    }


    public void onMarkerDrag(MarkerDragEvent event) {
    	logger.info("DRAGGING !!!");
    	
        Marker marker = event.getMarker();  
        
        logger.info("Lat:" + marker.getLatlng().getLat() + ", Lng:" + marker.getLatlng().getLng());
    }
    
    public Marker getCurrentMarker() {
        return currentMarker;
    }

    public void setCurrentMarker(Marker currentMarker) {
        this.currentMarker = currentMarker;
    }

    public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	private LocationType location;
	private List<LocationType> locations;
	
	public LocationType getLocation() {
		return location;
	}

	public void setLocation(LocationType location) {
		this.location = location;
	}

	public List<LocationType> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationType> locations) {
		this.locations = locations;
	}


	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}