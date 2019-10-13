package ch.bbc.fit4ipa.bbcbank.ejb;

import ch.bbc.fit4ipa.bbcbank.entities.Customer;

public interface CustomerService {

	public Customer getCustomerByCustomerNumber(String customerNumber);
	
}
