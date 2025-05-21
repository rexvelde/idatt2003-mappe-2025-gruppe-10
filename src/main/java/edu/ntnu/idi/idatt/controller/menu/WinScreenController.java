package edu.ntnu.idi.idatt.controller.menu;

import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import edu.ntnu.idi.idatt.view.menu.WinScreenView;

public class WinScreenController {
  private final WinScreenView winScreenView;

  public WinScreenController(WinScreenView winScreenView) {
    this.winScreenView = winScreenView;
    initialize();
  }

  private void initialize() {
    this.winScreenView.getMenuButton().setOnAction(event -> {
      MainMenuView mainMenuView = new MainMenuView();
      MainMenuController mainMenuController = new MainMenuController(mainMenuView);
      ViewManager.setRoot(mainMenuView);
    });
  }
}
