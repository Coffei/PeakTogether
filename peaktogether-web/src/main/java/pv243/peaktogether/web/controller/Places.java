package pv243.peaktogether.web.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 24.6.13
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class Places {
    private static final String GOOGLE_API_KEY = "AIzaSyBZlPGFtQj-2jLYqLNQ9L7iTZtaPvClno0";
    private static final String GOOGLE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/";

    @Inject
    private Logger log;


    public Location findByQuery(String query) {
        String url = buildUrl(query);
        InputStream stream = doGet(url);
        Location loc = parseLocation(stream);

        return loc;
    }

    private InputStream doGet(String url) {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        try {
            HttpResponse response = client.execute(get);
            return response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //in case of error
        return null;
    }

    private String buildUrl(String query) {
        StringBuilder builder = new StringBuilder(GOOGLE_URL);
        builder.append("xml?"); //format
        builder.append("key="); //google api key
        builder.append(GOOGLE_API_KEY);

        builder.append("&sensor=false&query="); //query
        builder.append(query);

        log.info("url: " + builder.toString());

        return builder.toString();
    }

    private Location parseLocation(InputStream stream) {
        if(stream == null)
            return null;

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(stream);
            XMLOutputter out = new XMLOutputter();
            log.info("document: " + out.outputString(doc));
        } catch (JDOMException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        if(doc!=null) {
            XPathExpression<Element> statusXpath = XPathFactory.instance().compile("/PlaceSearchResponse[status=\"OK\"]", Filters.element());
            XPathExpression<Element> locationXpath = XPathFactory.instance().compile("/PlaceSearchResponse/result/geometry/location", Filters.element());

            if(!statusXpath.evaluate(doc).isEmpty()) { //we can continue
                Element location = locationXpath.evaluateFirst(doc);
                if(location!=null) {
                    String slat = location.getChildText("lat");
                    String slon = location.getChildText("lng");

                    if(slat!=null && slon!= null) {
                        try {
                            Double lat = Double.parseDouble(slat);
                            Double lon = Double.parseDouble(slon);

                            return new Location(lat, lon);
                        } catch (NumberFormatException e) {
                            //Should not happen
                            log.severe("Google api doesn't fit");
                            log.severe(e.toString());
                        }
                    } else {
                        log.info("wrong slat, slons");
                    }
                } else {
                    log.info("no location element");
                }
            } else {
                log.info("status NOK");
            }
        }

        //in case of error
        return null;
    }

    public static class Location {
        private double lat;
        private double lon;

        public Location(){}
        public Location(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }
}
