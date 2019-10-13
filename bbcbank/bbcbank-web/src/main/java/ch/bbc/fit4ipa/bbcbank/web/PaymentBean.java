package ch.bbc.fit4ipa.bbcbank.web;

import java.util.List;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import ch.bbc.fit4ipa.bbcbank.ejb.AccountService;
import ch.bbc.fit4ipa.bbcbank.ejb.CustomerService;
import ch.bbc.fit4ipa.bbcbank.ejb.TransactionService;
import ch.bbc.fit4ipa.bbcbank.entities.Account;
import ch.bbc.fit4ipa.bbcbank.entities.Customer;
import ch.bbc.fit4ipa.bbcbank.entities.TransactionRecord;
import ch.bbc.fit4ipa.bbcbank.exceptions.OverDraftExceededException;
import ch.bbc.fit4ipa.bbcbank.exceptions.ValidationException;
import ch.bbc.fit4ipa.bbcbank.util.LogUtil;
import ch.bbc.fit4ipa.bbcbank.util.MessageUtil;

@ManagedBean
@RequestScoped
public class PaymentBean {

	@EJB
	private CustomerService customerService;
	
	@EJB
	private AccountService accountService;
	
	@EJB
	private TransactionService transactionService;
	
	/**
	 * Contains the current customer
	 */
	private Customer customer;
	
	/**
	 * Contains a list of the IBANs for the current customer.
	 */
	private List<Account> accountIbans;
	
	/**
	 * Account object which holds the current selected account.
	 */
	private Account selectedAccount;
	
	/**
	 * Transaction record object which holds the new values.
	 */
	private TransactionRecord transactionRecord;
	
	@PostConstruct
	public void postConstruct() {
		customer = customerService.getCustomerByCustomerNumber("585-123456");
		accountIbans = accountService.getIbansByCustomerNumber("585-123456");
		transactionRecord = new TransactionRecord();
		selectedAccount = new Account();
	}
	
	/**
	 * Does a payment.
	 * 
	 * @return redirection page.
	 */
	public String pay() {
		try {
			validate();
		} catch (ValidationException e1) {
			MessageUtil.createMessage(e1.getMessage(), MessageUtil.ERROR);
			return "payments.xhtml";
		}
		
		Double newBalance = null;	
		
		if ("D".equals(transactionRecord.getType())) {
			try {
				newBalance = transactionService.debit(selectedAccount.getIban(), transactionRecord.getAmount(), transactionRecord.getText());
			} catch (OverDraftExceededException e2) {
				MessageUtil.createMessage(e2.getMessage(), MessageUtil.ERROR);
				return "payments.xhtml";
			}
		}
		
		if ("C".equals(transactionRecord.getType()))
			newBalance = transactionService.credit(selectedAccount.getIban(), transactionRecord.getAmount(), transactionRecord.getText());
		
		String type = "C".equals(transactionRecord.getType()) ? "Einzahlung" : "Auszahlung";
		
		MessageUtil.createMessage("Die " + type + " wurde erfolgreich durchgeführt. Der neue Kontostand beträgt: " + newBalance , MessageUtil.SUCCESS);
		
		LogUtil.getInstance().log("New transaction added for account with iban " + selectedAccount.getIban() + " with amount " + transactionRecord.getAmount(), Level.INFO);
		
		emptyFields();
		
		return "payments.xhtml";
	}
	
	/**
	 * Performs all required validation for a debit or credit.
	 */
	public void validate() throws ValidationException {
		if (selectedAccount.getIban() == null || "".equals(selectedAccount.getIban())) 
			throw new ValidationException("Bitte wählen Sie ein Konto aus.");
		
		Account account = accountService.getAccountByIban(selectedAccount.getIban());
		
		if (account == null)
			throw new ValidationException("Das angegebene Konto existiert nicht.");
		
		if (account.getBalance()+transactionRecord.getAmount() > 999999.0 && "C".equals(transactionRecord.getType()))
			throw new ValidationException("Sie haben Ihr maximales Limit erreicht. Sie können nicht mehr als CHF 999'999 einzahlen.");
	}
	
	/**
	 * Emptys the fields in case payment was successful.
	 */
	public void emptyFields() {
		transactionRecord = null;
		transactionRecord = new TransactionRecord();
		selectedAccount = null;
		selectedAccount = new Account();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Account> getAccountIbans() {
		return accountIbans;
	}

	public void setAccountIbans(List<Account> accountIbans) {
		this.accountIbans = accountIbans;
	}

	public Account getSelectedAccount() {
		return selectedAccount;
	}

	public void setSelectedAccount(Account selectedAccount) {
		this.selectedAccount = selectedAccount;
	}

	public TransactionRecord getTransactionRecord() {
		return transactionRecord;
	}

	public void setTransactionRecord(TransactionRecord transactionRecord) {
		this.transactionRecord = transactionRecord;
	}
	
}
