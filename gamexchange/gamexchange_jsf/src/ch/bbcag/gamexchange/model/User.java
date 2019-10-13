package ch.bbcag.gamexchange.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class User {

	// Account information
	private int userId;
	
	@Size(min=2, max=45, message = "E-Mail-Adress needs to have between 2 and 45 characters")
	private String email;
	
	@Size(min=8, max=30, message = "Password needs to have between 8 and 30 characters")
	private String password;
	
	private int    coins;
	
	// Personal information
	@Size(min=2, max=45, message = "Firstname needs to have between 2 and 45 characters")
	private String firstname;
	
	@Size(min=2, max=45, message = "Lastname needs to have between 2 and 45 characters")
	private String lastname;
	
	@Size(min=2, max=45, message = "Steet needs to have between 2 and 45 characters")
	private String street;
	
	@Size(min=2, max=15, message = "Streetnumber needs to have between 2 and 15 characters")
	private String streetno;
	
	@Size(min=2, max=45, message = "City needs to have between 2 and 45 characters")
	private String city;
	
	@Size(min=4, max=20, message = "Postalcode needs to have between 4 and 12 characters")
	private String postalcode;
	
	@NotBlank(message = "This field is required")
	private String country;
	
	/**
	 * Directly sets all users values. 
	 * 
	 * @param u: The user with the values.
	 */
	public void setUser(User u) {
		this.setUserId(u.getUserId());
		this.setEmail(u.getEmail());
		this.setPassword(u.getPassword());
		this.setFirstname(u.getFirstname());
		this.setLastname(u.getLastname());
		this.setStreet(u.getStreetno());
		this.setCity(u.getCity());
		this.setPostalcode(u.getPostalcode());
		this.setCountry(u.getCountry());
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetno() {
		return streetno;
	}

	public void setStreetno(String streetno) {
		this.streetno = streetno;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return (getUserId() + " " + getEmail() + " " + getPassword() + " " + getFirstname() + " " + getLastname() + " " + getStreet() + " " + getStreetno() + " " + getCity() + " " + getPostalcode() + " " + getCountry() + " " + getCoins());
	}
	
}
