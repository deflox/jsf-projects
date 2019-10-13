package com.reecepy.ejb.exceptions;

/**
 * Exception for when login with wrong credentials
 *
 * @author Patrick Stillhart
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
