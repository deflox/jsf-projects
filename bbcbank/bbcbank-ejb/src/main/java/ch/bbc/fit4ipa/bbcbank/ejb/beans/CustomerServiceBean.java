package ch.bbc.fit4ipa.bbcbank.ejb.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.bbc.fit4ipa.bbcbank.ejb.CustomerService;
import ch.bbc.fit4ipa.bbcbank.entities.Customer;

@Stateless(name = "CustomerService")
public class CustomerServiceBean implements CustomerService {

	@PersistenceContext(unitName = "bbcbank")
	private EntityManager entityManager;
	
	@Override
	public Customer getCustomerByCustomerNumber(String customerNumber) {
		return entityManager.createNamedQuery("Customer.getCustomerByCustomerNumber", Customer.class)
				.setParameter("customerNumber", customerNumber)
				.getSingleResult();
	}
	
}
