package ch.bbcag.gamexchange.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import ch.bbcag.gamexchange.model.Item;

public class ItemsDAO {

	private PreparedStatement ps = null;
	private Connection con = null;
	private ResultSet rs = null;
	
	public ItemsDAO() {

		try {
			DataSource ds = (DataSource) InitialContext.doLookup("Onlineshop");
			con = ds.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public ArrayList<Item> selectAllItems() {
		
		return null;
		
	}
	
	public Item selectItemById(int id) {
		
		Item i = null;
		
		String sql = " SELECT item_id,"
				   + "        title,"
			       + "        description,"
				   + "        price,"
				   + "        category,"
				   + "        seller_id,"
				   + "        buyer_id,"
				   + "        sell_date,"
				   + "        image_path"
				   + "   FROM items"
				   + "  WHERE item_id = ?";

		try {

			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				
				i = new Item();
				i.setItemId(rs.getInt("itemId"));
				i.setTitle(rs.getString("title"));
				i.setDescription(rs.getString("description"));
				i.setPrice(rs.getInt("price"));
				i.setCategory(rs.getString("category"));
				i.setSellerId(rs.getInt("seller_id"));
				i.setBuyerId(rs.getInt("buyer_id"));
				i.setSellDate(rs.getString("sell_date"));
				i.setImagePath(rs.getString("image_path"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i;
		
	}
	
	public boolean insertItem() {
		return false;
	}
	
	public boolean updateItem() {
		return false;
	}
	
}
