package com.reecepy.ejb.exceptions;

/**
 * Exception when someone tried to many times to log in
 *
 * @author Patrick Stillhart
 */
public class AttemptException extends Exception {

    public AttemptException(String message) {
        super(message);
    }
}
