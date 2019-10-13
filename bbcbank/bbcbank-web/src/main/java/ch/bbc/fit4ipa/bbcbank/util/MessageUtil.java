package ch.bbc.fit4ipa.bbcbank.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MessageUtil {

	public static final Severity ERROR = FacesMessage.SEVERITY_ERROR;
	public static final Severity SUCCESS = FacesMessage.SEVERITY_INFO;
	
	public static void createMessage(String message, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
    }
	
}
