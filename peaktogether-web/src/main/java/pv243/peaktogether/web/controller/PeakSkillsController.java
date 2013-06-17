package pv243.peaktogether.web.controller;

import pv243.peaktogether.model.Skill;
import pv243.peaktogether.model.Sport;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.Arrays;
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
        where.add(newSkill);
    }

    public void removeSkill(List<Skill> from, Skill what) {
        from.remove(what);
    }

    public List<Sport> getSportValues() {
        return Arrays.asList(Sport.values());
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
