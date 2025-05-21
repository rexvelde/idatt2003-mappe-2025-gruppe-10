package edu.ntnu.idi.idatt.controller.elements;

import edu.ntnu.idi.idatt.view.elements.DiceView;

public class DiceController {
  private final DiceView diceView;

  public DiceController(DiceView diceView) {
    this.diceView = diceView;
    initialize();
  }

  private void initialize() {
    diceView.getRollButton().setOnAction(event -> {
      diceView.getRollButton().setDisable(true);
      diceView.game.playTurn();
    });
  }
}
