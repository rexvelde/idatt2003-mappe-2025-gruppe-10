package edu.ntnu.idi.idatt.view.menu;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.player.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.logging.Level;

/**
 * View for the win screen in the Ladder Game.
 * Displays the winner's name and a button to return to the main menu.
 */
public class WinScreenView extends BorderPane {
    private Button menuButton;

    /**
     * Constructor for the WinScreenView class.
     * Initializes the view with a title, winner's name, and a button to return to the main menu.
     *
     * @param winner The player who won the game.
     */
    public WinScreenView(Player winner) {
        // Displaying text for the winner
        Label titleLabel = new Label("Congratulations, " + winner.getName() + "!");
        titleLabel.getStyleClass().add("winner-title");

        Label winScreenText = new Label("You won this round!");
        winScreenText.getStyleClass().add("winner-text");

        // Button for going back to the home screen
        menuButton = new Button("Back to menu");
        menuButton.getStyleClass().add("win-screen-button");

        // The layout
        VBox winBox = new VBox(20, titleLabel, winScreenText, menuButton);
        winBox.setAlignment(Pos.CENTER);
        getStyleClass().add("win-screen");

        setCenter(winBox);
        LoggerToFile.log(Level.INFO, "Win screen successfully displayed", getClass());
    }

    /**
     * Returns the button to go back to the main menu.
     * For the controller.
     *
     * @return The button to go back to the main menu.
     */
    public Button getMenuButton() {
        return menuButton;
    }
}
