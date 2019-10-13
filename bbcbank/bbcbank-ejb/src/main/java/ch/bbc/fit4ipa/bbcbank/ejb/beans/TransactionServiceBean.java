package ch.bbc.fit4ipa.bbcbank.ejb.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.bbc.fit4ipa.bbcbank.ejb.TransactionService;
import ch.bbc.fit4ipa.bbcbank.entities.Account;
import ch.bbc.fit4ipa.bbcbank.entities.TransactionRecord;
import ch.bbc.fit4ipa.bbcbank.exceptions.OverDraftExceededException;

@Stateless(name = "TransactionService")
public class TransactionServiceBean implements TransactionService {
	
	@PersistenceContext(unitName = "bbcbank")
	private EntityManager entityManager;
	
	@Override
	public List<TransactionRecord> getTransactionRecordsByIban(String iban, double balance) {
		List<TransactionRecord> transactions = entityManager.createNamedQuery("TransactionRecord.getTransactionsByIban", TransactionRecord.class)
				.setParameter("iban", iban)
				.getResultList();
		
		// Calculate balance for each transaction record
		if (transactions != null && transactions.size() > 0) {
			double accountAmount = balance;
			transactions.get(0).setBalance(accountAmount);
			for (int i = 1; i <= transactions.size()-1; i++) {
				if ("D".equals(transactions.get(i-1).getType())) 
					accountAmount = accountAmount + transactions.get(i-1).getAmount();
				if ("C".equals(transactions.get(i-1).getType())) 
					accountAmount = accountAmount - transactions.get(i-1).getAmount();
				transactions.get(i).setBalance(accountAmount);
			}
		}
		
		return transactions;
	}
	
	@Override
	public double debit(String iban, double amount, String text) throws OverDraftExceededException {
		// Substract amount from account
		Account account = getAccountByIban(iban);
		double newBalance = account.getBalance() - amount;
		if (newBalance < 0-account.getOverdraft())
			throw new OverDraftExceededException("Die Überzugslimite von " + account.getOverdraft() + " wurde überschritten. Sie können die Auszahlung nicht durchführen. Ihr Kontostand: " + account.getBalance());
		account.setBalance(newBalance);
		entityManager.merge(account);
		
		// Add new transaction
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setType("D");
		transactionRecord.setText(text);
		transactionRecord.setAmount(amount);
		transactionRecord.setAccount(account);
		entityManager.persist(transactionRecord);
		
		return newBalance;
	}

	@Override
	public double credit(String iban, double amount, String text) {
		// Add amount to account 
		Account account = getAccountByIban(iban);
		account.setBalance(account.getBalance() + amount);
		entityManager.merge(account);
		
		// Add new transaction
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setType("C");
		transactionRecord.setText(text);
		transactionRecord.setAmount(amount);
		transactionRecord.setAccount(account);
		entityManager.persist(transactionRecord);
		
		return account.getBalance();
	}
	
	public Account getAccountByIban(String iban) {
		return entityManager.createNamedQuery("Account.getAccountByIban", Account.class)
				.setParameter("iban", iban)
				.getSingleResult();
	}
	
}
