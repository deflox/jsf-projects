package ch.bbc.fit4ipa.bbcbank.validators;

import javax.faces.application.FacesMessage;

public class BaseValidator {

	protected FacesMessage createMessage(String message) {
		FacesMessage msg =
				new FacesMessage("E-mail validation failed.",
						message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		return msg;
	}
	
}
