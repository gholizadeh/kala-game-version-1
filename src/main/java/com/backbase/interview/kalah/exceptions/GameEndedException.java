package com.backbase.interview.kalah.exceptions;

public class GameEndedException extends GameException {
    public GameEndedException(){
        super("The game is ended and has no winner. <equals>");
    }
}
