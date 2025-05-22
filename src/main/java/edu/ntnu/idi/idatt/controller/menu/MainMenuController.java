package edu.ntnu.idi.idatt.controller.menu;

import edu.ntnu.idi.idatt.controller.edit.EditPlayersController;
//import edu.ntnu.idi.idatt.controller.game.GooseGameController;
import edu.ntnu.idi.idatt.controller.game.GooseGameController;
import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.menu.ChooseBoardView;
import edu.ntnu.idi.idatt.view.edit.EditPlayersView;
import edu.ntnu.idi.idatt.view.game.GooseGameBoardView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;

import java.net.URISyntaxException;

public class MainMenuController {
  private final MainMenuView mainMenuView;

  public MainMenuController(MainMenuView mainMenuView) {
    this.mainMenuView = mainMenuView;
    initialize();
  }

  private void initialize() {
    mainMenuView.getPlayLadderGame().setOnAction(e -> {
      ChooseBoardView chooseBoardView = new ChooseBoardView();
      ChooseBoardController chooseBoardController = new ChooseBoardController(chooseBoardView);
      ViewManager.setRoot(chooseBoardView);
    });

    mainMenuView.getPlayGameOfTheGoose().setOnAction(e -> {
      GooseGameBoardView gooseGameBoardView = null;
      try {
        gooseGameBoardView = new GooseGameBoardView(1);
      } catch (InvalidBoardException | URISyntaxException ex) {
        throw new RuntimeException(ex);
      }
      GooseGameController gooseGameController = new GooseGameController(gooseGameBoardView);
      ViewManager.setRoot(gooseGameBoardView);
    });

    mainMenuView.getEditPlayers().setOnAction(e -> {
      EditPlayersView editPlayersView = new EditPlayersView();
      EditPlayersController editPlayersController = new EditPlayersController(editPlayersView);
      ViewManager.setRoot(editPlayersView);
    });
  }
}
