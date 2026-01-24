package org.softwarecave.springbootimages.images;

public class NoSuchImageException extends RuntimeException {
    public NoSuchImageException(String s) {
        super(s);
    }
}
