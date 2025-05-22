package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.model.dice.Dice;
import edu.ntnu.idi.idatt.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>BoardGameTest</h1>
 * Most of the functions rely on GUI because of game-reasons.
 * Only the functions that don't use GUI is tested,
 * that means the two getters, getBoard() and getDice().
 */
class BoardGameTest {
  BoardGame boardGameWithOneDice;
  BoardGame boardGameWithTwoDice;
  Player player1;
  Player player2;

  @BeforeEach
  void setUp() {
    boardGameWithOneDice = new BoardGame(new Board(90), 1);
    boardGameWithTwoDice = new BoardGame(new Board(90), 2);

    player1 = new Player("Player1", "Piece");
    player2 = new Player("Player2", "Piece");

    boardGameWithOneDice.addPlayer(player1);
    boardGameWithTwoDice.addPlayer(player2);
  }

  @AfterEach
  void tearDown() {
    boardGameWithOneDice = null;
    boardGameWithTwoDice = null;
  }

  @Test
  void getBoard() {
    assertEquals(90, boardGameWithOneDice.getBoard().tiles.size());
    assertEquals(90, boardGameWithTwoDice.getBoard().tiles.size());
  }

  @Test
  void getDice() {
    assertSame(Dice.class, boardGameWithOneDice.getDice().getClass());
    assertSame(Dice.class, boardGameWithTwoDice.getDice().getClass());
  }
}