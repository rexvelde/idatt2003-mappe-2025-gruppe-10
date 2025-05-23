package edu.ntnu.idi.idatt.view.menu;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.view.ViewManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.logging.Level;

/**
 * Main menu view for the Ladder Game and Game of the Goose.
 * This class sets up the main menu layout, including buttons and images.
 */
public class MainMenuView extends BorderPane {
    private Button playLadderGame;
    private Button playGameOfTheGoose;
    private Button editPlayers;

    /**
     * Constructor for the MainMenuView class.
     * Initializes the view with a title, buttons, and images.
     */
    public MainMenuView() {
        Label title = new Label("Ladder Game \n of The Goose");
        title.getStyleClass().add("title-label");
        title.setMinWidth(Region.USE_PREF_SIZE);
        title.setMaxWidth(Region.USE_PREF_SIZE);

        playLadderGame = new Button("Play Ladder Game");
        playLadderGame.getStyleClass().add("play-button");

        playGameOfTheGoose = new Button("Play Game of the Goose");
        playGameOfTheGoose.getStyleClass().add("play-goose-button");

        editPlayers = new Button("Edit Players");
        editPlayers.getStyleClass().add("edit-players-button");

        VBox buttonBox = new VBox(20, playLadderGame, playGameOfTheGoose, editPlayers);
        buttonBox.setAlignment(Pos.CENTER);

        // Stick man for left side of the main menu
        ImageView stickman = new ImageView(new Image("/images/stickman.png"));
        stickman.setFitWidth(270);
        stickman.setPreserveRatio(true);
        stickman.setTranslateY(-170);

        // Ladder for left side of the main menu
        ImageView ladderIcon = new ImageView(new Image("/images/ladder_icon.png"));
        ladderIcon.setFitWidth(200);
        ladderIcon.setPreserveRatio(true);
        ladderIcon.setTranslateY(-80);

        // Snake for left side of the menu
        ImageView snakeIcon = new ImageView(new Image("/images/snake_icon.png"));
        snakeIcon.setFitWidth(200);
        snakeIcon.setPreserveRatio(true);
        snakeIcon.setTranslateY(-80);

        VBox menuBox = new VBox(120, title, buttonBox);
        menuBox.setPadding(new Insets(150, 10, 10, 10));
        menuBox.setAlignment(Pos.TOP_CENTER);

        StackPane ladderAndStickman = new StackPane(ladderIcon, stickman);
        ladderAndStickman.setAlignment(Pos.TOP_CENTER);
        ladderAndStickman.setPadding(new Insets(0, 0, 0, 20));

        VBox leftBox = new VBox(ladderAndStickman);
        leftBox.setAlignment(Pos.BOTTOM_LEFT);
        leftBox.setPadding(new Insets(0, 0, 50, 0));

        VBox rightBox = new VBox(snakeIcon);
        rightBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightBox.setPadding(new Insets(0, 50, 50, 0));

        this.setLeft(leftBox);
        this.setRight(rightBox);
        this.setCenter(menuBox);
        this.setPadding(new Insets(15));

        LoggerToFile.log(Level.INFO, "Main menu has loaded correct", getClass());
    }

    /**
     * For Play Ladder Game button controller.
     */
    public Button getPlayLadderGame() {
        return playLadderGame;
    }

    /**
     * For Play Game of the Goose button controller.
     */
    public Button getPlayGameOfTheGoose() {
        return playGameOfTheGoose;
    }

    /**
     * For Edit Players button controller.
     */
    public Button getEditPlayers() {
        return editPlayers;
    }
}
