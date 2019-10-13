package ch.bbc.fit4ipa.bbcbank.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@NamedQueries({
	@NamedQuery(name = "Account.getAccountAndTransactionRecordsByIban", query = "select a from Account a where a.iban = :iban"),
	@NamedQuery(name = "Account.getIbansByCustomerNumber", query = "select a.iban from Account a where a.customer.customerNumber = :customerNumber"),
	@NamedQuery(name = "Account.getAccountByIban", query = "select a from Account a where a.iban = :iban")
})
public class Account extends AbstractBaseEntity {
	
	@Column(name = "IBAN", nullable = false, unique = true)
	private String iban;
	
	@Column(name = "BALANCE", nullable = false)
	private Double balance;
	
	@Column(name = "OVERDRAFT", nullable = false)
	private Double overdraft;
	
	// Customer for this account
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	// Gets transaction records for this account
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy("transactionTime DESC")
	private List<TransactionRecord> transactionRecords = new ArrayList<>();

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(Double overdraft) {
		this.overdraft = overdraft;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<TransactionRecord> getTransactionRecords() {
		return transactionRecords;
	}

	public void setTransactionRecords(List<TransactionRecord> transactionRecords) {
		this.transactionRecords = transactionRecords;
	}
	
}
