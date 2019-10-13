package ch.bbc.fit4ipa.bbcbank.ejb;

import java.util.List;

import ch.bbc.fit4ipa.bbcbank.entities.TransactionRecord;
import ch.bbc.fit4ipa.bbcbank.exceptions.OverDraftExceededException;

public interface TransactionService {

	public List<TransactionRecord> getTransactionRecordsByIban(String iban, double accountBalance);
	
	public double debit(String iban, double amount, String text) throws OverDraftExceededException;
	
	public double credit(String iban, double amount, String text);
	
	
}
