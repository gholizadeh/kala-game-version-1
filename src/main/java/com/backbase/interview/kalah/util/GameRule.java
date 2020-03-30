package com.backbase.interview.kalah.util;

import com.backbase.interview.kalah.config.StaticValues;
import com.backbase.interview.kalah.exceptions.*;
import com.backbase.interview.kalah.model.Turn;
import com.backbase.interview.kalah.model.domain.GameEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GameRule {
    /**
     * checks if game is valid to play
     * - if pitId is in a valid range
     * - there must be at least one pit with stone left in it.
     * - each player only can choose pitId in their own pits (according to player turn)
     *
     * @param game
     * @param pitId
     */
    public static void validateGame(GameEntity game, Integer pitId) throws GameException {
        if(Objects.isNull(pitId) || pitId.compareTo(0) < 1 || pitId.compareTo(StaticValues.P2Home) > 0)
            throw new InvalidMovementException();

        if (Objects.nonNull(game.getWinner()))
            throw new GameFinishedException(game.getWinner().name() + " already won this game");

        if (!game.getStatuses().parallelStream().anyMatch(item -> {
            return !item.getIndex().equals(StaticValues.P1Home) && !item.getIndex().equals(StaticValues.P2Home) && item.getValue().compareTo(0) > 0;
        }))
            throw new GameEndedException();

        validatePitId(game, pitId);

        if( (game.getTurn().equals(Turn.P1) && pitId.compareTo(StaticValues.P1Home) > 0) ||
                (game.getTurn().equals(Turn.P2) && pitId.compareTo(StaticValues.P1Home) < 0))
            throw new InvalidPlayerException();

        if (!game.getStatuses().parallelStream().anyMatch(item -> {
            return item.getIndex().equals(pitId) && item.getValue().compareTo(0) > 0;
        }))
            throw new EmptyPitMovementException();
    }

    /**
     * checks if chosen pit is validate to move
     * - you must choose a pit number between 1-6 and 8-13
     * - chosen pitId must has stone left in it.
     *
     * @param game
     * @param pitId
     * @throws EmptyPitMovementException
     */
    public static void validatePitId(GameEntity game, Integer pitId) throws InvalidMovementException {
        if(pitId.equals(StaticValues.P1Home) || pitId.equals(StaticValues.P2Home))
            throw new InvalidMovementException();
    }
}
