package ch.bbc.fit4ipa.bbcbank;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;

import ch.bbc.fit4ipa.bbcbank.ejb.AccountService;
import ch.bbc.fit4ipa.bbcbank.ejb.CustomerService;
import ch.bbc.fit4ipa.bbcbank.ejb.TransactionService;

public abstract class AbstractServiceTest {
	
	protected Context ctx;

	// Service names
	protected static final String TRANSACTION_SERVICE_NAME = "java:global/classpath.ear/bbcbank-ejb/TransactionService";
	protected static final String CUSTOMER_SERVICE_NAME = "java:global/classpath.ear/bbcbank-ejb/CustomerService";
	protected static final String ACCOUNT_SERVICE_NAME = "java:global/classpath.ear/bbcbank-ejb/AccountService";
	
	@Before
	public void setUp() throws NamingException {
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		
		// Link to local MySQL database
		props.put("bbcbank", "new://Resource?type=DataSource");
		props.put("bbcbank.JtaManaged", "false");
		props.put("bbcbank.JdbcDriver", "com.mysql.jdbc.Driver");
		props.put("bbcbank.JdbcUrl","jdbc:mysql://localhost:3306/bbcbank");
		props.put("bbcbank.username", "root");
		props.put("bbcbank.password", "12345678");

		ctx = new InitialContext(props);
	}

	public TransactionService getTransactionService() throws NamingException {
		return (TransactionService) ctx.lookup(TRANSACTION_SERVICE_NAME);
	}
	
	public CustomerService getCustomerService() throws NamingException {
		return (CustomerService) ctx.lookup(CUSTOMER_SERVICE_NAME);
	}
	
	public AccountService getAccountService() throws NamingException {
		return (AccountService) ctx.lookup(ACCOUNT_SERVICE_NAME);
	}
	
}
