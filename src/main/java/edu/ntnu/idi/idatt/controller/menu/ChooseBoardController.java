package edu.ntnu.idi.idatt.controller.menu;

import edu.ntnu.idi.idatt.controller.game.LadderGameController;
import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.board.Board;
import edu.ntnu.idi.idatt.model.board.BoardGame;
import edu.ntnu.idi.idatt.model.board.BoardGameFactory;
import edu.ntnu.idi.idatt.model.fileHandler.JsonBoardFileHandler;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.menu.ChooseBoardView;
import edu.ntnu.idi.idatt.view.game.LadderBoardView;
import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;

import static edu.ntnu.idi.idatt.view.ViewManager.setRoot;

/**
 * ChooseBoardController handles the logic for the ChooseBoardView.
 * It initializes the view and sets up event listeners for user interactions.
 */
public class ChooseBoardController {
  private final ChooseBoardView chooseBoardView;

    /**
     * Constructor for ChooseBoardController.
     * Initializes the ChooseBoardView and sets up event listeners.
     *
     * @param chooseBoardView The view to be controlled.
     */
  public ChooseBoardController(ChooseBoardView chooseBoardView) {
    this.chooseBoardView = chooseBoardView;
    initialize();
    LoggerToFile.log(Level.INFO, "All events have been added", getClass());
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
        BoardGame boardGame = new BoardGameFactory().createBoardGameFromUploadedFile(selectedFile);

        LadderBoardView boardView = new LadderBoardView(boardGame);
        LadderGameController controller = new LadderGameController(boardView);
        ViewManager.setRoot(boardView);

        LoggerToFile.log(Level.INFO, "JSON-board have been uploaded", getClass());
      } catch (InvalidBoardException ee) {
        new Alert(Alert.AlertType.ERROR, "Board failed to load: " + ee.getMessage()).showAndWait();
        LoggerToFile.log(Level.WARNING, "Invalid JSON-board", getClass());
      }
    });

    // Choose Slippery Slope
    chooseBoardView.getSlipperySlopeBox().setOnMouseClicked(e -> {
      LoggerToFile.log(Level.INFO, "Slippery Slope has been selected", getClass());
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
      LoggerToFile.log(Level.INFO, "Snake Pit has been selected", getClass());
      LadderBoardView ladderBoardView = null;
      try {
        ladderBoardView = new LadderBoardView(1);
      } catch (InvalidBoardException | URISyntaxException ex) {
        throw new RuntimeException(ex);
      }
      LadderGameController ladderGameController = new LadderGameController(ladderBoardView);
      ViewManager.setRoot(ladderBoardView);
    });

    chooseBoardView.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(mainMenuView);
        setRoot(mainMenuView);
      }
    });
  }
}
