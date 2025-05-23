package edu.ntnu.idi.idatt.controller.menu;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import edu.ntnu.idi.idatt.view.menu.WinScreenView;

import java.util.logging.Level;

public class WinScreenController {
  private final WinScreenView winScreenView;

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
