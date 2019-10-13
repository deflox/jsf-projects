package ch.bbcag.gamexchange.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.bbcag.gamexchange.db.UsersDaoImpl;
import ch.bbcag.gamexchange.models.User;

/**
 * Servlet implementation class EditUser
 */
@WebServlet(urlPatterns = {"/useredit"})
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String email;
	
	private String firstname;
	private String lastname;
	private String street;
	private String streetnumber;
	private String city;
	private String postalcode;
	private String country;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		UsersDaoImpl iDAO = new UsersDaoImpl();
		
		HttpSession session = request.getSession();
		
		// Gets user from session and get current db user
		User sessionuser = (User) session.getAttribute("user");
		User currentdbuser = iDAO.selectUserByEmail(sessionuser.getEmail());	
		
		email = request.getParameter("useremail");
			
		// Check if user has entered the same email as saved in the session, to avoid
		// checking the database for duplicate email, since email already exists in database
		// which would result in producing error, that entered email already exists. 
		if (email.equals(sessionuser.getEmail())) {
				
			// Get form input
			firstname = request.getParameter("firstname");
			lastname = request.getParameter("lastname");
			street = request.getParameter("street");
			streetnumber = request.getParameter("streetnumber");
			city = request.getParameter("city");
			postalcode = request.getParameter("postalcode");
			country = request.getParameter("country");		

			// Set user and call update function
			User u = new User();	
			u.setUserid(sessionuser.getUserid());
			u.setEmail(email);
			u.setPassword(currentdbuser.getPassword());
			u.setFirstname(firstname);
			u.setLastname(lastname);
			u.setStreet(street);
			u.setStreetnumber(streetnumber);
			u.setCity(city);
			u.setPostalcode(postalcode);
			u.setCountry(country);
			iDAO.updateUser(u);
			
			// Update session
			session.setAttribute("user", u);
				
			response.sendRedirect("dashboard.jsp");
				
		} else {
				
			if (iDAO.selectUserByEmail(email) == null) {
					
				// Get form input
				firstname = request.getParameter("firstname");
				lastname = request.getParameter("lastname");
				street = request.getParameter("street");
				streetnumber = request.getParameter("streetnumber");
				city = request.getParameter("city");
				postalcode = request.getParameter("postalcode");
				country = request.getParameter("country");		

				// Set user and call update function
				User u = new User();	
				u.setUserid(sessionuser.getUserid());
				u.setEmail(email);
				u.setPassword(currentdbuser.getPassword());
				u.setFirstname(firstname);
				u.setLastname(lastname);
				u.setStreet(street);
				u.setStreetnumber(streetnumber);
				u.setCity(city);
				u.setPostalcode(postalcode);
				u.setCountry(country);
				iDAO.updateUser(u);
				
				// Update session
				session.setAttribute("user", u);
					
				response.sendRedirect("dashboard.jsp");
					
			} else {
					
				out.println("This email already exists. Please choose another one.");
					
			}
			
		}
		
	}

}
