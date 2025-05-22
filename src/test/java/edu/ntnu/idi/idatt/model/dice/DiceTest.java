package edu.ntnu.idi.idatt.model.dice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
  private Dice diceWithOneDice;
  private Dice diceWithTwoDice;

  @BeforeEach
  void setUp() {
    diceWithOneDice = new Dice(1);
    diceWithTwoDice = new Dice(2);
  }

  @AfterEach
  void tearDown() {
    diceWithOneDice = null;
    diceWithTwoDice = null;
  }

  @Test
  void rollOneDice() {
    for (int i = 0; i < 1000; i++) {
      int roll = diceWithOneDice.roll();
      assertTrue(1 <= roll || roll <= 6);
    }
  }

  @Test
  void rollTwoDice() {
    for (int i = 0; i < 1000; i++) {
      int roll = diceWithTwoDice.roll();
      assertTrue(2 <= roll || roll <= 12);
    }
  }

  @Test
  void getDie() {
    for (int i = 0; i < 1000; i++) {
      assertTrue( 1 <= diceWithOneDice.getDie(0) || diceWithOneDice.getDie(0) <= 6);
    }
  }

  @Test
  void getDieWhenNotInList() {
    assertThrows(IndexOutOfBoundsException.class, () -> diceWithOneDice.getDie(2));
  }
}