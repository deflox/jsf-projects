package ch.bbcag.gamexchange.db;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class Database {
	
	public UsersDAO getUsersDAO() {
		return new UsersDAO();
	}
	
	public ItemsDAO getItemsDAO() {
		return new ItemsDAO();
	}	
	
}
