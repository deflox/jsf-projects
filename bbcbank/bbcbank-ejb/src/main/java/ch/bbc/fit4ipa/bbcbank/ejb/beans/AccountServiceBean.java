package ch.bbc.fit4ipa.bbcbank.ejb.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.bbc.fit4ipa.bbcbank.ejb.AccountService;
import ch.bbc.fit4ipa.bbcbank.entities.Account;

@Stateless(name = "AccountService")
public class AccountServiceBean implements AccountService {

	@PersistenceContext(unitName = "bbcbank")
	private EntityManager entityManager;
	
	@Override
	public List<Account> getIbansByCustomerNumber(String customerNumber) {
		return entityManager.createNamedQuery("Account.getIbansByCustomerNumber", Account.class)
				.setParameter("customerNumber", customerNumber)
				.getResultList();
	}

	@Override
	public Account getAccountByIban(String iban) {
		return entityManager.createNamedQuery("Account.getAccountByIban", Account.class)
				.setParameter("iban", iban)
				.getSingleResult();
	}

}
