package pv243.peaktogether.web.controller;

import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.SimpleUser;
import pv243.peaktogether.dao.MemberDAOInt;
import pv243.peaktogether.dao.security.SecurityDAO;
import pv243.peaktogether.model.Member;
import pv243.peaktogether.model.Skill;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 17.6.13
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RegisterController {

    @Inject
    private MemberDAOInt memberDao;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private SecurityDAO securityDAO;


    @Inject
    private Logger log;


    private Member newMember;
    private String password;
    private String verifyPassword;


    public String register() {
        log.info("in register");
        if(this.password==null || this.password.length() <= 3 || this.verifyPassword==null || this.verifyPassword.length() <= 3) {
            log.info("password incorrect");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "password must be longer than 3 characters", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        if(!this.password.equals(this.verifyPassword)) {
            log.info("password unequal");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "passwords don't match!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        newMember.setRegistered(new Date());


        //Create member
        memberDao.create(newMember);
        log.info("after member creation");
        //create identity object
        SimpleUser user = new SimpleUser(newMember.getEmail());
        user.setEmail(newMember.getEmail());
        securityDAO.persistUser(user, new Password(this.password));

        log.info("oncomplete");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered successfully. You can log in now.", null));
        return "login";
    }

    @PostConstruct
    private void init() {
        log.info("RegisteredController initted");
        this.newMember = new Member();
        this.newMember.setSkills(new ArrayList<Skill>());

    }

    //getters, setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public Member getNewMember() {
        return newMember;
    }

}
