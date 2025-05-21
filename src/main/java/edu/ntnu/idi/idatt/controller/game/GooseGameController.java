package edu.ntnu.idi.idatt.controller.game;

import edu.ntnu.idi.idatt.controller.menu.MainMenuController;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.game.GooseGameBoardView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;

public class GooseGameController {
  private final GooseGameBoardView gooseGameBoardView;

  public GooseGameController(GooseGameBoardView gooseGameBoardView) {
    this.gooseGameBoardView = gooseGameBoardView;
    initialize();
  }

  private void initialize() {
    gooseGameBoardView.getMenuButton().setOnAction(e -> {
      MainMenuView mainMenuView = new MainMenuView();
      MainMenuController mainMenuController = new MainMenuController(mainMenuView);
      ViewManager.setRoot(mainMenuView);
    });
  }
}
