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

/**
 * GooseGameController handles the logic for the Goose Game.
 * It initializes the view and sets up event listeners for user interactions.
 */
public class GooseGameController implements Controller {
  private final GooseGameBoardView gooseGameBoardView;

    /**
     * Constructor for GooseGameController.
     * Initializes the GooseGameBoardView and sets up event listeners.
     *
     * @param gooseGameBoardView The view to be controlled.
     */
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
