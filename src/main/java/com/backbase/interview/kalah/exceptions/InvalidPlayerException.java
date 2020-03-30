package com.backbase.interview.kalah.exceptions;

public class InvalidPlayerException extends GameException {
    public InvalidPlayerException(){
        super("Chosen pit is not for the player which has the turn to play");
    }
}
