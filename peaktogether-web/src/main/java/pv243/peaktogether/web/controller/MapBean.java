package pv243.peaktogether.web.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;



@ManagedBean
@ViewScoped
public class MapBean implements Serializable {  

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	private MapModel mapModel;  
    private String title;  
    private double lat;  
    private double lng;  
    private String input;

    public MapBean() {  
        mapModel = new DefaultMapModel();  
    }  

    public void addMarker(ActionEvent actionEvent) {  
        mapModel.addOverlay(new Marker(new LatLng(lat, lng), title));  
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));  
    }  

    public void submit() {
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Form submitted", "Amount markers: " + mapModel.getMarkers().size() + ", Input: " + input));
    }

    public void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  

    // Getters+setters.
}