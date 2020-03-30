package com.backbase.interview.kalah.service;

import com.backbase.interview.kalah.config.StaticValues;
import com.backbase.interview.kalah.exceptions.*;
import com.backbase.interview.kalah.model.Turn;
import com.backbase.interview.kalah.model.dto.GameModel;
import com.backbase.interview.kalah.model.dto.GameModelStatus;
import com.backbase.interview.kalah.model.domain.GameEntity;
import com.backbase.interview.kalah.model.domain.GameStatusEntity;
import com.backbase.interview.kalah.model.repository.GameRepository;
import com.backbase.interview.kalah.util.GameRule;
import com.backbase.interview.kalah.util.GameTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    /**
     * to create a new game, initial and persist it.
     * @return
     */
    public GameModel create(){
        GameEntity entity = new GameEntity();
        entity.setTurn(Turn.P1);
        initGame(entity);
        return GameTransformer.transformEntityToModel(repository.save(entity));
    }

    /**
     * to get a game all pits' status and see the result
     * @param gameId
     * @return
     * @throws GameNotExistException
     */
    public GameModelStatus getStatus(Integer gameId) throws GameNotExistException {
        Optional<GameEntity> game = repository.findById(gameId.longValue());
        if(game.isPresent()){
            return GameTransformer.transformEntityToFullModel(game.get());
        }else{
            throw new GameNotExistException();
        }
    }

    /**
     * to make a move on a game by given gameId and pitId as starting point to make a move
     * only one thread is allowed to make a move in a same time
     * @param gameId
     * @param pitId
     * @return
     * @throws GameException
     */
    synchronized public GameModelStatus play(Integer gameId, Integer pitId) throws GameException {
        Optional<GameEntity> game = repository.findById(gameId.longValue());
        if(game.isPresent()){
            GameEntity entity = game.get();
            makeMove(entity, pitId);
            return GameTransformer.transformEntityToFullModel(repository.save(entity));
        }else{
            throw new GameNotExistException();
        }
    }

    /**
     * to make game move and merge it.
     * chosen pitId would be validated according to chosen game.
     * @param entity
     * @param pitId
     * @throws GameException
     */
    private void makeMove(GameEntity entity, Integer pitId) throws GameException {
        //1- validate game and first chosen pitId
        GameRule.validateGame(entity, pitId);
        //2- get all pits
        Map<Integer, Integer> pits = GameTransformer.statusesToMap(entity.getStatuses());
        //3- for each round if there are stones in last pit and pit is not a base pit continue
        while (true) {
            try {
                GameRule.validatePitId(entity, pitId);
                //check if a player is winner
                checkWinner(pits, entity);
                //3- pick up stones, empty chosen pit, go to next pit
                int count = pits.get(pitId);
                pits.put(pitId, 0);
                pitId++;

                while (count > 0){
                    //if current pit is a base bit but it's not related just ignore it.
                    if((entity.getTurn().equals(Turn.P1) && pitId.equals(StaticValues.P2Home)) ||
                            (entity.getTurn().equals(Turn.P2) && pitId.equals(StaticValues.P1Home))) {
                        pitId = goToNextPit(pitId);
                        continue;
                    }

                    pits.merge(pitId, 1, Integer::sum);

                    count--;
                    if(count > 0) {
                        pitId = goToNextPit(pitId);
                    }else{
                        if(pits.get(pitId)==1)
                            throw new EmptyPitMovementException();
                    }
                }
            }catch (InvalidMovementException e){
                //the last pit id is a base pit => finish the round without changing turn
                break;
            }catch (EmptyPitMovementException e){
                //the last pit has no stones left in => finish the round and change turn
                entity.setTurn(entity.getTurn().equals(Turn.P1) ? Turn.P2 : Turn.P1);
                break;
            }catch (GameFinishedException e){
                //a player won the game
                break;
            }
        }

        //now we can update entity and statuses
        entity.getStatuses().parallelStream().forEach(item -> {
            item.setValue(pits.get(item.getIndex()));
        });
        if(Objects.nonNull(entity.getWinner())){
            throw new GameFinishedException(entity.getWinner().name() + " is winner");
        }
    }

    private void checkWinner(Map<Integer, Integer> pits, GameEntity entity) throws GameFinishedException{
        float toWin = (StaticValues.StoneCont * (StaticValues.P2Home - 2)) / 2;
        if(!pits.get(StaticValues.P1Home).equals(pits.get(StaticValues.P2Home))) {
            if (pits.get(StaticValues.P1Home).floatValue() > toWin) {
                entity.setWinner(Turn.P1);
            } else if (pits.get(StaticValues.P2Home).floatValue() > toWin){
                entity.setWinner(Turn.P2);
            }
        }

        if(Objects.nonNull(entity.getWinner())){
            throw new GameFinishedException(entity.getWinner().name() + " is winner");
        }
    }

    private int goToNextPit(Integer pitId){
        if (pitId.equals(StaticValues.P2Home))
            pitId = 1;
        else
            pitId++;

        return pitId;
    }

    /**
     * to make initial status of new game.
     * by default for each pit there are 6 stone and pits no.7 , no.14 which are bases would have no stone in.
     * @param game
     */
    private void initGame(GameEntity game) {
        if(Objects.nonNull(game)) {
            ArrayList<GameStatusEntity> statuses = new ArrayList<>();
            for (int i = 1; i < StaticValues.P2Home + 1 ; ++i) {
                int value = i == StaticValues.P1Home || i == StaticValues.P2Home ? 0 : StaticValues.StoneCont;
                statuses.add(new GameStatusEntity(i, value, game));
            }
            game.setStatuses(statuses);
        }
    }
}
