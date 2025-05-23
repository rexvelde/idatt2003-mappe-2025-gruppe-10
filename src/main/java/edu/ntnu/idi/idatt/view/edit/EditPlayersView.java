package edu.ntnu.idi.idatt.view.edit;

import edu.ntnu.idi.idatt.controller.menu.MainMenuController;
import edu.ntnu.idi.idatt.exception.PlayerFileFormatException;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.fileHandler.CsvPlayerFileHandler;
import edu.ntnu.idi.idatt.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import edu.ntnu.idi.idatt.view.ViewManager;
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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class EditPlayersView extends BorderPane {

  private static final Logger logger = Logger.getLogger(EditPlayersView.class.getName());
  private final GridPane playerGrid;
  private final int MAX_PLAYERS = 5;
  private final List<Player> players = ViewManager.players;
  private final Map<Piece, Button> piecesNotAvailable = new HashMap<>();
  private enum Piece { DICE, EIGHTBALL, PAWN, PUZZLE, COIN }

  private final CsvPlayerFileHandler csvHandler = new CsvPlayerFileHandler();
  private final Label statusLabel = new Label();

  private final Button downloadButton;
  private final Button uploadButton;
  private final Button doneButton;


  public EditPlayersView() {
    // Header with title
    Label titleLabel = new Label("Edit Players");
    titleLabel.getStyleClass().add("view-title");
    StackPane header = new StackPane(titleLabel);
    header.setPadding(new Insets(140,20,20,20));
    setTop(header);

    // Player grid setup
    playerGrid = new GridPane();
    playerGrid.setHgap(10);
    playerGrid.setVgap(10);
    playerGrid.setPadding(new Insets(20));
    playerGrid.getStyleClass().add("player-grid");
    playerGrid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    playerGrid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

    // Adding column headers
    Label playerNameHeader = new Label("Player name:");
    playerNameHeader.getStyleClass().add("column-header");
    Label pieceHeader = new Label("Choose piece:");
    pieceHeader.getStyleClass().add("column-header");

    playerGrid.add(playerNameHeader, 1, 0);
    playerGrid.add(pieceHeader, 2, 0);

    // Set up player rows
    setupPlayerRows();

    // Button icons
    ImageView downloadIcon = new ImageView(new Image("/images/download_icon.png"));
    downloadIcon.setFitWidth(24);
    downloadIcon.setFitHeight(24);

    ImageView uploadIcon = new ImageView(new Image("/images/upload_icon.png"));
    uploadIcon.setFitWidth(24);
    uploadIcon.setFitHeight(24);

    // Download button
    VBox downloadBox = new VBox(5);
    downloadButton = new Button("", downloadIcon);
    downloadButton.getStyleClass().add("download-button");
    Label downloadLabel = new Label("Download as CSV");
    downloadLabel.getStyleClass().add("button-label");
    downloadBox.getChildren().addAll(downloadButton, downloadLabel);
    downloadBox.setAlignment(Pos.CENTER);
    downloadButton.setOnAction(e -> saveToCsv());

    // Upload button
    VBox uploadBox = new VBox(5);
    uploadButton = new Button("", uploadIcon);
    uploadButton.getStyleClass().add("upload-button");
    Label uploadLabel = new Label("Upload from CSV");
    uploadLabel.getStyleClass().add("button-label");
    uploadBox.getChildren().addAll(uploadButton, uploadLabel);
    uploadBox.setAlignment(Pos.CENTER);
    uploadButton.setOnAction(e -> loadFromCsv());

    // Left side HBox
    HBox leftButtons = new HBox(20, downloadBox, uploadBox);
    leftButtons.setAlignment(Pos.CENTER_LEFT);

    // Done button
    doneButton = new Button("Done");
    doneButton.getStyleClass().add("done-button");
    HBox rightButton = new HBox(doneButton);
    rightButton.setAlignment(Pos.CENTER_RIGHT);

    StackPane leftPane = new StackPane(leftButtons);
    leftPane.setAlignment(Pos.CENTER_LEFT);

    StackPane rightPane = new StackPane(rightButton);
    rightPane.setAlignment(Pos.CENTER_RIGHT);

    BorderPane buttonRow = new BorderPane();
    buttonRow.setLeft(leftPane);
    buttonRow.setRight(rightPane);

    buttonRow.maxWidthProperty().bind(playerGrid.widthProperty());

    // Put everything together
    VBox centerContainer = new VBox(10);
    centerContainer.setAlignment(Pos.TOP_CENTER);
    centerContainer.setPadding(new Insets(10));

    centerContainer.getChildren().addAll(playerGrid, buttonRow, statusLabel);

    setCenter(centerContainer);

    getStyleClass().add("edit-players-view");
    LoggerToFile.log(Level.INFO, "All elements have loaded correct", getClass());
  }

  /**
   * Creates a row in grid for each player in players fills in up to MAX_PLAYERS with empty rows.
   */
  private void setupPlayerRows() {

    piecesNotAvailable.clear();
    playerGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
    int rowIndex = 1;

    // Create row for each player
    for (Player player : players) {
      addPlayerRow(rowIndex++, player.getName(), player.getPiece());
    }

    // Add empty rows for the rest
    while (rowIndex <= MAX_PLAYERS) {
      addPlayerRow(rowIndex++, "", "");
    }
  }

  private void addPlayerRow(int rowNumber, String defaultName, String defaultPiece) {
    // Column 0: player number
    Label numLabel = new Label(Integer.toString(rowNumber));
    numLabel.getStyleClass().add("player-number");

    // Column 1: player name
    TextField nameField = new TextField(defaultName);
    nameField.getStyleClass().add("player-name-field");

    // Column 2: Options of pieces
    HBox pieceOptions = createPieceOptions(defaultPiece);

    // Add components to grid
    playerGrid.add(numLabel, 0, rowNumber);
    playerGrid.add(nameField, 1, rowNumber);
    playerGrid.add(pieceOptions, 2, rowNumber);
  }

  public void updatePlayersFromGrid() {
    players.clear(); // Clear existing players and fill again

    for (int row = 1; row <= MAX_PLAYERS; row++) {
      TextField nameField = (TextField) getNodeFromGrid(row, 1);
      if (nameField == null) {
        continue;
      }

      String name = nameField.getText().trim();
      if (name.isEmpty()) {
        continue;
      }

      HBox pieceBox = (HBox) getNodeFromGrid(row, 2);
      String selectedPiece = findSelectedPiece(pieceBox);

      Player player = new Player(name, selectedPiece);
      players.add(player);
    }
  }

  private String findSelectedPiece(HBox pieceBox) {
    if (pieceBox == null) {
      return "";
    }
    for (javafx.scene.Node node : pieceBox.getChildren()) {
      if (node instanceof Button button && Boolean.TRUE.equals(button.getProperties().get("selected"))) {
        return ((Piece) button.getUserData()).name().toLowerCase();
      }
    }
    return "";
  }

  private javafx.scene.Node getNodeFromGrid(int row, int col) {
    for (javafx.scene.Node node : playerGrid.getChildren()) {
      Integer a = GridPane.getColumnIndex(node);
      Integer b = GridPane.getRowIndex(node);

      if (a != null && b != null && a == col && b == row) {
        return node;
      }
    }
    return null;
  }

  private HBox createPieceOptions(String defaultPiece) {
    Button diceButton = createPieceButton("/images/pieces/dice.png", "dice-piece", Piece.DICE);
    Button eightBallButton = createPieceButton("/images/pieces/eightball.png", "eightball-piece", Piece.EIGHTBALL);
    Button pawnButton = createPieceButton("/images/pieces/pawn.png", "pawn-piece", Piece.PAWN);
    Button puzzleButton = createPieceButton("/images/pieces/puzzle.png", "puzzle-piece", Piece.PUZZLE);
    Button coinButton = createPieceButton("/images/pieces/coin.png", "coin-piece", Piece.COIN);

    selectOneButtonOnly(diceButton, eightBallButton, pawnButton, puzzleButton, coinButton);

    if (defaultPiece != null && !defaultPiece.isBlank()) {
      try {
        Piece piece = Piece.valueOf(defaultPiece.toUpperCase());
        for (Button button : new Button[]{diceButton, eightBallButton, pawnButton, puzzleButton, coinButton}) {
          if (button.getUserData() == piece) {
            button.getProperties().put("selected", true);
            button.getStyleClass().add("selected");
            piecesNotAvailable.put(piece, button);
            break;
          }
        }
      } catch (IllegalArgumentException e) {
        logger.warning(() -> "Invalid piece: " + defaultPiece);
      }
    }

    HBox pieceBox =
        new HBox(10, diceButton, eightBallButton, pawnButton, puzzleButton, coinButton);
    pieceBox.setAlignment(Pos.CENTER_LEFT);
    return pieceBox;
  }

  private void selectOneButtonOnly(Button... buttons) {
    for (Button button : buttons) {
      button.setOnAction(event -> {
        Piece pieceId = (Piece) button.getUserData();
        // Hvis en annen rad allerede har brikken, frigjøres den
        Button lastButton = piecesNotAvailable.get(pieceId);
        if (lastButton != button && lastButton != null) {
          lastButton.getStyleClass().remove("selected");
          lastButton.getProperties().remove("selected");
        }

        // Fjerne selected fra de andre knappene i samme rad
        ((HBox) button .getParent()).getChildren().forEach(node -> {
            if (node instanceof Button aButton && aButton != button) {
              aButton.getStyleClass().remove("selected");
              aButton.getProperties().remove("selected");
              piecesNotAvailable.values().remove(aButton);
            }
        });
        // Markere som valgt og så låse for andre rader
        button.getStyleClass().add("selected");
        button.getProperties().put("selected", true);
        piecesNotAvailable.put(pieceId, button);
      });
    }
  }

  private Button createPieceButton(String imagePath, String styleClass, Piece pieceId) {
    try {
      ImageView imageView = new ImageView(new Image(imagePath));
      imageView.setFitHeight(30);
      imageView.setFitWidth(30);

      Button button = new Button();
      button.setGraphic(imageView);
      button.getStyleClass().addAll("piece-button", styleClass);
      button.setUserData(pieceId);

      return button;
    } catch (Exception e) {
      // In case image fails to load
      Button button = new Button("?");
      button.getStyleClass().add("piece-button");
      return button;
    }
  }

  /**
   * Load players from a CSV file.
   */
  private void loadFromCsv() {

    updatePlayersFromGrid();

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select a CSV file to load players from.");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    var file = fileChooser.showOpenDialog(getScene().getWindow());
    if (file == null) {
      statusLabel.setText("File loading cancelled..");
      return;
    }

    try {
      var loadedPlayers = csvHandler.readPlayers(file.toPath());
      // Overwrite list with the playerlist loaded from file
      players.clear();
      players.addAll(loadedPlayers);
      // Reload GUI
      setupPlayerRows();
      statusLabel.setText("Successfully loaded " + loadedPlayers.size() + " players from " + file.getName());
    } catch (PlayerFileFormatException e) {
      statusLabel.setText("Error loading file: " + e.getMessage());
    }
  }

  /**
   * Save players to a CSV file.
   */
  private void saveToCsv() {
    updatePlayersFromGrid();

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Please choose where to save CSV file.");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    var file = fileChooser.showSaveDialog(getScene().getWindow());
    if (file == null) {
      statusLabel.setText("Save has been cancelled.");
      return;
    }

    try {
      csvHandler.writePlayersToCsv(players, file.toPath());
      statusLabel.setText("Saved " + players.size() + " players to " + file.getName());
      LoggerToFile.log(Level.INFO, "Saved " + players.size() + " players to " + file.getName(), getClass());
    } catch (PlayerFileFormatException e) {
      LoggerToFile.log(Level.WARNING, "Error saving CSV: " + e.getMessage(), getClass());
      statusLabel.setText("Error saving CSV: " + e.getMessage());
    }
  }

  public Button getDoneButton() {
    return doneButton;
  }

  public Button getUploadButton() {
    return uploadButton;
  }

  public Button getDownloadButton() {
    return downloadButton;
  }
}