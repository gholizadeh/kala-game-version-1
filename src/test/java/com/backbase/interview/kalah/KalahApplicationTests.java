package com.backbase.interview.kalah;

import com.backbase.interview.kalah.exceptions.*;
import com.backbase.interview.kalah.model.dto.GameModel;
import com.backbase.interview.kalah.model.repository.GameRepository;
import com.backbase.interview.kalah.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KalahApplicationTests {

	@Autowired
	private GameService service;
	@Autowired
	private GameRepository repository;

	private GameModel game;

	@BeforeAll
	void GameCreation(){
		game = service.create();

	}

	@Test
	void testGameNotExist(){
		Assertions.assertThrows(GameNotExistException.class, () -> {
			service.play(100, 12);
		});
	}

	@Test
	void testInvalidPit(){
		Assertions.assertThrows(InvalidMovementException.class, () -> {
			GameModel game2 = service.create();
			service.play(game2.getId().intValue(), 14);
		});
	}

	@Test
	void testGame()throws GameException{
		Assertions.assertNotNull(game);
		testMovement();
		testPlayerMovementWhileNotAllowed();
		testEmptyPitMovement();
		testMovementWhileGameisFinished();
	}

	/**
	 * a pre defined scenario of a couple of movement to check if it respect to turns
	 * @throws GameException
	 */
	void testMovement() throws GameException{
		service.play(game.getId().intValue(), 2);
		service.play(game.getId().intValue(), 12);
		service.play(game.getId().intValue(), 12);
		service.play(game.getId().intValue(), 5);
		service.play(game.getId().intValue(), 11);
		service.play(game.getId().intValue(), 11);
		service.play(game.getId().intValue(), 5);
		service.play(game.getId().intValue(), 5);
		service.play(game.getId().intValue(), 6);
		service.play(game.getId().intValue(), 11);
		service.play(game.getId().intValue(), 2);
		service.play(game.getId().intValue(), 11);
		service.play(game.getId().intValue(), 4);
		service.play(game.getId().intValue(), 11);
		service.play(game.getId().intValue(), 6);
		service.play(game.getId().intValue(), 3);
		service.play(game.getId().intValue(), 11);
		service.play(game.getId().intValue(), 12);
		service.play(game.getId().intValue(), 8);
		service.play(game.getId().intValue(), 11);
		service.play(game.getId().intValue(), 10);
		service.play(game.getId().intValue(), 5);
		service.play(game.getId().intValue(), 2);
		service.play(game.getId().intValue(), 3);
		service.play(game.getId().intValue(), 4);
		service.play(game.getId().intValue(), 10);
		service.play(game.getId().intValue(), 6);
		service.play(game.getId().intValue(), 5);
		service.play(game.getId().intValue(), 6);
		service.play(game.getId().intValue(), 3);
		service.play(game.getId().intValue(), 11);
		service.play(game.getId().intValue(), 1);
	}

	void testPlayerMovementWhileNotAllowed(){
		Assertions.assertThrows(InvalidPlayerException.class, () -> {
			service.play(game.getId().intValue(), 5);
		});
	}

	void testEmptyPitMovement(){
		Assertions.assertThrows(EmptyPitMovementException.class, () -> {
			service.play(game.getId().intValue(), 11);
		});
	}

	void testMovementWhileGameisFinished(){
		Assertions.assertThrows(GameFinishedException.class, () -> {
			service.play(game.getId().intValue(), 12);
		});
	}

}
