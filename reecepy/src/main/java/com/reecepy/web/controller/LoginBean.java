package com.reecepy.web.controller;

import com.reecepy.ejb.exceptions.AttemptException;
import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.user.User;
import com.reecepy.web.helper.SessionUtils;
import com.reecepy.web.helper.UrlUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Manages login
 * Logs user in
 *
 * @author  Patrick Stillhart
 */
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    private User credentials;

    public LoginBean() {
        credentials = new User();

        String message = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("message");
        if (message != null) SessionUtils.createMessage(message);

    }

    /**
     * tires to login a user
     *
     * @return the next page if successful
     */
    public String login() {
        try {
            userSessionBean.login(credentials);

            return UrlUtils.forward(UrlUtils.Dashboard);
        } catch (AttemptException | ValidationException e) {
            SessionUtils.createMessage(e.getMessage());
            return null;
        }

    }

    /*
    JSF Getter & setter
     */

    public boolean activateBean() {
        return true;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public User getCredentials() {
        return credentials;
    }

    public void setCredentials(User credentials) {
        this.credentials = credentials;
    }
}
