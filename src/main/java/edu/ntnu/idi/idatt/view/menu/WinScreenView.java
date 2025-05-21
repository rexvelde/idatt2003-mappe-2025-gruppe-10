package edu.ntnu.idi.idatt.view.menu;

import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.view.ViewManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class WinScreenView extends BorderPane {
    public WinScreenView(Player winner) {
        // Displaying text for the winner
        Label titleLabel = new Label("Congratulations, " + winner.getName() + "!");
        titleLabel.getStyleClass().add("winner-title");

        Label winScreenText = new Label("You won this round!");
        winScreenText.getStyleClass().add("winner-text");

        // Button for going back to the home screen
        Button menuButton = new Button("Back to menu");
        menuButton.getStyleClass().add("win-screen-button");
        menuButton.setOnAction(event -> {
            ViewManager.setRoot(new MainMenuView());
        });

        // The layout
        VBox winBox = new VBox(20, titleLabel, winScreenText, menuButton);
        winBox.setAlignment(Pos.CENTER);
        getStyleClass().add("win-screen");

        setCenter(winBox);
    }
}
