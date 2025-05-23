package edu.ntnu.idi.idatt.controller.game;

import edu.ntnu.idi.idatt.controller.Controller;
import edu.ntnu.idi.idatt.controller.menu.MainMenuController;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.view.game.LadderBoardView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;
import java.util.logging.Level;

import static edu.ntnu.idi.idatt.view.ViewManager.setRoot;

public class LadderGameController implements Controller {
  private final LadderBoardView ladderBoardView;

  public LadderGameController(LadderBoardView ladderBoardView) {
    this.ladderBoardView = ladderBoardView;
    initialize();
    LoggerToFile.log(Level.INFO, "All event listeners are active", getClass());
  }

  private void initialize() {
    this.ladderBoardView.getExitButton().setOnAction(event -> {
      Dialog<ButtonType> dialog = ladderBoardView.exitDialog();
      Optional<ButtonType> result = dialog.showAndWait();
      LoggerToFile.log(Level.INFO, result.get().toString(), getClass());

      if (result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(mainMenuView);
        setRoot(mainMenuView);
      }
    });
  }
}
