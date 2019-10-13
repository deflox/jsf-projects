package ch.bbc.fit4ipa.bbcbank.entities;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "transaction_record")
@NamedQueries({
	@NamedQuery(name = "TransactionRecord.getTransactionsByIban", query = "select t from TransactionRecord t where t.account.iban = :iban order by t.transactionTime desc")
})
public class TransactionRecord extends AbstractBaseEntity {

	@Column(name = "TYPE", nullable = false)
	private String type;
	
	@Column(name = "TEXT", nullable = false)
	private String text;
	
	@Column(name = "AMOUNT", nullable = false)
	private Double amount;
	
	@Column(name = "TRANSACTION_TIME", nullable = false)
	private Timestamp transactionTime;
	
	// Account for this transaction
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	@Transient
	private Double balance;
	
	@PrePersist
	public void prePersist() {
		this.transactionTime = new Timestamp(System.currentTimeMillis());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Timestamp getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
