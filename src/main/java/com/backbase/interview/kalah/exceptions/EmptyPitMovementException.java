package com.backbase.interview.kalah.exceptions;

public class EmptyPitMovementException extends GameException {
    public EmptyPitMovementException() {
        super("Chosen pit has no stone left in it");
    }
}
