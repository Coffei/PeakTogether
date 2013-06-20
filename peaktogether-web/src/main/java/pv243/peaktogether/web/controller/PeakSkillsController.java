package pv243.peaktogether.web.controller;

import pv243.peaktogether.model.Skill;
import pv243.peaktogether.model.Sport;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 17.6.13
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
public class PeakSkillsController {

    @Inject
    private Logger log;

    private Skill newSkill;

    public void addSkill(List<Skill> where)  {
        log.info("Adding " + newSkill);
        log.info("Before: " + where);
        where.add(newSkill);
        log.info("After: " + where);
    }

    public void removeSkill(List<Skill> from, Skill what) {
        log.info("Removing " + what);
        log.info("Before: " + from);
        from.remove(what);
        log.info("After: " + from);
    }

    public List<Sport> getSportValues(List<Skill> skills) {
        List<Sport> sports = new ArrayList<Sport>(Arrays.asList(Sport.values()));
        sports.removeAll(getUsedValues(skills));

        return sports;
    }

    private List<Sport> getUsedValues(List<Skill> skills) {
        if(skills==null)
            return Collections.emptyList();

        List<Sport> used = new ArrayList<Sport>();
        for(Skill skill : skills) {
            used.add(skill.getSport());
        }

        return used;
    }

    @PostConstruct
    private void init() {
        newSkill = new Skill();
    }

    public Skill getNewSkill() {
        return newSkill;
    }

    public void setNewSkill(Skill newSkill) {
        this.newSkill = newSkill;
    }
}
