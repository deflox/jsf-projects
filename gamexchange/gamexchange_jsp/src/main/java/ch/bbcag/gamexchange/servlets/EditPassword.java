package ch.bbcag.gamexchange.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class EditPassword
 */
@WebServlet(urlPatterns = {"/passwordedit"})
public class EditPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String oldpw = request.getParameter("currentpassword");
		String newpw = request.getParameter("newpassword");
		String newpwrepeat = request.getParameter("newpasswordrepeat");
		
		HttpSession session = request.getSession();
		User sessionuser = (User) session.getAttribute("user");
		
		UsersDaoImpl iDAO = new UsersDaoImpl();
		
		if (BCrypt.checkpw(oldpw, sessionuser.getPassword())) {
			
			if (newpw.equals(newpwrepeat)) {
				
				User u = new User();
				
				u.setUserid(sessionuser.getUserid());
				u.setEmail(sessionuser.getEmail());
				u.setPassword(BCrypt.hashpw(newpw, BCrypt.gensalt(12)));
				u.setFirstname(sessionuser.getFirstname());
				u.setLastname(sessionuser.getLastname());
				u.setStreet(sessionuser.getStreet());
				u.setStreetnumber(sessionuser.getStreetnumber());
				u.setCity(sessionuser.getCity());
				u.setPostalcode(sessionuser.getPostalcode());
				u.setCountry(sessionuser.getCountry());
				
				out.println("Password matches we would do insert now with following data:");
				out.println(u);
				out.println(newpw);
				
				iDAO.updateUser(u);
				
				session.setAttribute("user", u);

				response.sendRedirect("dashboard.jsp");
				
			} else {
				
				out.println("The both new password do not match.");
				
			}
			
		} else {
			
			out.println("Entered old password does not match.");
			
		}
		
	}

}
