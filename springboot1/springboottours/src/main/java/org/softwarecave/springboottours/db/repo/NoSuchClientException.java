package org.softwarecave.springboottours.db.repo;

public class NoSuchClientException extends RuntimeException {
    public NoSuchClientException(String message) {
        super(message);
    }
}
