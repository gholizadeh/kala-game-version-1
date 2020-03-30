package com.backbase.interview.kalah.exceptions;

public class GameNotExistException extends GameException {
    public GameNotExistException(){
        super("The game you are looking for is not exist");
    }
}
