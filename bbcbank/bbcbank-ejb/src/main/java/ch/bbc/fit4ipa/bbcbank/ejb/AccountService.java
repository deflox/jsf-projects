package ch.bbc.fit4ipa.bbcbank.ejb;

import java.util.List;

import ch.bbc.fit4ipa.bbcbank.entities.Account;

public interface AccountService {
	
	public List<Account> getIbansByCustomerNumber(String customerNumber);
	
	public Account getAccountByIban(String iban);
	
}
