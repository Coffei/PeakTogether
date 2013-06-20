/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pv243.peaktogether.web.controller.security;

import org.picketlink.Identity;
import org.picketlink.Identity.AuthenticationResult;
import pv243.peaktogether.dao.MemberDAOInt;
import pv243.peaktogether.model.Member;

import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * We control the authentication process from this action bean, so that in the event of a failed authentication we can add an
 * appropriate FacesMessage to the response.
 *
 * @author Shane Bryzak
 *
 */
@ManagedBean
@RequestScoped
public class LoginController {

    @Inject
    private Identity identity;


    @Inject
    private MemberDAOInt memberDao;

    @Inject
    private Logger log;

    @Produces
    @Named("signedMember")
    public Member loggedMember() {
        if(!identity.isLoggedIn()) {
            return null;
        } else {
            String email = identity.getUser().getLoginName();
            return memberDao.findByEmail(email);
        }
    }

    public void login()  {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        log.info("login");
        AuthenticationResult result = identity.login();
        if (AuthenticationResult.FAILED.equals(result)) {
            facesContext.addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong email or password.", ""));
        } else {
            try {
                facesContext.getExternalContext().redirect("index.jsf");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public String logout() {
        identity.logout();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User logged out!", ""));
        return "login?faces-redirect=true";

    }

}
