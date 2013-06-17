package pv243.peaktogether.web.controller;

import pv243.peaktogether.model.Skill;
import pv243.peaktogether.model.Sport;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ManagedBean
@ViewScoped
public class Controller {

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

}
