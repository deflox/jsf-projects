package ch.bbcag.gamexchange.util;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class Validator {
	
	private static final String EMAIL_REGEX = 
			"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	
	public static boolean validateEmail(String email) {
		return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
	}
	
	public static boolean isEmpty(ArrayList<String> elements) {
		boolean empty = false;
		try {
			for (String s : elements) {
				if (s.isEmpty()) {
					empty = true;
				}
			}
			return empty;
		} catch (NullPointerException e) {
			empty = true;
			return empty;
		}
	}
	
}
