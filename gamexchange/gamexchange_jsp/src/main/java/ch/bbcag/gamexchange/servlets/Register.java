package ch.bbcag.gamexchange.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.bbcag.gamexchange.db.BCrypt;
import ch.bbcag.gamexchange.db.UsersDaoImpl;
import ch.bbcag.gamexchange.models.User;

/**
 * Servlet implementation class RegisterServlet
 */

@WebServlet(urlPatterns = { "/register" })
public class Register extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// Attributes
	private String email;
	private String password;
	private String passwordrepeat;
	
	private String firstname;
	private String lastname;
	private String street;
	private String streetnumber;
	private String city;
	private String postalcode;
	private String country;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		UsersDaoImpl users = new UsersDaoImpl();
		
		password = request.getParameter("userpassword");
		passwordrepeat = request.getParameter("userpasswordrepeat");
		
		if (password.equals(passwordrepeat)) {
			
			email = request.getParameter("useremail");
			
			if (users.selectUserByEmail(email) == null) {
				
				firstname = request.getParameter("firstname");
				lastname = request.getParameter("lastname");
				street = request.getParameter("street");
				streetnumber = request.getParameter("streetnumber");
				city = request.getParameter("city");
				postalcode = request.getParameter("postalcode");
				country = request.getParameter("country");		 

				User u = new User();
				
				u.setEmail(email);
				u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
				u.setFirstname(firstname);
				u.setLastname(lastname);
				u.setStreet(street);
				u.setStreetnumber(streetnumber);
				u.setCity(city);
				u.setPostalcode(postalcode);
				u.setCountry(country);
				
				users.insertUser(u);
				
				response.sendRedirect("login.jsp");
				
			} else {
				
				out.println("This email already exists. Please choose another one.");
				
			}
			
		} else {
			
			out.println("The both passwords do not match. Please try again.");
			
		}
		
	}
	
	

}
