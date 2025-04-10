package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.model.Board;
import edu.ntnu.idi.idatt.model.BoardGame;
import edu.ntnu.idi.idatt.model.BoardGameFactory;
import edu.ntnu.idi.idatt.model.Tile;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URISyntaxException;
import java.util.List;

public class BoardView extends BorderPane {
  public BoardGame boardGame;
  private final GridPane boardGrid;
  private final VBox sidebar;


  public BoardView(int boardId) throws InvalidBoardException, URISyntaxException {
    super();
    // this.boardGame = new BoardGame();
    this.boardGame = new BoardGameFactory().createBoardGameFromFile(boardId);
    this.sidebar = new VBox();
    this.sidebar.getStyleClass().add("in-game-sidebar");
    this.boardGrid = new GridPane();
    this.boardGrid.getStyleClass().add("board-grid");

    this.boardSetup();
    this.sideBarSetup();
  }

  private void boardSetup() {
    int x;
    int y;
    int width = 10;
    int height = 9;

    ColumnConstraints columnConstraints = new ColumnConstraints();
    RowConstraints rowConstraints = new RowConstraints();

    columnConstraints.setPercentWidth((double) 100 / height);
    rowConstraints.setPercentHeight((double) 100 / width);

    boardGrid.getColumnConstraints().add(columnConstraints);
    boardGrid.getRowConstraints().add(rowConstraints);

    for (int i = 0; i < 90; i++) {
      try {
        Tile currentTile = boardGame.getBoard().getTile(i + 1);
        Label label = new Label("" + (currentTile.getTileId()));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(label);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setPrefSize(75, 75);
        stackPane.getStyleClass().add("tile-pane");

        if (currentTile.getTileId() % 7 == 3 || currentTile.getTileId() % 13 == 0) {
          stackPane.getStyleClass().add("land-action");
        }

        if (currentTile.getTileId() == boardGame.getBoard().getMaxTileId()) {
          stackPane.getStyleClass().add("final-tile");
        }

        if (i % width == i % (width * 2)) {
          x = i % width;
          y = (height - 1) - (i / width);
        } else {
          x = (width - 1) - i % width;
          y = (height - 1) - i / width;
        }

        boardGrid.add(stackPane, x, y, 1, 1);
      } catch (IndexOutOfBoundsException ignored) {

      }
    }

    this.setCenter(boardGrid);
  }

  private void sideBarSetup() {
    Label sidebarLabel = new Label("Sidebar label");
    sidebar.getChildren().clear();
    sidebar.getChildren().add(sidebarLabel);
    sidebarLabel.getStyleClass().add("sidebar-label");

    this.setRight(sidebar);
  }
}
