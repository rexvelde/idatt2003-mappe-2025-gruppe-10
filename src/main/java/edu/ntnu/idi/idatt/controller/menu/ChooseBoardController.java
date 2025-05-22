package edu.ntnu.idi.idatt.controller.menu;

import edu.ntnu.idi.idatt.controller.game.LadderGameController;
import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.model.board.Board;
import edu.ntnu.idi.idatt.model.board.BoardGame;
import edu.ntnu.idi.idatt.model.fileHandler.JsonBoardFileHandler;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.menu.ChooseBoardView;
import edu.ntnu.idi.idatt.view.game.LadderBoardView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URISyntaxException;

import static edu.ntnu.idi.idatt.view.ViewManager.setRoot;

public class ChooseBoardController {
  private final ChooseBoardView chooseBoardView;
  private final int diceAmount = 2;

  public ChooseBoardController(ChooseBoardView chooseBoardView) {
    this.chooseBoardView = chooseBoardView;
    initialize();
  }

  private void initialize() {
    // Back Button
    chooseBoardView.getBackButton().setOnAction(e -> {
      MainMenuView mainMenuView = new MainMenuView();
      MainMenuController mainMenuController = new MainMenuController(mainMenuView);
      setRoot(mainMenuView);
    });

    // Import JSON-file button
    chooseBoardView.getImportJsonButton().setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Select JSON File");
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));

      File selectedFile = fileChooser.showOpenDialog(this.chooseBoardView.getScene().getWindow());
      if (selectedFile == null) {
        return;
      }

      try {
        JsonBoardFileHandler handler = new JsonBoardFileHandler();
        Board board = handler.readBoardFromJsonFile(selectedFile.getAbsolutePath());
        BoardGame boardGame = new BoardGame(board, diceAmount);

        LadderBoardView boardView = new LadderBoardView(boardGame);
        ViewManager.setRoot(boardView);
      } catch (InvalidBoardException ee) {
        new Alert(Alert.AlertType.ERROR, "Board failed to load: " + ee.getMessage()).showAndWait();
      }
    });

    // Choose Slippery Slope
    chooseBoardView.getSlipperySlopeBox().setOnMouseClicked(e -> {
      System.out.println("Slippery Slope selected");
      LadderBoardView ladderBoardView;
      try {
        ladderBoardView = new LadderBoardView(0);
        ViewManager.setRoot(ladderBoardView);
      } catch (InvalidBoardException | URISyntaxException ex) {
        throw new RuntimeException(ex);
      }
      LadderGameController ladderGameController = new LadderGameController(ladderBoardView);
      ViewManager.setRoot(ladderBoardView);
    });

    // Choose Snake Pit
    chooseBoardView.getSnakePitBox().setOnMouseClicked(e -> {
      System.out.println("Snake Pit selected");
      LadderBoardView ladderBoardView = null;
      try {
        ladderBoardView = new LadderBoardView(1);
      } catch (InvalidBoardException | URISyntaxException ex) {
        throw new RuntimeException(ex);
      }
      LadderGameController ladderGameController = new LadderGameController(ladderBoardView);
      ViewManager.setRoot(ladderBoardView);
    });
  }
}
