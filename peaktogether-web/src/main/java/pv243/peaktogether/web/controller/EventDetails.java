package pv243.peaktogether.web.controller;

import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.model.Event;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private Event event;

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
            } catch(NumberFormatException e) {}
        }
    }

    private SimpleDateFormat format = new SimpleDateFormat("dd MMMM HH:mm", Locale.ENGLISH);
    public String formatDate(Date date) {
        if(date==null)
            return "";
        return format.format(date);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
