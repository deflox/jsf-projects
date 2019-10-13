package ch.bbcag.gamexchange.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;


public class Item {

	private int itemId;
	
	// Info about the product
	@Size(min=2, max=45, message = "Product title needs to have between 2 and 45 characters")
	private String title;
	@Size(min=2, max=2500, message = "Description needs to have between 2 and 2500 characters")
	private String description;
	@Digits(integer=4, fraction=0, message = "Number cannot be greater than 4 digits can't have any number after the comma")
	private int price;
	@NotBlank
	private String category;
	
	// Foreign key on "users" table
	private int sellerId;
	private int buyerId;
	
	// Date, item was sold
	private String sellDate;
	
	private String imagePath;
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public String getSellDate() {
		return sellDate;
	}

	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
