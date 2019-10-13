package com.reecepy.web.controller;

import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.user.User;
import com.reecepy.ejb.utils.BCrypt;
import com.reecepy.web.helper.SessionUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Manages a settings view
 * Prepares data for user edits
 *
 * @author  Leo Rudin
 */
@ManagedBean
@RequestScoped
public class PreferencesBean implements Serializable {

    private final static Logger LOGGER = Logger.getLogger(PreferencesBean.class.getName());

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    // Profile
    private User user;

    // Circle
    private String addCircleMemberMail;
    private String addCircleMemberAnswer;

    private List<User> circleMembers;

    // New password
    private String currentPassword;
    private String newPassword;
    private String newPasswordRepeat;

    private boolean personalDataError = false;
    private boolean changePasswordError = false;

    @PostConstruct
    public void init() {
        user = userSessionBean.getUser().getCopy();
        circleMembers = userSessionBean.getCircleMembers();
    }

    /**
     * Updates the personal section from the site to the db
     */
    public void updatePersonalData() {

        try {
            userSessionBean.updateUser(user, false);
            // TODO: add success message
        } catch (ValidationException e) {
            this.personalDataError = true;
            this.changePasswordError = false;
            SessionUtils.createMessage(e.getMessage());
        }

    }

    /**
     * Updates the password section from the site to the db
     */
    public String updatePassword() {
        this.changePasswordError = true;
        this.personalDataError = false;

        if (!BCrypt.checkpw(currentPassword, userSessionBean.getUser().getPassword())) {
            SessionUtils.createMessage("Current password is not correct.");
            return null;
        }

        if (!newPassword.equals(newPasswordRepeat)) {
            SessionUtils.createMessage("The Passwords don't match.");
            return null;
        }

        User user = userSessionBean.getUser();
        user.setPassword(newPassword);

        try {
            userSessionBean.updateUser(user, true);
            return null;
        } catch (ValidationException e) {
            SessionUtils.createMessage(e.getMessage());
            return null;
        }

    }

    /**
     * Sets the addable status from the user
     */
    public void saveAddable() {
        try {
            userSessionBean.updateUser(userSessionBean.getUser(), false);
        } catch (ValidationException e) {
            // Should not happen because we load the user from the db
            LOGGER.severe("validationException while saving addable for User: " + userSessionBean.getUser().getUserId() + " :: Message: " + e.getMessage());
        }
    }

    /**
     * Checks if a user (with email) is available to be added to a cricle
     */
    public void checkMemberEmail() {
        if(userSessionBean.availableCircleMember(addCircleMemberMail)) addCircleMemberAnswer = "true";
        else addCircleMemberAnswer = "false";

    }

    /**
     * Adds a member to the own circle
     */
    public void addMember() {
        userSessionBean.addMemberToCircle(addCircleMemberMail);
        circleMembers = userSessionBean.getCircleMembers();
        addCircleMemberMail = "";

    }

    /**
     * Removes a member to the own circle
     */
    public void removeMember(String email) {
        userSessionBean.removeCircleMember(email);
        circleMembers = userSessionBean.getCircleMembers();
    }


    /*
    JSF Getter & setter
     */
    public List<User> getCircleMembers() {
        return circleMembers;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public User getUser() {
        return user;
    }

    public String getAddCircleMemberMail() {
        return addCircleMemberMail;
    }

    public void setAddCircleMemberMail(String addCircleMemberMail) {
        this.addCircleMemberMail = addCircleMemberMail;
    }

    public String getAddCircleMemberAnswer() {
        return addCircleMemberAnswer;
    }

    public void setAddCircleMemberAnswer(String addCircleMemberAnswer) {
        this.addCircleMemberAnswer = addCircleMemberAnswer;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

    public boolean isPersonalDataError() {
        return personalDataError;
    }

    public boolean isChangePasswordError() {
        return changePasswordError;
    }

}
