package com.reecepy.web.helper;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utils to work with session
 *
 * @author  Patrick Stillhart
 */

public abstract class SessionUtils {

    /**
     * Creates a FaceMessage as information for the user
     *
     * @param message the message to display
     */
    public static void createMessage(String message) {
        createMessage(message, FacesMessage.SEVERITY_ERROR);
    }

    /**
     * Creates a FaceMessage as information for the user
     *
     * @param message  the message to display
     * @param severity Enum for the typ of information
     */
    public static void createMessage(String message, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
    }

    /**
     * Returns current session
     *
     * @return current session
     */
    public static HttpSession getSession() {
        return (HttpSession)
                FacesContext.
                        getCurrentInstance().
                        getExternalContext().
                        getSession(false);
    }

    /**
     * Gets the users ip
     *
     * @return the ip
     */
    public static String getIp() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }


}