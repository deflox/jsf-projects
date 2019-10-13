package ch.bbcag.gamexchange.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.hibernate.validator.constraints.NotBlank;

import ch.bbcag.gamexchange.db.Database;
import ch.bbcag.gamexchange.model.User;
import ch.bbcag.gamexchange.util.Validator;

@ManagedBean
@RequestScoped
public class RegisterBean {

	@ManagedProperty(value="#{database}")
	private Database db;
	
	User user;
	
	@NotBlank(message = "This field is required")
	String repeatedpassword;
	
	public RegisterBean() {
		user = new User();
	}
	
	public String signUp() {
		  
		if (db.getUsersDAO().selectUserByEmail(user.getEmail()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This E-Mail-Adress is already in use", ""));
			return null;
		}
		
		if (!(Validator.validateEmail(user.getEmail()))) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please provide a valid E-Mail-Adress", ""));
			return null;
		}
		
		if (!(user.getPassword().equals(getRepeatedpassword()))) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The both passwords did not match", ""));
			return null;
		}
				
		if (db.getUsersDAO().insertUser(user)) {
			return "signin?faces-redirect=true";
		} else {
			return "signup?faces-redirect=true";	
		}
	
	}
	
	public String getEmailAvailibility() {
		if (user.getEmail() != null) {
			if (!Validator.validateEmail(user.getEmail())) {
				return "This E-Mail-Adress is not valid. Please check your entered input.";
			} else {
				if (db.getUsersDAO().selectUserByEmail(user.getEmail()) == null) {
					return "E-Mail-Adress is available.";
				} else {
					return "This E-Mail-Adress is already in use. Please choose another one.";
				}
			}
		} 
		return "";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	public String getRepeatedpassword() {
		return repeatedpassword;
	}

	public void setRepeatedpassword(String repeatedpassword) {
		this.repeatedpassword = repeatedpassword;
	}
	
}
