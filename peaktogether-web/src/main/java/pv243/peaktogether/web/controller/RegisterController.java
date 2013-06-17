package pv243.peaktogether.web.controller;

import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.SimpleUser;
import pv243.peaktogether.dao.MemberDAOInt;
import pv243.peaktogether.dao.security.SecurityDAO;
import pv243.peaktogether.model.Member;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 17.6.13
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
public class RegisterController {

    @Inject
    private MemberDAOInt memberDao;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private SecurityDAO securityDAO;

    @Inject
    private FacesContext facesContext;

    private Member newMember;
    private String password;


    public String register() {
        if(this.password==null || this.password.length() <= 3) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "password must be longer than 3 characters", "");
            throw new ValidatorException(msg);
        }
        newMember.setRegistered(new Date());

        //Create member
        memberDao.create(newMember);

        //create identity object
        SimpleUser user = new SimpleUser(newMember.getEmail());
        user.setEmail(newMember.getEmail());
        securityDAO.persistUser(user, new Password(this.password));

        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered successfully. You can log in now.", null));
        return "login";
    }

    @PostConstruct
    private void init() {
        this.newMember = new Member();


    }

    //getters, setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member getNewMember() {
        return newMember;
    }

}
