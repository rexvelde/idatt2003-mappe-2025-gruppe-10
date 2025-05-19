package edu.ntnu.idi.idatt.view.game;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.model.board.BoardGame;
import edu.ntnu.idi.idatt.model.fileHandler.CsvPlayerFileHandler;
import edu.ntnu.idi.idatt.model.fileHandler.CsvPlayerFileHandler.BoardGameObserver;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.tile.Tile;
import edu.ntnu.idi.idatt.view.edit.PlayerPiece;
import edu.ntnu.idi.idatt.view.menu.ViewManager;
import edu.ntnu.idi.idatt.view.menu.WinScreenView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class BoardView extends BorderPane {
    public BoardGame boardGame;
    private final GridPane boardGrid;
    private final VBox sidebar;
    private final Map<Integer, StackPane> tilePane = new HashMap<>();
    private final Map<Player, PlayerPiece> pieces = new HashMap<>();


    public BoardView(int boardId) throws InvalidBoardException, URISyntaxException {
        super();
        // this.boardGame = new BoardGame();
        this.boardGame = new CsvPlayerFileHandler.BoardGameFactory().createBoardGameFromFile(boardId);
        this.sidebar = new VBox();
        this.sidebar.getStyleClass().add("in-game-sidebar");
        this.boardGrid = new GridPane();
        this.boardGrid.getStyleClass().add("board-grid");

        this.boardSetup();
        this.sideBarSetup();
        this.spawnPieces();

        boardGame.addObserver(new CsvPlayerFileHandler.BoardGameObserver() {
            public void onTurnChanged(Player player) {
            }

            public void onPlayerMoved(Player player, int from, int to) {
                PlayerPiece piece = pieces.get(player);
                tilePane.get(from).getChildren().remove(piece);
                tilePane.get(to).getChildren().add(piece);
            }

            public void onGameEnded(Player winner) {
                ViewManager.setRoot(new WinScreenView(winner));
            }
        });
    }

    public BoardView(BoardGame boardGame) {
        this.boardGame = boardGame;
        this.sidebar = new VBox();
        this.boardGrid = new GridPane();
        this.boardSetup();
        this.sideBarSetup();
        spawnPieces();

        boardGame.addObserver(new BoardGameObserver() {
            @Override
            public void onTurnChanged(Player player) {
            }

            @Override
            public void onPlayerMoved(Player player, int from, int to) {
                PlayerPiece piece = pieces.get(player);
                StackPane fromPane = tilePane.get(from);
                StackPane toPane = tilePane.get(to);
                if (fromPane != null) fromPane.getChildren().remove(piece);
                if (toPane != null) toPane.getChildren().add(piece);
            }

            @Override
            public void onGameEnded(Player winner) {
                ViewManager.setRoot(new WinScreenView(winner));
            }
        });
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

        for (int i = 0; i < boardGame.getBoard().tiles.size() - 1; i++) {
            try {
                Tile currentTile = boardGame.getBoard().getTile(i + 1);
                Label label = new Label("" + (currentTile.getTileId()));
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(label);
                stackPane.setAlignment(Pos.CENTER);
                stackPane.setPrefSize(75, 75);
                stackPane.getStyleClass().add("tile-pane");

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
                tilePane.put(currentTile.getTileId(), stackPane);
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
        tilePane.forEach((k, v) -> {
            Tile currentTile = boardGame.getBoard().getTile(k);

            // Adding nextTile for every tile here, because that reduces weird coupling.
            // We still recognize that is not the best place for this.
            currentTile.setNextTile(boardGame.getBoard().getTile(currentTile.landAction + 1));

            // Coloring the tiles according to relation with actions
            if (currentTile.isLandAction()) {
                if (currentTile.landAction > currentTile.getTileId()) {
                    v.getStyleClass().add("positive-land-action");
                    tilePane.get(currentTile.landAction).getStyleClass().add("positive-land-target");
                } else {
                    v.getStyleClass().add("negative-land-action");
                    tilePane.get(currentTile.landAction).getStyleClass().add("negative-land-target");
                }
            }
        });

        this.setCenter(boardGrid);
    }

    private void sideBarSetup() {
        sidebar.getChildren().clear();

        Label sidebarLabel = new Label("Game controls:");
        sidebarLabel.getStyleClass().add("sidebar-label");

        DiceView diceView = new DiceView(boardGame);
        sidebar.getChildren().addAll(sidebarLabel, diceView);
        sidebar.setSpacing(15);
        sidebar.setAlignment(Pos.TOP_CENTER);

        this.setRight(sidebar);
    }

    private void spawnPieces() {
        for (Player player : ViewManager.players) {
            PlayerPiece piece = new PlayerPiece(player);
            pieces.put(player, piece);

            // Spawns the player on the first tile
            tilePane.get(1).getChildren().add(piece);
            boardGame.addPlayer(player);
        }
    }
}
