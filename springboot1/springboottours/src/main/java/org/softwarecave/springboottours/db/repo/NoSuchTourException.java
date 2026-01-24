package org.softwarecave.springboottours.db.repo;

public class NoSuchTourException extends RuntimeException {
    public NoSuchTourException(String message) {
        super(message);
    }
}
