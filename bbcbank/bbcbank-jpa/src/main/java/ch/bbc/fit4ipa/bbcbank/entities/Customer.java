package ch.bbc.fit4ipa.bbcbank.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@NamedQueries({
	@NamedQuery(name = "Customer.getCustomerByCustomerNumber", query = "select c from Customer c where c.customerNumber = :customerNumber")
})
public class Customer extends AbstractBaseEntity {

	@Column(name = "CUSTOMER_NUMBER", nullable = false)
	private String customerNumber;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "STREET", nullable = false)
	private String street;
	
	@Column(name = "CITY", nullable = false)
	private String city;
	
	@Column(name = "ZIPCODE", nullable = false)
	private short zipCode;

	// Gets account records for this customer
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Account> accounts = new ArrayList<>();

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public short getZipCode() {
		return zipCode;
	}

	public void setZipCode(short zipCode) {
		this.zipCode = zipCode;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
}
