package ch.bbc.fit4ipa.bbcbank.exceptions;

import java.util.logging.Level;

import ch.bbc.fit4ipa.bbcbank.util.LogUtil;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private LogUtil logUtil = LogUtil.getInstance();
	
	public ValidationException(String message) {
		super(message);
		logUtil.log("Validation error occured: " + message, Level.INFO);
	}
	
}
