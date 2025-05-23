package edu.ntnu.idi.idatt.controller.edit;

import edu.ntnu.idi.idatt.controller.Controller;
import edu.ntnu.idi.idatt.controller.menu.MainMenuController;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.edit.EditPlayersView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.scene.input.KeyCode;

import java.util.logging.Level;

import static edu.ntnu.idi.idatt.view.ViewManager.setRoot;
/**
 * EditPlayersController handles the logic for the EditPlayersView.
 * It initializes the view and sets up event listeners for user interactions.
 */
public class EditPlayersController implements Controller {
  EditPlayersView editPlayersView;

  /**
   * Constructor for EditPlayersController.
   * Initializes the EditPlayersView and sets up event listeners.
   *
   * @param editPlayersView The view to be controlled.
   */
  public EditPlayersController(EditPlayersView editPlayersView) {
    this.editPlayersView = editPlayersView;
    initialize();
    LoggerToFile.log(Level.INFO, "All event listeners are active", getClass());
  }

  private void initialize() {
    // Done button
    editPlayersView.getDoneButton().setOnAction(e -> {
      try {
        editPlayersView.updatePlayersFromGrid();
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(mainMenuView);
        ViewManager.setRoot(mainMenuView);
        } catch (Exception ex) {
        editPlayersView.statusLabel.setText("Alle spillere må ha valgt en brikke før du kan gå videre!");
      }
    });

    editPlayersView.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        editPlayersView.updatePlayersFromGrid();
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(mainMenuView);
        setRoot(mainMenuView);
      }
    });
  }
}
