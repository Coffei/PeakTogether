package pv243.peaktogether.util;

import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 22.6.13
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class MapUtils {

    Logger log = Logger.getLogger(this.getClass().getName());

    private static final int GLOBE_WIDTH = 256;
    private static final int GLOBE_HEIGHT = 256;
    private static final int MAX_ZOOM = 21;
    private static final double EARTH_PERIMETER = 40041.455;

    private double lastAvgLat;
    private double lastAvgLon;


    public int computeZoom(double lat, double lon, double distanceInKm, int mapWidth, int mapHeight) {
        double diffLat = (360*distanceInKm)/EARTH_PERIMETER;

        return computeZoom(lat + diffLat, lon, lat - diffLat, lon, mapWidth, mapHeight);
    }


    public int computeZoom(List<Marker> markers, int mapWidth, int mapHeight) {
        if(markers==null || markers.isEmpty())
            return MAX_ZOOM;

        Double latMax, lonMax, latMin, lonMin;
        latMax = lonMax = latMin = lonMin = null;

        //we need to find max and min lat and lon
        for(Marker marker : markers) {
            LatLng coords = marker.getLatlng();
            if(latMax==null || coords.getLat() > latMax.doubleValue())
                latMax = Double.valueOf(coords.getLat());
            if(latMin==null || coords.getLat() < latMin.doubleValue())
                latMin = Double.valueOf(coords.getLat());
            if(lonMax==null || coords.getLng() > lonMax.doubleValue())
                lonMax = Double.valueOf(coords.getLng());
            if(lonMin==null || coords.getLng() < lonMin.doubleValue())
                lonMin = Double.valueOf(coords.getLng());
        }


        return computeZoom(latMax, lonMax, latMin, lonMin, mapWidth, mapHeight);
    }

    public int computeZoom(double latMax, double lonMax, double latMin, double lonMin, int mapWidth, int mapHeight) {
        log.info("latMax " + latMax);
        log.info("latMin " + latMin);
        log.info("lonMax " + lonMax);
        log.info("lonMin " + lonMin);

        double latFraction = (latRad(latMax) - latRad(latMin)) / Math.PI;

        double lngDiff = lonMax - lonMin;
        double lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;

        int latZoom = zoom(mapHeight, GLOBE_HEIGHT, latFraction); //watchout! hardcoded values or map dimensions
        int lngZoom = zoom(mapWidth, GLOBE_WIDTH, lngFraction);

        int computedZoom =  Math.min(latZoom, lngZoom);
        return Math.min(computedZoom, MAX_ZOOM);
    }

    public void computeGeoAverage(List<Marker> markers) {
        double lat = 0;
        double lon = 0;
        for (Marker marker : markers) {
            lat += marker.getLatlng().getLat();
            lon += marker.getLatlng().getLng();
        }



        int markersCount = markers.size();
        if(markersCount > 0) {
            lat /= markersCount;
            lon /= markersCount;
        }

        this.lastAvgLat = lat;
        this.lastAvgLon = lon;
    }

    private double latRad(double lat) {
        double sin = Math.sin(lat * Math.PI / 180);
        double radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
    }

    private int zoom(double mapPx, double worldPx, double fraction) {
        return (int) Math.floor(Math.log(mapPx / worldPx / fraction) / Math.log(2));
    }


    public double getLastAvgLat() {
        return lastAvgLat;
    }

    public void setLastAvgLat(double lastAvgLat) {
        this.lastAvgLat = lastAvgLat;
    }

    public double getLastAvgLon() {
        return lastAvgLon;
    }

    public void setLastAvgLon(double lastAvgLon) {
        this.lastAvgLon = lastAvgLon;
    }
}
