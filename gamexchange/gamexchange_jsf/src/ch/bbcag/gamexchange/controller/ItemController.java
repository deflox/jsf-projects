package ch.bbcag.gamexchange.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import ch.bbcag.gamexchange.db.Database;
import ch.bbcag.gamexchange.model.Item;

@ManagedBean
@RequestScoped
public class ItemController {

	@ManagedProperty(value="#{database}")
	private Database db;
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;

	Item item;
	
	public ItemController() {
		item = new Item();
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
