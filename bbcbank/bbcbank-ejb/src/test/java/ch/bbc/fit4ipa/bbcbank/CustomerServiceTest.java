package ch.bbc.fit4ipa.bbcbank;

import javax.naming.NamingException;

import org.junit.Test;

import ch.bbc.fit4ipa.bbcbank.ejb.CustomerService;
import ch.bbc.fit4ipa.bbcbank.entities.Customer;
import org.junit.Assert;

public class CustomerServiceTest extends AbstractServiceTest {

	@Test
	public void testGetCustomerByCustomerNumber() throws NamingException {
		CustomerService customerService = getCustomerService();
		
		Customer customer = customerService.getCustomerByCustomerNumber("585-123456");
		
		Assert.assertNotNull(customer);
	}
	
}
