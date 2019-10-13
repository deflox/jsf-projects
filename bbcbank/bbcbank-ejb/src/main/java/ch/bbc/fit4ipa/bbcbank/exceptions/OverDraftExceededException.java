package ch.bbc.fit4ipa.bbcbank.exceptions;

public class OverDraftExceededException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public OverDraftExceededException(String message) {
		super(message);
	}
}
