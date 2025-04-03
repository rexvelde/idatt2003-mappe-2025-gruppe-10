package edu.ntnu.idi.idatt.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class EditPlayersView extends BorderPane {

  private final GridPane playerGrid;
  private final int MAX_PLAYERS = 5;

  public EditPlayersView() {
    // Header with title
    Label titleLabel = new Label("Edit Players");
    titleLabel.getStyleClass().add("view-title");
    StackPane header = new StackPane(titleLabel);
    header.setPadding(new Insets(20));
    setTop(header);

    // Player grid setup
    playerGrid = new GridPane();
    playerGrid.setHgap(10);
    playerGrid.setVgap(10);
    playerGrid.setPadding(new Insets(20));
    playerGrid.getStyleClass().add("player-grid");
    playerGrid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    playerGrid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

    // Add column headers
    Label playerNameHeader = new Label("Player name");
    playerNameHeader.getStyleClass().add("column-header");
    Label pieceHeader = new Label("Choose piece:");
    pieceHeader.getStyleClass().add("column-header");

    playerGrid.add(playerNameHeader, 1, 0);
    playerGrid.add(pieceHeader, 2, 0);

    // Add rows for each player
    setupPlayerRows();

    // Done button
    Button doneButton = new Button("Done");
    doneButton.getStyleClass().add("done-button");
    doneButton.setOnAction(event -> {
      // Return to main menu
      MainMenuView mainMenuView = new MainMenuView();
      ViewManager.setRoot(mainMenuView);
    });

    StackPane buttonContainer = new StackPane(doneButton);
    buttonContainer.setAlignment(Pos.CENTER_RIGHT);
    buttonContainer.setPadding(new Insets(20));

    // Main layout
    setTop(header);
    setCenter(playerGrid);
    setBottom(buttonContainer);
    getStyleClass().add("edit-players-view");
  }

  private void setupPlayerRows() {
    // First two players (hardcoded for now, gonna change)
    addPlayerRow(1, "Elias");
    addPlayerRow(2, "Harald");

    // Add empty rows for the rest
    for (int i = 3; i <= MAX_PLAYERS; i++) {
      addPlayerRow(i, "");
    }
  }

  private void addPlayerRow(int playerNumber, String playerName) {
    // Player number label
    Label numLabel = new Label(Integer.toString(playerNumber));
    numLabel.getStyleClass().add("player-number");

    // Player name field
    TextField nameField = new TextField(playerName);
    nameField.getStyleClass().add("player-name-field");

    // Game piece options
    HBox pieceOptions = createPieceOptions();

    // Add components to grid
    playerGrid.add(numLabel, 0, playerNumber);
    playerGrid.add(nameField, 1, playerNumber);
    playerGrid.add(pieceOptions, 2, playerNumber);
  }

  private HBox createPieceOptions() {
    Button diceButton = createPieceButton("/images/gold_dice_icon.png", "dice-piece");
    Button eightBallButton = createPieceButton("/images/eight_ball.png", "eightball-piece");
    Button pawnButton = createPieceButton("/images/pawn_piece.png", "pawn-piece");
    Button puzzleButton = createPieceButton("/images/puzzle_icon.png", "puzzle-piece");
    Button coinButton = createPieceButton("/images/coin_icon.png", "coin-piece");

    selectOneButtonOnly(diceButton, eightBallButton, pawnButton, puzzleButton, coinButton);

    HBox pieceBox = new HBox(10, diceButton, eightBallButton, pawnButton, puzzleButton, coinButton);
    pieceBox.setAlignment(Pos.CENTER_LEFT);
    return pieceBox;
  }

  private void selectOneButtonOnly(Button... buttons) {
    for (Button button : buttons) {
      button.setOnAction(event -> {
        // Clear selection on all buttons
        for (Button b : buttons) {
          b.getStyleClass().remove("selected");
        }
        // Add selection to clicked button
        button.getStyleClass().add("selected");
      });
    }
  }

  private Button createPieceButton(String imagePath, String styleClass) {
    try {
      ImageView imageView = new ImageView(new Image(imagePath));
      imageView.setFitHeight(30);
      imageView.setFitWidth(30);

      Button button = new Button();
      button.setGraphic(imageView);
      button.getStyleClass().add("piece-button");
      button.getStyleClass().add(styleClass);

      return button;
    } catch (Exception e) {
      // In case image fails to load
      Button button = new Button("?");
      button.getStyleClass().add("piece-button");
      return button;
    }
  }
}