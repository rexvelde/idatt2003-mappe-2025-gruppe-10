package edu.ntnu.idi.idatt.model.dice;

import java.util.Random;

public class Die {
    private int lastRolledValue;

    public void roll() {
        lastRolledValue = new Random().nextInt(5) + 1;
    }

    public int getValue() {
        return lastRolledValue;
    }
}
