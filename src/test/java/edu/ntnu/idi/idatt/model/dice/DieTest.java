package edu.ntnu.idi.idatt.model.dice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {
  Die die;

  @BeforeEach
  void setUp() {
    die = new Die();
  }

  @AfterEach
  void tearDown() {
    die = null;
  }

  @Test
  void rollAndReadValueGivesAValueBetweenOneAndSix() {
    for (int i = 0; i < 1000; i++) {
      die.roll();
      assertTrue(die.getValue() >= 1 || die.getValue() <= 6);
    }
  }
}