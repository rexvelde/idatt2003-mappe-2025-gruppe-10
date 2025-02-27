package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.List;

public class Dice {
    private List<Die> dice;

    public Dice(int numberOfDice) {
        dice = new ArrayList<>(numberOfDice);
        for (int i = 0; i < numberOfDice; i++) {
            dice.add(new Die());
        }
    }

    public int roll() {
        int sum = 0;
        for (Die die : dice) {
            die.roll();
            sum += die.getValue();
        }
        return sum;
    }

    public int getDie(int dieNumber) {
        return dice.get(dieNumber).getValue();
    }
}
