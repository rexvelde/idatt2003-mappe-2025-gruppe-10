package edu.ntnu.idi.idatt.model.dice;

import edu.ntnu.idi.idatt.logger.LoggerToFile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The Dice class represents a collection of dice used in the game.
 * It allows rolling the dice and retrieving the values of individual dice.
 */
public class Dice {
  private List<Die> dice;

    /**
     * Constructor for the Dice class.
     * Initializes the dice with a given number of dice.
     *
     * @param numberOfDice The number of dice to be created.
     */
  public Dice(int numberOfDice) {
    dice = new ArrayList<>(numberOfDice);
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
    LoggerToFile.log(Level.INFO, "Added " + numberOfDice + " dice", getClass());
  }

    /**
     * Rolls all the dice and returns the sum of their values.
     *
     * @return The sum of values of all rolled dice.
     */
  public int roll() {
    int sum = 0;
    for (Die die : dice) {
      die.roll();
      sum += die.getValue();
    }
    LoggerToFile.log(Level.INFO, "Sum of dice is: " + sum, getClass());
    return sum;
  }

    /**
     * Returns the value of a specific die.
     *
     * @param dieNumber The index of the die to retrieve the value from.
     * @return The value of the specified die.
     * @throws IndexOutOfBoundsException if the dieNumber is out of bounds.
     */
  public int getDie(int dieNumber) {
    if (dieNumber >= dice.size()) {
      LoggerToFile.log(Level.WARNING, "Index is out of bounds. Max: " + dice.size(), getClass());
      throw new IndexOutOfBoundsException();
    }
    return dice.get(dieNumber).getValue();
  }
}
