package com.reecepy.web.filter;

import com.reecepy.web.controller.UserSessionBean;
import com.reecepy.web.helper.UrlUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Checks if a user have access to a certain page and forwards if not
 *
 * @author  Patrick Stillhart
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"}, dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD })
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Filter for all pages
     * Allow private pages only when logged in
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String reqURI = request.getRequestURI();
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            HttpSession session = request.getSession(true);
            UserSessionBean userSession = (UserSessionBean) session.getAttribute("userSessionBean");

            if (userSession != null && userSession.isLoggedIn() || reqURI.contains("javax.faces.resource")) {// Is logged in OR is resources
                if(isPublicPage(reqURI)) response.sendRedirect(request.getContextPath() + "/" + UrlUtils.Dashboard);
                chain.doFilter(request, servletResponse);
            } else {
                if (userSession != null && !userSession.isLoggedOut() && !isPublicPage(reqURI) || request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid() && !isPublicPage(reqURI)) { // session expired
                    session.setAttribute("message", "Your session timed out, please login again");

                    if (isAJAXRequest(request)) {
                        String redirect = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\""
                                + request.getContextPath()
                                + "/"
                                + UrlUtils.LOGIN_PAGE
                                + "\"></redirect></partial-response>";

                        response.setHeader("Cache-Control", "no-cache");
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("text/xml");
                        PrintWriter pw = response.getWriter();
                        pw.println(redirect);
                        pw.flush();
                        return;
                    }

                    response.sendRedirect(request.getContextPath() + "/" + UrlUtils.LOGIN_PAGE);
                } else if (!isPublicPage(reqURI)) { // is not a public page
                    response.sendRedirect(request.getContextPath() + "/" + UrlUtils.LOGIN_PAGE);
                } else {
                    chain.doFilter(request, servletResponse);
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println("Filter error: " + t.getMessage());
        }

    }

    /**
     * Check if attribute is a private page
     *
     * @param request the page to check
     * @return true if public page
     */
    private boolean isPublicPage(String request) {
        for (String compare : UrlUtils.PUBLIC_PAGES)
            if (request.contains(compare)) return true;
        return false;
    }

    /**
     * Checks if request is an ajax request
     *
     * @param request the request
     * @return true if is ajax
     */
    private boolean isAJAXRequest(HttpServletRequest request) {
        boolean check = false;
        String facesRequest = request.getHeader("Faces-Request");
        if (facesRequest != null && facesRequest.equals("partial/ajax")) {
            check = true;
        }
        return check;
    }

    @Override
    public void destroy() {
    }
}