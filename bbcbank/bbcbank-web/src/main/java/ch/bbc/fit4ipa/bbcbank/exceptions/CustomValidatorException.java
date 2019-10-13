package ch.bbc.fit4ipa.bbcbank.exceptions;

import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import ch.bbc.fit4ipa.bbcbank.util.LogUtil;

public class CustomValidatorException extends ValidatorException {

	private static final long serialVersionUID = 1L;
	
	private LogUtil logUtil = LogUtil.getInstance();
	
	public CustomValidatorException(FacesMessage message) {
		super(message);
		logUtil.log("Validation error occured: " + message.getDetail(), Level.INFO);
	}
	
}
