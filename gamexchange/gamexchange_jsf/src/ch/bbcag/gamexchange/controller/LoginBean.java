package ch.bbcag.gamexchange.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ch.bbcag.gamexchange.db.Database;
import ch.bbcag.gamexchange.model.User;
import ch.bbcag.gamexchange.util.BCrypt;

@ManagedBean
@RequestScoped
public class LoginBean {

	@ManagedProperty(value="#{database}")
	private Database db;
	
	User user;
	
	public LoginBean() {
		user = new User();
	}
	
	public String signIn() {
		
		User tempUser = db.getUsersDAO().selectUserByEmail(user.getEmail()); // Try to get user with entered email
		
		if (tempUser != null) { // Found user
			
			if (BCrypt.checkpw(user.getPassword(), tempUser.getPassword())) { // Password matched
				
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        		session.setAttribute("user", tempUser);
				return "dashboard?faces-redirect=true";
				
			} else { 
				// Password did not match
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No user found. Are you sure you're entered values are correct?", ""));
				return null;
			}
			
		} else { 
			// Didn't found user
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No user found. Are you sure you're entered values are correct?", ""));
			return null;
		}

	}
	
	public String forgotPw() {
		return "";
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
