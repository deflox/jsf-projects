package ch.bbcag.gamexchange.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import ch.bbcag.gamexchange.db.Database;
import ch.bbcag.gamexchange.model.User;
import ch.bbcag.gamexchange.util.BCrypt;

@ManagedBean
@RequestScoped
public class EditUserBean {

	@ManagedProperty(value="#{database}")
	private Database db;
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;
	
	User user;
	
	@Size(min=2, max=45, message = "E-Mail-Adress needs to have between 2 and 45 characters")
	String newEmail;
	
	@Size(min=8, max=30, message = "Password needs to have between 8 and 30 characters")
	String newUserPassword;
	String newuserPasswordRepeat;
	
	public EditUserBean() {
		user = new User();
	}
	
	public String editPassword() {
		
		if (newUserPassword.equals(newuserPasswordRepeat)) {
			
			User sessionUser = sessionBean.getUser();
						
			if (BCrypt.checkpw(user.getPassword(), sessionUser.getPassword())) {
				
				sessionUser.setPassword(BCrypt.hashpw(getNewUserPassword(), BCrypt.gensalt(12)));
				
				if (db.getUsersDAO().updateUser(sessionUser)) {
					return "dashboard?faces-redirect=true";
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Server error. Couldn't change password.", ""));
					return null;
				}
				
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Current password is not correct.", ""));
				return null;
			}
			
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The both passwords did not match.", ""));
			return null;
		}
		
	}
	
	public String editUser() {
		
		User sessionUser = getSessionBean().getUser();
		
		if (!(newEmail.equals(sessionUser.getEmail()))) {
			if (db.getUsersDAO().selectUserByEmail(newEmail) != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The entered E-Mail-Adress already exists.", ""));
				return null;
			}
		}
		
		sessionUser.setEmail(newEmail);
		
		if (db.getUsersDAO().updateUser(sessionUser)) {
			
			return "dashboard?faces-redirect=true";
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error save settings. Please try again later.", ""));
			return null;
		}
		
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

	public String getNewUserPassword() {
		return newUserPassword;
	}

	public void setNewUserPassword(String newUserPassword) {
		this.newUserPassword = newUserPassword;
	}

	public String getNewuserPasswordRepeat() {
		return newuserPasswordRepeat;
	}

	public void setNewuserPasswordRepeat(String newuserPasswordRepeat) {
		this.newuserPasswordRepeat = newuserPasswordRepeat;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public String getNewEmail() {
		return sessionBean.getUser().getEmail();
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	
}
