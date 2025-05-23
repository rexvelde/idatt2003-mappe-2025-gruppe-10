package edu.ntnu.idi.idatt.model.dice;

import java.util.Random;

/**
 * The Die class represents a single die that can be rolled to generate a random value between 1 and 6.
 * It provides methods to roll the die and retrieve the last rolled value.
 */
public class Die {
    private int lastRolledValue;

    public void roll() {
        lastRolledValue = new Random().nextInt(5) + 1;
    }

    public int getValue() {
        return lastRolledValue;
    }
}
