package ch.bbcag.gamexchange.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ch.bbcag.gamexchange.model.User;

@ManagedBean
@SessionScoped
public class SessionBean {

	public boolean isLoggedIn() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		return session.getAttribute("user") != null;
	}

	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
		return "index?faces-redirect=true";
	}

	public User getUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		return (User) session.getAttribute("user");
	}
	
	public void updateSession(User user) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("user", user);
	}

	public String getProfileIcon() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = (md.digest(getUser().getEmail().getBytes("CP1252")));

			StringBuilder sb = new StringBuilder(
					"http://www.gravatar.com/avatar/");
			for (byte b : array) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,
						3));
			}

			sb.append("?s=25&d=retro");

			return sb.toString();

		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 not working!!!!");
		}
	}

}