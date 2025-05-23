package edu.ntnu.idi.idatt.controller.menu;

import edu.ntnu.idi.idatt.controller.Controller;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import edu.ntnu.idi.idatt.view.menu.WinScreenView;

import java.util.logging.Level;

/**
 * WinScreenController handles the logic for the WinScreenView.
 * It initializes the view and sets up event listeners for user interactions.
 */
public class WinScreenController implements Controller {
  private final WinScreenView winScreenView;

    /**
     * Constructor for WinScreenController.
     * Initializes the WinScreenView.
     *
     * @param winScreenView The view to be controlled.
     */
  public WinScreenController(WinScreenView winScreenView) {
    this.winScreenView = winScreenView;
    initialize();
    LoggerToFile.log(Level.INFO, "All event listeners are active", getClass());
  }

  private void initialize() {
    this.winScreenView.getMenuButton().setOnAction(event -> {
      LoggerToFile.log(Level.INFO, "Menu button pressed", getClass());
      MainMenuView mainMenuView = new MainMenuView();
      MainMenuController mainMenuController = new MainMenuController(mainMenuView);
      ViewManager.setRoot(mainMenuView);
    });
  }
}
