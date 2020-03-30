package com.backbase.interview.kalah.controller;

import com.backbase.interview.kalah.exceptions.GameException;
import com.backbase.interview.kalah.exceptions.GameNotExistException;
import com.backbase.interview.kalah.model.dto.GameModel;
import com.backbase.interview.kalah.model.dto.GameModelStatus;
import com.backbase.interview.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameApi {

    @Autowired
    private GameService service;

    /**
     * creates a new game and send the gameModel back
     */
    @PostMapping
    public GameModel create(){
        return service.create();
    }

    /**
     * to check a game status
     */
    @GetMapping("/{gameId}")
    public GameModelStatus getStatus(@PathVariable Integer gameId) throws GameNotExistException {
        return service.getStatus(gameId);
    }

    /**
     * to make a move
     */
    @PutMapping("/{gameId}/pits/{pitId}")
    public GameModelStatus move(@PathVariable Integer gameId, @PathVariable Integer pitId) throws GameException {
        return service.play(gameId, pitId);
    }
}
