package ch.bbcag.gamexchange.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import ch.bbcag.gamexchange.model.User;
import ch.bbcag.gamexchange.util.BCrypt;

public class UsersDAO {

	private PreparedStatement ps = null;
	private Connection con = null;
	private ResultSet rs = null;

	public UsersDAO() {

		try {
			DataSource ds = (DataSource) InitialContext.doLookup("Onlineshop");
			con = ds.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public boolean updateUser(User u) {
		
		String sql = " UPDATE users"
				   + "    SET useremail    = ?,"
				   + "        userpassword = ?,"
				   + "        firstname    = ?,"
				   + "        lastname     = ?,"
				   + "        street       = ?,"
				   + "        streetno     = ?,"
				   + "        city         = ?,"
				   + "        postalcode   = ?,"
				   + "        country      = ? "
				   + "  WHERE user_id      = ? ";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, u.getEmail());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstname());
			ps.setString(4, u.getLastname());
			ps.setString(5, u.getStreet());
			ps.setString(6, u.getStreetno());
			ps.setString(7, u.getCity());
			ps.setString(8, u.getPostalcode());
			ps.setString(9, u.getCountry());
			ps.setInt(10,   u.getUserId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean insertUser(User u) {
		
		String sql = "INSERT INTO users "
				+ " (useremail, "
				+ " userpassword, "
				+ " firstname, "
				+ " lastname, "
				+ " street, "
				+ " streetno, "
				+ " city, "
				+ " postalcode, "
				+ " country, "
				+ " coins) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, u.getEmail());
			ps.setString(2, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(12)));
			ps.setString(3, u.getFirstname());
			ps.setString(4, u.getLastname());
			ps.setString(5, u.getStreet());
			ps.setString(6, u.getStreetno());
			ps.setString(7, u.getCity());
			ps.setString(8, u.getPostalcode());
			ps.setString(9, u.getCountry());
			ps.setInt(10, 0);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
		
	}

	public User selectUserByEmail(String email) {

		User u = null;
		String sql = " SELECT user_id,"
				   + "        useremail,"
			       + "        userpassword,"
				   + "        firstname,"
				   + "        lastname,"
				   + "        street,"
				   + "        streetno,"
				   + "        city,"
				   + "        postalcode,"
				   + "        country,"
				   + "        coins"
				   + "   FROM users"
				   + "  WHERE useremail = ?";

		try {

			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (rs.next()) {
				
				u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setEmail(rs.getString("useremail"));
				u.setPassword(rs.getString("userpassword"));
				u.setFirstname(rs.getString("firstname"));
				u.setLastname(rs.getString("lastname"));
				u.setStreet(rs.getString("street"));
				u.setStreetno(rs.getString("streetno"));
				u.setCity(rs.getString("city"));
				u.setPostalcode(rs.getString("postalcode"));
				u.setCountry(rs.getString("country"));
				u.setCoins(rs.getInt("coins"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;

	}

}
