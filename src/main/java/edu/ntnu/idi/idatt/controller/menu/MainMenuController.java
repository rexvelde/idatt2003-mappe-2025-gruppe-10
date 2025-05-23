package edu.ntnu.idi.idatt.controller.menu;

import edu.ntnu.idi.idatt.controller.Controller;
import edu.ntnu.idi.idatt.controller.edit.EditPlayersController;
//import edu.ntnu.idi.idatt.controller.game.GooseGameController;
import edu.ntnu.idi.idatt.controller.game.GooseGameController;
import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.menu.ChooseBoardView;
import edu.ntnu.idi.idatt.view.edit.EditPlayersView;
import edu.ntnu.idi.idatt.view.game.GooseGameBoardView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URISyntaxException;
import java.util.logging.Level;

/**
 * MainMenuController handles the logic for the MainMenuView.
 * It initializes the view and sets up event listeners for user interactions.
 */
public class MainMenuController implements Controller {
    private final MainMenuView mainMenuView;

    /**
     * Constructor for MainMenuController.
     * Initializes the MainMenuView and sets up event listeners.
     *
     * @param mainMenuView The view to be controlled.
     */
    public MainMenuController(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        initialize();
        LoggerToFile.log(Level.INFO, "All event listeners are active", getClass());
    }

    private void initialize() {
        mainMenuView.getPlayLadderGame().setOnAction(e -> {
            if (!validPlayerList()) {
                displayPlayerlistValidationError();
                return;
            }
            ChooseBoardView chooseBoardView = new ChooseBoardView();
            ChooseBoardController chooseBoardController = new ChooseBoardController(chooseBoardView);
            ViewManager.setRoot(chooseBoardView);
        });

        mainMenuView.getPlayGameOfTheGoose().setOnAction(e -> {
            if (!validPlayerList()) {
                displayPlayerlistValidationError();
                return;
            }
            GooseGameBoardView gooseGameBoardView = null;
            try {
                gooseGameBoardView = new GooseGameBoardView(2, 1);
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

    private boolean validPlayerList() {
        var players = ViewManager.players;
        if (players.isEmpty()) {
            return false;
        }
        return players.stream().allMatch(player ->
                player.getName() != null && !player.getName().isBlank() &&
                player.getPiece() != null && !player.getPiece().isBlank());
    }

    private void displayPlayerlistValidationError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid player list");
        alert.setContentText("Please add at least one player, and make sure all players have a piece selected.");
        alert.setHeaderText(null);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
}
