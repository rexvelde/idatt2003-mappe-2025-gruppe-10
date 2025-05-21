package edu.ntnu.idi.idatt.view.game;

import edu.ntnu.idi.idatt.controller.menu.MainMenuController;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GooseGameBoardView extends BorderPane {
    private Button menuButton;

    public GooseGameBoardView() {
        Label goose = new Label("Game of The Goose");

        // Button for going back to the home screen
        menuButton = new Button("Back to menu");
        menuButton.getStyleClass().add("win-screen-button");

        // The layout
        VBox gooseBox = new VBox(20, menuButton);
        gooseBox.setAlignment(Pos.CENTER);

        setCenter(gooseBox);
    }

    public Button getMenuButton() {
        return menuButton;
    }
}
