package ch.bbcag.gamexchange.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ch.bbcag.gamexchange.db.Database;
import ch.bbcag.gamexchange.model.Item;

@ManagedBean
@RequestScoped
public class ItemDetailController {

	@ManagedProperty(value="#{database}")
	private Database db;
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;

	Item item;
	
	int currentId;
	
	@PostConstruct
	public void init() {
		
		currentId = getItemId();
		
		if (currentId == -1) {
			
		} else {

		}
		
	}
	
	public int getItemId() {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    String id = facesContext.getExternalContext().getRequestParameterMap().get("id");
	    if (id == null) {
	    	return -1;
	    } else {
	    	return Integer.parseInt(id);
	    }
	}

	
	public String addItem() {
		return null;
	}
	
	public String deleteItem() {
		return null;
	}
	
	public String editItem() {
		return null;
	}
	
	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}
