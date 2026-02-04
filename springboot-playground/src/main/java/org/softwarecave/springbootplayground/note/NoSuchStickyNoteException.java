package org.softwarecave.springbootplayground.note;

public class NoSuchStickyNoteException extends RuntimeException {
    public NoSuchStickyNoteException(String s) {
        super(s);
    }
}
