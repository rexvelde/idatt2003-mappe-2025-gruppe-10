package edu.ntnu.idi.idatt.view.game;

import edu.ntnu.idi.idatt.controller.elements.DiceController;
import edu.ntnu.idi.idatt.controller.menu.WinScreenController;
import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.board.BoardGameFactory;
import edu.ntnu.idi.idatt.model.board.BoardGame;
import edu.ntnu.idi.idatt.model.board.BoardGameObserver;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.tile.Tile;
import edu.ntnu.idi.idatt.view.edit.PlayerPiece;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.elements.DiceView;
import edu.ntnu.idi.idatt.view.menu.WinScreenView;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class GooseGameBoardView extends BorderPane {
    public BoardGame boardGame;
    private final GridPane boardGrid;
    private final double offset = 20;
    private final VBox sidebar;
    private final Map<Integer, StackPane> tilePane = new HashMap<>();
    private final Map<Player, PlayerPiece> pieces = new HashMap<>();
    private Button exitButton;

    private LoggerToFile logger = new LoggerToFile();

    public GooseGameBoardView(int boardId, int diceAmount) throws InvalidBoardException, URISyntaxException {
        super();
        this.boardGame = new BoardGameFactory().createBoardGameFromFile(boardId, diceAmount);
        this.sidebar = new VBox();
        this.sidebar.getStyleClass().add("in-game-sidebar");
        this.boardGrid = new GridPane();
        this.boardGrid.getStyleClass().add("board-grid");

        this.boardSetup();
        this.sideBarSetup();
        this.spawnPieces();

        boardGame.addObserver(new BoardGameObserver() {
            public void onTurnChanged(Player player) {}

            // When a player moves. Also creates offset for pieces so they are not on top of each other.
            public void onPlayerMoved(Player player, int from, int to) {
                PlayerPiece piece = pieces.get(player);
                StackPane fromPane = tilePane.get(from);
                StackPane toPane = tilePane.get(to);

                if (fromPane != null) {
                    fromPane.getChildren().remove(piece);
                    recalculatePiecePlacement(fromPane);
                }
                if (toPane != null) {
                    toPane.getChildren().add(piece);
                    recalculatePiecePlacement(toPane);
                }
            }

            public void onGameEnded(Player winner) {
                WinScreenView winScreenView = new WinScreenView(winner);
                WinScreenController winScreenController = new WinScreenController(winScreenView);
                ViewManager.setRoot(winScreenView);
            }
        });
    }

    private void boardSetup() {
        int x;
        int y;
        int width = 12;
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

                // RULESET FOR SPIRAL
                // Basically hardcoded according to these rules:
                // Bottom row, 0-11
                // Right col, 12-18
                // Top row, 19-30
                // Left col, 31-35
                // Bottom-mid row, 36-44
                // Right-mid col, 45-47
                // Top-mid row, 48-55
                // Left-mid col, 56
                // Mid-row, 57-63

                // Each parenthesis is used to indicate adjustment of position, based on high value.

                if (i <= 11) {
                    x = i % width;
                    y = height - 1;
                } else if (i <= 18) {
                    x = width - 1;
                    y = height - 3 - (i-13);
                } else if (i <= 30) {
                    x = width - (i-20) - 2;
                    y = 0;
                } else if (i <= 36) {
                    x = 0;
                    y = (i-32)+2;
                } else if (i <= 45) {
                    x = (i-36);
                    y = height - 3;
                } else if (i <= 48) {
                    x = width - 3;
                    y = height - 3 - (i-45);
                } else if (i <= 56) {
                    x = width - (i-48) - 2;
                    y = 2;
                } else if (i == 57) {
                    x = 2;
                    y = 3;
                } else if (i <= 63) {
                    x = (i-57) + 1;
                    y = 4;
                } else {
                    x = 0;
                    y = 0;
                }

                boardGrid.add(stackPane, x, y, 1, 1);
                tilePane.put(currentTile.getTileId(), stackPane);
            } catch (IndexOutOfBoundsException exception) {
                logger.log(Level.SEVERE, exception.getMessage(), getClass());
            }
        }

        logger.log(Level.INFO, "Loaded Board successfully", this.getClass());

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

        logger.log(Level.INFO, "GooseGame loaded board successfully", this.getClass());
    }

    private void sideBarSetup() {
        sidebar.getChildren().clear();

        Label sidebarLabel = new Label("Game controls:");
        sidebarLabel.getStyleClass().add("sidebar-label");

        DiceView diceView = new DiceView(boardGame, 1);
        DiceController diceController = new DiceController(diceView);

        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("in-game-exit-button");

        sidebar.getChildren().addAll(sidebarLabel, diceView, exitButton);
        sidebar.setSpacing(15);
        sidebar.setAlignment(Pos.TOP_CENTER);

        this.setRight(sidebar);
        logger.log(Level.INFO, "GooseGame has successfully loaded sidebar", this.getClass());
    }

    private void spawnPieces() {
        for (Player player : ViewManager.players) {
            PlayerPiece piece = new PlayerPiece(player);
            pieces.put(player, piece);

            // Spawns the player on the first tile
            tilePane.get(1).getChildren().add(piece);
            recalculatePiecePlacement(tilePane.get(1));
            boardGame.addPlayer(player);

            // Oppdatere spillernavn label i begynnelsen
            if (!boardGame.getPlayers().isEmpty()) {
                boardGame.notifyTurnChanged(boardGame.getPlayers().getFirst());
            }
        }
        boardGame.startGame();
        logger.log(Level.INFO, "GooseGame has successfully started game", this.getClass());
    }

    /**
     * Recalculates the placement of pieces in the stack pane.
     * This is used to ensure that pieces are not overlapping.
     *
     * @param pane The StackPane containing the pieces that are getting recalculated.
     */
    private void recalculatePiecePlacement(StackPane pane) {
        List<PlayerPiece> piecesInPane = pane.getChildren().stream()
                .filter(node -> node instanceof PlayerPiece)
                .map(node -> (PlayerPiece) node)
                .toList();
        int n = piecesInPane.size();
        for (int i = 0; i < n; i++) {
            double deltaX = (i - (n - 1) / 2.0) * offset;
            piecesInPane.get(i).setTranslateX(deltaX);
            piecesInPane.get(i).setTranslateY(0);
        }

        logger.log(Level.INFO, "GooseGame has successfully recalculated pieces", this.getClass());
    }

    public Dialog<ButtonType> exitDialog() {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Leave game");
        ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
        ButtonType stay = new ButtonType("Stay", ButtonBar.ButtonData.NO);
        dialog.setContentText("If you leave the game, you will lose the progress.\nDo you want to continue?");
        dialog.getDialogPane().getButtonTypes().addAll(stay, exit);
        return dialog;
    }

    public Button getExitButton() {
        return exitButton;
    }
}