package ch.bbcag.gamexchange.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.bbcag.gamexchange.db.BCrypt;
import ch.bbcag.gamexchange.db.UsersDaoImpl;
import ch.bbcag.gamexchange.models.User;

/**
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//PrintWriter out = response.getWriter();
		
		String useremail = request.getParameter("useremail");
		String pw        = request.getParameter("userpassword");
		
		response.setContentType("text/html;charset=UTF-8");
		UsersDaoImpl users = new UsersDaoImpl();
		
		RequestDispatcher dispatcherError = request.getRequestDispatcher("login.jsp");
		
		HttpSession session = request.getSession();          
		
		if (!(session.getAttribute("user") == null)) {
			
			session.removeAttribute("user");
		
		}	
			
		User u = users.selectUserByEmail(useremail);
			
		if (users.selectUserByEmail(useremail) != null) {
				
			if (BCrypt.checkpw(pw, u.getPassword())) {
				
				session.setAttribute("user", u);
				response.sendRedirect("dashboard.jsp");
					
			} else {
					
				request.setAttribute("error", "Login failed. Are you sure the given data is correct?");
                dispatcherError.forward(request, response);
					
			}
				
		} else {
				
			request.setAttribute("error", "Login failed. Are you sure the given data is correct?");
            dispatcherError.forward(request, response);
				
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
	}

}
