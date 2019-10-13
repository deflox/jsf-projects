package ch.bbc.fit4ipa.bbcbank;

import java.util.List;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import ch.bbc.fit4ipa.bbcbank.ejb.AccountService;
import ch.bbc.fit4ipa.bbcbank.entities.Account;

public class AccountServiceTest extends AbstractServiceTest {
	
	@Test
	public void testGetIbansByCustomerNumber() throws NamingException {
		AccountService accountService = getAccountService();
		
		List<Account> ibans = accountService.getIbansByCustomerNumber("585-123456");
		
		Assert.assertNotNull(ibans);
		Assert.assertTrue(ibans.size() == 2);
	}
	
	@Test
	public void testGetAccountByIban() throws NamingException {
		AccountService accountService = getAccountService();
		
		Account account = accountService.getAccountByIban("CH320058558512345601T");
		
		Assert.assertNotNull(account);
	}
	
}
