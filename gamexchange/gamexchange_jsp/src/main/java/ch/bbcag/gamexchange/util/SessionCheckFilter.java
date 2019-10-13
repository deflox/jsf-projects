package ch.bbcag.gamexchange.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class SessionCheckFilter
 */
@WebFilter(
	urlPatterns = "/*",
	initParams = @WebInitParam(name = "avoid-urls", value = "index.jsp,register.jsp,register,login.jsp,login,style.css,sweetalert.css,jquery.min.js,pwstrenght.js,sweetalert.min.js")       
)
public class SessionCheckFilter implements Filter {

	private String contextPath;
	private ArrayList<String> urlList;
	
    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
    	contextPath = config.getServletContext().getContextPath();
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");

        urlList = new ArrayList<String>();

        while (token.hasMoreTokens()) {
          urlList.add(token.nextToken().trim());
        }
    }

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    String url = request.getServletPath().replace("/", "");
	    boolean allowedRequest = false;    
	    
	    if(url.contains("webjars") || url.contains("style.css")){
	      allowedRequest = true;
	    }
	    
	    if(urlList.contains(url)) {
	      allowedRequest = true;
	    }
	         
	    if (!allowedRequest && request.getSession().getAttribute("user") == null) {
	      response.sendRedirect(contextPath + "/login.jsp");
	    }
	    else{
	      chain.doFilter(req, res);
	    }
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
