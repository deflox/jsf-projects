package com.reecepy.web.controller;

import com.reecepy.ejb.exceptions.AttemptException;
import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.user.User;
import com.reecepy.web.helper.SessionUtils;
import com.reecepy.web.helper.UrlUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

/**
 * Manages a register view.
 * Registers a new user
 *
 * @author  Patrick Stillhart
 */
@ManagedBean
@RequestScoped
public class RegisterBean implements Serializable{

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    private User user;

    private String passwordRepeat;

    public RegisterBean() {
        user = new User();

    }

    /**
     * Registers a new user
     *
     * @return the next page
     */
    public String register() {

        if (!user.getPassword().equals(passwordRepeat)) {
            SessionUtils.createMessage("The Passwords don't match");
            return null;
        }

        try {
            user.setAddable(true);
            userSessionBean.registerUser(user);
        } catch (ValidationException e) {
            SessionUtils.createMessage(e.getMessage());
            return null;
        }

        try {
            // Set plain password again since it will be encrypted during register process
            user.setPassword(passwordRepeat);
            userSessionBean.login(user);
            return UrlUtils.forward(UrlUtils.Dashboard);
        } catch (AttemptException | ValidationException e) {
            return UrlUtils.forward(UrlUtils.HOME_PAGE);
        }




    }

    /*
    JSF Getter & setter
     */

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
