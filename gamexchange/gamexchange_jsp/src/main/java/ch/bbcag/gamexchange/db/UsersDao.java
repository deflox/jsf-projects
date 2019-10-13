package ch.bbcag.gamexchange.db;

import ch.bbcag.gamexchange.models.User;

public interface UsersDao {
	
	public void insertUser(User u);
	public void updateUser(User u);
	public User selectUserByEmail(String email);
	
}