package com.reecepy.web.helper;

import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Utils to work with URLs
 *
 * @author  Patrick Stillhart
 */
public abstract class UrlUtils {

    /**
     * The public home page (without .xhtml)
     */
    public static final String HOME_PAGE = "signin";

    /**
     * The login page (without .xhtml)
     */
    public static final String LOGIN_PAGE = "signin";

    /**
     * The page after login (without .xhtml)
     */
    public static final String Dashboard = "dashboard";

    /**
     * The page with recipes
     */
    public static final String BROWSE = "browse";

    /**
     * Pages that are public
     * Most visited pages should be at the beginning
     */
    public static final String[] PUBLIC_PAGES = new String[] {"signin", "signup"};

    /**
     * Redirect Parameter
     */
    public final static String REDIRECT_PARAMETER = "?faces-redirect=true";

    /**
     * DEFAULT_PATH
     */
    public final static String DEFAULT_PATH = "reecepy";


    /**
     * Forward a user to a side
     */
    public static String forward(String page) {
        return page + REDIRECT_PARAMETER;
    }

    /**
     * Forward a user to a side
     */
    public static String forward(String page, String attributes) {
        return "/" + DEFAULT_PATH + "/" + page + REDIRECT_PARAMETER + " &" + attributes;
    }

    /**
     * Gets a domain parameter
     *
     * @param parameterName the desired parameter / null if empty
     * @return value of the parameter
     */
    public static String getDomainParameter(String parameterName) {
        try {
            return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parameterName);
        } catch (NoSuchElementException e) {
            return null;
        }
    }


}