package pv243.peaktogether.web.controller;

import pv243.peaktogether.model.Member;
import pv243.peaktogether.model.Skill;
import pv243.peaktogether.model.Sport;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@ManagedBean
@ViewScoped
public class Controller implements Serializable {

    private List<Skill> skills;

    public List<Skill> getSkills() {
      return skills;
    }

    @PostConstruct
    private void init() {
        skills = new ArrayList<Skill>();
        Skill skill = new Skill();
        skill.setSport(Sport.CANOEING);
        skill.setLevel(5);

        Skill skill2 = new Skill();
        skill2.setSport(Sport.KAYAKING);
        skill2.setLevel(6);

        Skill skill3 = new Skill();
        skill3.setSport(Sport.HIKING);
        skill3.setLevel(2);

        Skill skill4 = new Skill();
        skill4.setSport(Sport.CLIMBING);
        skill4.setLevel(9);

        skills.addAll(Arrays.asList(skill, skill2, skill3, skill4));
    }

    public List<Skill> getMemberSkills(Member member) {
        if(member==null || member.getSkills()==null)
            return Collections.emptyList();

        return new ArrayList<Skill>(member.getSkills());
    }

    public void addMessage() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Testing message!", null));
    }

}
