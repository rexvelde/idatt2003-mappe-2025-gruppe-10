package edu.ntnu.idi.idatt.controller.edit;

import edu.ntnu.idi.idatt.controller.menu.MainMenuController;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.edit.EditPlayersView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;

public class EditPlayersController {
  EditPlayersView editPlayersView;

  public EditPlayersController(EditPlayersView editPlayersView) {
    this.editPlayersView = editPlayersView;
    initialize();
  }

  private void initialize() {
    // Done button
    editPlayersView.getDoneButton().setOnAction(e -> {
      editPlayersView.updatePlayersFromGrid();
      MainMenuView mainMenuView = new MainMenuView();
      MainMenuController mainMenuController = new MainMenuController(mainMenuView);
      ViewManager.setRoot(mainMenuView);
    });

    // Download button
    // editPlayersView.getDownloadButton().setOnAction();

    // Upload button

    // PlayerGrid
  }
}
