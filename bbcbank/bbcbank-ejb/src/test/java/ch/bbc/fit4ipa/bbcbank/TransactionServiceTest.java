package ch.bbc.fit4ipa.bbcbank;

import java.util.List;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import ch.bbc.fit4ipa.bbcbank.ejb.TransactionService;
import ch.bbc.fit4ipa.bbcbank.entities.TransactionRecord;
import ch.bbc.fit4ipa.bbcbank.exceptions.OverDraftExceededException;

public class TransactionServiceTest extends AbstractServiceTest {
		
	@Test
	public void testGetTransactionRecordsByIban() throws NamingException {
		TransactionService transactionService = getTransactionService();
		
		List<TransactionRecord> transactions = transactionService.getTransactionRecordsByIban("CH320058558512345601T", 900);
		
		Assert.assertNotNull(transactions);
	}
	
	@Test
	public void testDebit() throws NamingException, OverDraftExceededException {
		TransactionService transactionService = getTransactionService();
		
		double newAmount = transactionService.debit("CH320058558512345601T", 100, "Auszahlung");
		
		Assert.assertNotNull(newAmount);
	}
	
	@Test
	public void testCredit() throws NamingException {
		TransactionService transactionService = getTransactionService();
		
		double newAmount = transactionService.credit("CH320058558512345601T", 500.0, "Einzahlung");
		
		Assert.assertNotNull(newAmount);
	}
	
}
