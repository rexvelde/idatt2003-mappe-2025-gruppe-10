package edu.ntnu.idi.idatt.view.menu;

import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.edit.ChooseBoardView;
import edu.ntnu.idi.idatt.view.edit.EditPlayersView;
import edu.ntnu.idi.idatt.view.game.GooseGameBoardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuView extends BorderPane {
    public MainMenuView() {
        Label title = new Label("Ladder Game \n of The Goose");
        title.getStyleClass().add("title-label");
        title.setMinWidth(Region.USE_PREF_SIZE);
        title.setMaxWidth(Region.USE_PREF_SIZE);

        Button playLadderGame = new Button("Play Ladder Game");
        playLadderGame.getStyleClass().add("play-button");
        playLadderGame.setOnAction(event -> {
            ChooseBoardView chooseBoardView = new ChooseBoardView();
            ViewManager.setRoot(chooseBoardView);
        });

        Button playGameOfTheGoose = new Button("Play Game of the Goose");
        playGameOfTheGoose.getStyleClass().add("play-goose-button");
        playGameOfTheGoose.setOnAction(event -> {
            GooseGameBoardView gooseGameBoardView = new GooseGameBoardView();
            ViewManager.setRoot(gooseGameBoardView);
        });

//    play.setOnAction(event -> {
//      BoardView boardView = null;
//      try {
//        boardView = new BoardView(0);
//      } catch (InvalidBoardException | URISyntaxException ex) {
//        throw new RuntimeException(ex);
//      }
//      ViewManager.setRoot(boardView);
//    });

        Button editPlayers = new Button("Edit Players");
        editPlayers.getStyleClass().add("edit-players-button");
        editPlayers.setOnAction(e -> {
            EditPlayersView editPlayersView = new EditPlayersView();
            ViewManager.setRoot(editPlayersView);
        });

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
    }
}
