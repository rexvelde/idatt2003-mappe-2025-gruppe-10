package edu.ntnu.idi.idatt.controller.elements;

import edu.ntnu.idi.idatt.controller.Controller;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.view.elements.DiceView;

import java.util.logging.Level;

public class DiceController implements Controller {
  private final DiceView diceView;

  public DiceController(DiceView diceView) {
    this.diceView = diceView;
    initialize();
    LoggerToFile.log(Level.INFO, "All event listeners are active", getClass());
  }

  private void initialize() {
    diceView.getRollButton().setOnAction(event -> {
      diceView.getRollButton().setDisable(true);
      diceView.game.playTurn();
    });
  }
}
