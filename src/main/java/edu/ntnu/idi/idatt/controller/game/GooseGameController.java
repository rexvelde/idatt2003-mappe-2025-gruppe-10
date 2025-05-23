package edu.ntnu.idi.idatt.controller.game;

import edu.ntnu.idi.idatt.controller.Controller;
import edu.ntnu.idi.idatt.controller.menu.MainMenuController;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.game.GooseGameBoardView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.logging.Level;

import static edu.ntnu.idi.idatt.view.ViewManager.setRoot;

public class GooseGameController implements Controller {
  private final GooseGameBoardView gooseGameBoardView;

  public GooseGameController(GooseGameBoardView gooseGameBoardView) {
    this.gooseGameBoardView = gooseGameBoardView;
    initialize();
    LoggerToFile.log(Level.INFO, "All event listeners are active", getClass());
  }

  private void initialize() {
    gooseGameBoardView.getExitButton().setOnAction(e -> {
      Optional<ButtonType> result = gooseGameBoardView.exitDialog().showAndWait();
      LoggerToFile.log(Level.INFO, result.get().toString(), getClass());

      if (result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(mainMenuView);
        setRoot(mainMenuView);
      }
    });
  }
}
