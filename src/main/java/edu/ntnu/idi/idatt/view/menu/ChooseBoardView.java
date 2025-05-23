package edu.ntnu.idi.idatt.view.menu;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.logging.Level;

public class ChooseBoardView extends BorderPane {
    private final Button importJsonButton;
    private final Button backButton;
    private static VBox snakePitBox;
    private static VBox slipperySlopeBox;

    public ChooseBoardView() {
        Label titleLabel = new Label("Choose Board");
        titleLabel.getStyleClass().add("view-title");

        ImageView snakeIcon = new ImageView(new Image("/images/snake_icon.png"));
        snakeIcon.setFitWidth(100);
        snakeIcon.setFitHeight(100);

        HBox titleBox = new HBox(10, titleLabel, snakeIcon);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(40, 20, 20, 20));
        setTop(titleBox);

        LoggerToFile.log(Level.INFO, "Loaded structure", getClass());

        // --- Snake Pit ---
        ImageView snakePitPreview = new ImageView(new Image("/images/snakePitPreview.png"));
        snakePitPreview.setFitWidth(200);
        snakePitPreview.setFitHeight(200);
        snakePitPreview.getStyleClass().add("board-preview");
        Label snakePitLabel = new Label("Snake Pit");
        snakePitLabel.getStyleClass().add("board-label");

        VBox snakePitBox = getVBox(snakePitPreview, snakePitLabel);

        LoggerToFile.log(Level.INFO, "Loaded Snake Pit", getClass());

        // --- Slippery Slope ---
        ImageView slipperySlopePreview = new ImageView(new Image("/images/slipperySlopePreview.png"));
        slipperySlopePreview.setFitWidth(200);
        slipperySlopePreview.setFitHeight(200);
        slipperySlopePreview.getStyleClass().add("board-preview");
        Label slipperySlopeLabel = new Label("Slippery Slope");
        slipperySlopeLabel.getStyleClass().add("board-label");

        slipperySlopeBox = getBox(slipperySlopePreview, slipperySlopeLabel);

        LoggerToFile.log(Level.INFO, "Loaded Slippery Slope", getClass());

        // User import board from JSON file
        importJsonButton = new Button();
        importJsonButton.getStyleClass().add("import-board-button");

        ImageView importJsonIcon = new ImageView(new Image("/images/upload_icon.png"));
        importJsonIcon.setFitWidth(40);
        importJsonIcon.setFitHeight(40);
        importJsonButton.setGraphic(importJsonIcon);

        Label importJsonLabel = new Label("Import board from JSON");
        importJsonLabel.getStyleClass().add("import-board-label");

        VBox importJsonBox = getImportJsonBox(importJsonButton, importJsonLabel);

        LoggerToFile.log(Level.INFO, "Loaded Import Json", getClass());

        // The predefined boards
        HBox predefinedBoardsBox = new HBox(40, snakePitBox, slipperySlopeBox);
        predefinedBoardsBox.setAlignment(Pos.CENTER);

        // Adding everything to a VBox
        VBox boardsBox = new VBox(40, predefinedBoardsBox, importJsonBox);
        boardsBox.setAlignment(Pos.CENTER);
        boardsBox.setPadding(new Insets(20));

        setCenter(boardsBox);

        backButton = new Button("Back");
        backButton.getStyleClass().add("back-button");

        HBox bottomBox = new HBox(backButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(20));
        setBottom(bottomBox);

        this.getStyleClass().add("choose-board-view");
        LoggerToFile.log(Level.INFO, "Everything has loaded correct", getClass());
    }

    private VBox getImportJsonBox(Button importJsonIcon, Label importJsonLabel) {
        VBox importJsonBox = new VBox(15, importJsonIcon, importJsonLabel);
        importJsonBox.setAlignment(Pos.CENTER);
        return importJsonBox;
    }

    private static VBox getBox(ImageView slipperySlopePreview, Label slipperySlopeLabel) {
        slipperySlopeBox = new VBox(10, slipperySlopePreview, slipperySlopeLabel);
        slipperySlopeBox.setAlignment(Pos.CENTER);
        return slipperySlopeBox;
    }

    private static VBox getVBox(ImageView snakePitPreview, Label snakePitLabel) {
        snakePitBox = new VBox(10, snakePitPreview, snakePitLabel);
        snakePitBox.setAlignment(Pos.CENTER);
        return snakePitBox;
    }

    public Button getImportJsonButton() {
        return importJsonButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public VBox getSnakePitBox() {
        return snakePitBox;
    }

    public VBox getSlipperySlopeBox() {
        return slipperySlopeBox;
    }
}
