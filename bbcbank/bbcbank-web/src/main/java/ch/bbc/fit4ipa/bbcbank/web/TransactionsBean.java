package ch.bbc.fit4ipa.bbcbank.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ch.bbc.fit4ipa.bbcbank.ejb.AccountService;
import ch.bbc.fit4ipa.bbcbank.ejb.CustomerService;
import ch.bbc.fit4ipa.bbcbank.ejb.TransactionService;
import ch.bbc.fit4ipa.bbcbank.entities.Account;
import ch.bbc.fit4ipa.bbcbank.entities.Customer;
import ch.bbc.fit4ipa.bbcbank.entities.TransactionRecord;

@ManagedBean
@ViewScoped
public class TransactionsBean {

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
	 * Account object which holds the current selected account.
	 */
	private Account account;
	
	/**
	 * Current displayed list of Transaction Records.
	 */
	private List<TransactionRecord> transactionRecords;
	
	/**
	 * Contains a list of the IBANs for the current customer.
	 */
	private List<Account> ibans;
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("start postConstruct()");
		customer = customerService.getCustomerByCustomerNumber("585-123456");
		ibans = accountService.getIbansByCustomerNumber("585-123456");
		account = accountService.getAccountByIban(customer.getAccounts().get(0).getIban());
		transactionRecords = transactionService.getTransactionRecordsByIban(account.getIban(), account.getBalance());
		System.out.println("end postConstruct()");
	}

	/**
	 * Refreshes the transaction list view in case user
	 * selects a different account.
	 * 
	 * @return String redirection page
	 */
	public void refresh() {
		System.out.println("start refresh()");
		account = accountService.getAccountByIban(account.getIban());
		transactionRecords = transactionService.getTransactionRecordsByIban(account.getIban(), account.getBalance());
		System.out.println("end refresh()");
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<TransactionRecord> getTransactionRecords() {
		return transactionRecords;
	}

	public void setTransactionRecords(List<TransactionRecord> transactionRecords) {
		this.transactionRecords = transactionRecords;
	}

	public List<Account> getIbans() {
		return ibans;
	}

	public void setIbans(List<Account> ibans) {
		this.ibans = ibans;
	}
	
}
