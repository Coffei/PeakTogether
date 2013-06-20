package pv243.peaktogether.web.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.model.Event;

@ManagedBean
@RequestScoped
public class EventSearch {

	private Double radius;
	private Double longtitude;
	private Double latitude;

	private List<Event> result;

	@Inject
	private Logger logger;

	@Inject
	private EventDAOInt eventDAO;

	public EventSearch() {
	}

	public String search() {

		return "result?faces-redirect=true&radius=" + this.radius + "&longtitude=" + this.longtitude + "&latitude="
				+ this.latitude;
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
			this.result = performSearch();
			logger.info("query result size:" + this.result.size());

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

	public List<Event> getResult() {
		return result;
	}

	public void setResult(List<Event> result) {
		this.result = result;
	}

}
