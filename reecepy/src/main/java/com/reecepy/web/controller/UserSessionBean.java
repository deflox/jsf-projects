package com.reecepy.web.controller;


import com.reecepy.ejb.controller.UserManagementBeanLocal;
import com.reecepy.ejb.exceptions.AttemptException;
import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.user.User;
import com.reecepy.web.helper.ActivityBean;
import com.reecepy.web.helper.SessionUtils;
import com.reecepy.web.helper.UrlUtils;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Holder for session information
 *
 * @author  Patrick Stillhart
 */
@ManagedBean
@SessionScoped
public class UserSessionBean implements Serializable {

    private final static Logger LOGGER = Logger.getLogger(UserManagementBeanLocal.class.getName());

    /**
     * General login status
     */
    private boolean isLoggedIn;

    /**
     * true if the user logged out himself
     */
    private boolean loggedOut;
    private User user;

    @EJB
    private UserManagementBeanLocal userManagementBeanLocal;

    @ManagedProperty("#{activityBean}")
    private ActivityBean activityBean;

    public UserSessionBean() {
        user = new User();
        isLoggedIn = loggedOut = false;
    }

    /**
     * Register a new User
     */
    public void registerUser(User user) throws ValidationException {
        userManagementBeanLocal.register(user);
    }

    /**
     * Logs a user in
     *
     * @param credentials The login credentials
     */
    public void login(User credentials) throws AttemptException, ValidationException {
        String ip = SessionUtils.getIp();
        user = userManagementBeanLocal.login(ip, credentials);
        // only happens if no exception happened
        isLoggedIn = true;

    }

    /**
     * Checks weather the user is or isn't logged in
     *
     * @return true if logged in
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Logs the current User out
     *
     * @return HomePage
     */
    public String logout() {
        activityBean.remove(user.getEmail());

        loggedOut = true;
        isLoggedIn = false;
        user = null;

        return UrlUtils.HOME_PAGE + UrlUtils.REDIRECT_PARAMETER;
    }

    /**
     * Updates the current user on the db
     *
     * @param updateUser            The new user values
     * @param hashPassword          Should the password be hashed
     * @throws ValidationException  Formal field error
     */
    public void updateUser(User updateUser, boolean hashPassword) throws ValidationException {
        userManagementBeanLocal.updateUser(user, updateUser, hashPassword);
        user = updateUser;
        activityBean.remove(user.getEmail());
    }

    public boolean availableCircleMember(String email) {
        if(user.getEmail().equals(email)) return false;
        return userManagementBeanLocal.availableCircleMember(email);
    }

    /**
     * Adds a Member to a circle
     *
     * @param email the user from the user to add
     */
    public void addMemberToCircle(String email) {
        userManagementBeanLocal.addMemberToCircle(email, user);
        activityBean.notifyUser(email);
    }

    public int getUserCountForCircle() {
        return userManagementBeanLocal.getUserCountForCircle(user);
    }

    /**
     * Gets all circle members for current logged in user.
     *
     * @return list with members (current at first position)
     */
    public List<User> getCircleMembers() {

        List<User> unsortedMembers = userManagementBeanLocal.getCircleMemberForUser(user);

        // Set the current user as first entry in list
        User temp;
        for(int i = 0; i < unsortedMembers.size(); i++) {
            if(user.getUserId() == unsortedMembers.get(i).getUserId()) {
                temp = unsortedMembers.get(0);
                unsortedMembers.set(0, unsortedMembers.get(i));
                unsortedMembers.set(i, temp);
            }
        }

        return unsortedMembers;

    }

    public void removeCircleMember(String emailToRemove) {
        userManagementBeanLocal.removeCircleMember(emailToRemove, user);
        activityBean.notifyUser(emailToRemove);
    }

    public User getUser() {
        if (activityBean.checkAndRemove(user.getEmail())) user = userManagementBeanLocal.getUserById(user.getUserId());
        return user;
    }

    @PreDestroy
    public void onDestroy() {
        if(isLoggedIn) activityBean.remove(user.getEmail());
    }

    /*
    JSF Getter & setter
     */

    public void setActivityBean(ActivityBean activityBean) {
        this.activityBean = activityBean;
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }
}