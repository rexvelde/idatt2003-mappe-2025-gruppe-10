package edu.ntnu.idi.idatt.view.edit;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.view.game.BoardView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import edu.ntnu.idi.idatt.view.menu.ViewManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URISyntaxException;

import static edu.ntnu.idi.idatt.view.menu.ViewManager.setRoot;

public class ChooseBoardView extends BorderPane {

    public ChooseBoardView() {
        Label titleLabel = new Label("Choose Board");
        titleLabel.getStyleClass().add("view-title");

        ImageView snakeIcon = new ImageView(new Image("/images/snake_icon.png"));
        snakeIcon.setFitWidth(48);
        snakeIcon.setFitHeight(48);

        HBox titleBox = new HBox(10, titleLabel, snakeIcon);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(40, 20, 20, 20));
        setTop(titleBox);

        // --- Snake Pit ---
        ImageView snakePitPreview = new ImageView(new Image("/images/snakePitPreview.png"));
        snakePitPreview.setFitWidth(200);
        snakePitPreview.setFitHeight(200);
        Label snakePitLabel = new Label("Snake Pit");
        snakePitLabel.getStyleClass().add("board-label");

        VBox snakePitBox = getVBox(snakePitPreview, snakePitLabel);

        // --- Slippery Slope ---
        ImageView slipperySlopePreview = new ImageView(new Image("/images/slipperySlopePreview.png"));
        slipperySlopePreview.setFitWidth(200);
        slipperySlopePreview.setFitHeight(200);
        Label slipperySlopeLabel = new Label("Slippery Slope");
        slipperySlopeLabel.getStyleClass().add("board-label");

        VBox slipperySlopeBox = getBox(slipperySlopePreview, slipperySlopeLabel);

        HBox boardsBox = new HBox(40, snakePitBox, slipperySlopeBox);
        boardsBox.setAlignment(Pos.CENTER);
        boardsBox.setPadding(new Insets(20));
        setCenter(boardsBox);

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            MainMenuView mainMenuView = new MainMenuView();
            setRoot(mainMenuView);
        });

        HBox bottomBox = new HBox(backButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(20));
        setBottom(bottomBox);

        this.getStyleClass().add("choose-board-view");
    }

    private static VBox getBox(ImageView slipperySlopePreview, Label slipperySlopeLabel) {
        VBox slipperySlopeBox = new VBox(10, slipperySlopePreview, slipperySlopeLabel);
        slipperySlopeBox.setAlignment(Pos.CENTER);

        slipperySlopeBox.setOnMouseClicked(e -> {
            System.out.println("Slippery Slope selected");
            BoardView boardView = null;
            try {
                boardView = new BoardView(1);
            } catch (InvalidBoardException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
            ViewManager.setRoot(boardView);
        });
        return slipperySlopeBox;
    }

    private static VBox getVBox(ImageView snakePitPreview, Label snakePitLabel) {
        VBox snakePitBox = new VBox(10, snakePitPreview, snakePitLabel);
        snakePitBox.setAlignment(Pos.CENTER);

        snakePitBox.setOnMouseClicked(e -> {
            System.out.println("Snake Pit selected");
            BoardView boardView = null;
            try {
                boardView = new BoardView(0);
            } catch (InvalidBoardException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
            ViewManager.setRoot(boardView);
        });
        return snakePitBox;
    }
}
