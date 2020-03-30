package com.backbase.interview.kalah.exceptions;

public class InvalidMovementException extends GameException {
    public InvalidMovementException(){
        super("Chosen pit is not valid");
    }
}
