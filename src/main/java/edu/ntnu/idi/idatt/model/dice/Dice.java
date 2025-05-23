package edu.ntnu.idi.idatt.model.dice;

import edu.ntnu.idi.idatt.logger.LoggerToFile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Dice {
  private List<Die> dice;

  public Dice(int numberOfDice) {
    dice = new ArrayList<>(numberOfDice);
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
    LoggerToFile.log(Level.INFO, "Added " + numberOfDice + " dice", getClass());
  }

  public int roll() {
    int sum = 0;
    for (Die die : dice) {
      die.roll();
      sum += die.getValue();
    }
    LoggerToFile.log(Level.INFO, "Sum of dice is: " + sum, getClass());
    return sum;
  }

  public int getDie(int dieNumber) {
    if (dieNumber >= dice.size()) {
      LoggerToFile.log(Level.WARNING, "Index is out of bounds. Max: " + dice.size(), getClass());
      throw new IndexOutOfBoundsException();
    }
    return dice.get(dieNumber).getValue();
  }
}
