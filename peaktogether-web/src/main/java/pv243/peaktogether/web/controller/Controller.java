package pv243.peaktogether.web.controller;

import pv243.peaktogether.model.Member;
import pv243.peaktogether.model.Skill;
import pv243.peaktogether.model.Sport;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@ManagedBean
@ViewScoped
public class Controller implements Serializable {

    @Inject
    private Places places;

    private String query;

    private String result;



    public void submit() {
        Places.Location loc = places.findByQuery(query);
        if(loc==null){
            result = "error";
        } else {
            result = loc.getLat() + ", " + loc.getLon();
        }
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
