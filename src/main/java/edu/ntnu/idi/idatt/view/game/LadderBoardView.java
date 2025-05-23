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
import edu.ntnu.idi.idatt.view.edit.PlayerPieceView;
import edu.ntnu.idi.idatt.view.ViewManager;
import edu.ntnu.idi.idatt.view.elements.DiceView;
import edu.ntnu.idi.idatt.view.menu.WinScreenView;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class LadderBoardView extends BorderPane {
    public BoardGame boardGame;
    private final GridPane boardGrid;
    private final Pane actionConnectorPane = new Pane();
    private final double offset = 20;
    private final VBox sidebar;
    private final Map<Integer, StackPane> tilePane = new HashMap<>();
    private final Map<Player, PlayerPieceView> pieces = new HashMap<>();
    private final List<Line> lines = new ArrayList<>();
    private final Pane lineGroup = new Pane();
    private final int diceAmount = 2;

    private Button exitButton;
    private Dialog<ButtonType> dialog;

    public LadderBoardView(int boardId) throws InvalidBoardException, URISyntaxException {
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
            public void onTurnChanged(Player player) {
            }

            // When a player moves. Also creates offset for pieces so they are not on top of each other.
            public void onPlayerMoved(Player player, int from, int to) {
                PlayerPieceView piece = pieces.get(player);
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

        LoggerToFile.log(Level.INFO, "LadderBoard have successfully loaded", getClass());
    }

    public LadderBoardView(BoardGame boardGame) {
        super();
        this.boardGame = boardGame;
        this.sidebar = new VBox();
        this.sidebar.getStyleClass().add("in-game-sidebar");
        this.boardGrid = new GridPane();
        this.boardGrid.getStyleClass().add("board-grid");

        this.boardSetup();
        this.sideBarSetup();
        this.spawnPieces();

        boardGame.addObserver(new BoardGameObserver() {
            public void onTurnChanged(Player player) {
            }

            // When a player moves. Also creates offset for pieces so they are not on top of each other.
            public void onPlayerMoved(Player player, int from, int to) {
                PlayerPieceView piece = pieces.get(player);
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

                LoggerToFile.log(Level.INFO,
                        "Player " + player + " moved from " + from + " to " + to,
                        getClass());
            }

            public void onGameEnded(Player winner) {
                WinScreenView winScreenView = new WinScreenView(winner);
                WinScreenController winScreenController = new WinScreenController(winScreenView);
                ViewManager.setRoot(winScreenView);
                LoggerToFile.log(Level.INFO, "Winning screen has been set", getClass());
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
                LoggerToFile.log(Level.INFO, "LadderGame have loaded all tiles", getClass());
            } catch (IndexOutOfBoundsException exception) {
                LoggerToFile.log(Level.SEVERE, exception.getMessage(), getClass());
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
                    lines.add(new Line(
                            v.getLayoutX(),
                            v.getLayoutY(),
                            tilePane.get(currentTile.landAction).getLayoutX(),
                            tilePane.get(currentTile.landAction).getLayoutY()
                    ));
                } else {
                    v.getStyleClass().add("negative-land-action");
                    tilePane.get(currentTile.landAction).getStyleClass().add("negative-land-target");
                }
            }
        });
        lines.forEach(line -> {
            line.getStyleClass().add("line");
            lineGroup.getChildren().add(line);
        });
        boardGrid.add(lineGroup, 0, 0, 1, 1);
        this.setCenter(boardGrid);
        LoggerToFile.log(Level.INFO, "Ladder board has successfully loaded", getClass());

        actionConnectorPane.setMouseTransparent(true);
        StackPane actionConnectorBoard = new StackPane(boardGrid, actionConnectorPane);
        setCenter(actionConnectorBoard);

        boardGrid.applyCss();
        boardGrid.layout();

        Platform.runLater(() -> {
            PauseTransition wait = new PauseTransition(Duration.millis(500));
            wait.setOnFinished(event -> addActionConnectors());
            wait.play();

            widthProperty().addListener((observable, oldValue, newValue) -> addActionConnectors());
            heightProperty().addListener((observable, oldValue, newValue) -> addActionConnectors());
        });
    }

    private void addActionConnectors() {

        actionConnectorPane.getChildren().clear();

        tilePane.forEach((number, from) -> {
            Tile tile = boardGame.getBoard().getTile(number);
            if (!tile.isLandAction()) {
                return;
            }

            StackPane to = tilePane.get(tile.getLandAction());
            if (to == null) {
                return;
            }

            Point2D fromPoint = from.localToScene(from.getWidth() / 2, from.getHeight() / 2);
            Point2D toPoint = to.localToScene(to.getWidth() / 2, to.getHeight() / 2);

            Point2D fromInConnectorPane = actionConnectorPane.sceneToLocal(fromPoint);
            Point2D toInConnectorPane = actionConnectorPane.sceneToLocal(toPoint);

            Line line = new Line(fromInConnectorPane.getX(), fromInConnectorPane.getY(), toInConnectorPane.getX(), toInConnectorPane.getY());
            line.getStyleClass().add(tile.getLandAction() > number ? "positive-land-action-connector" : "negative-land-action-connector");
            actionConnectorPane.getChildren().add(line);
        });
    }

    private void sideBarSetup() {
        sidebar.getChildren().clear();

        Label sidebarLabel = new Label("Game controls:");
        sidebarLabel.getStyleClass().add("sidebar-label");

        DiceView diceView = new DiceView(boardGame, 2);
        DiceController diceController = new DiceController(diceView);

        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("in-game-exit-button");

        sidebar.getChildren().addAll(sidebarLabel, diceView, exitButton);
        sidebar.setSpacing(15);
        sidebar.setAlignment(Pos.TOP_CENTER);

        this.setRight(sidebar);
        LoggerToFile.log(Level.INFO, "LadderGame sidebar has been loaded successfully", getClass());
    }

    private void spawnPieces() {
        ViewManager.players.forEach(player -> {
            PlayerPieceView piece = new PlayerPieceView(player);
            pieces.put(player, piece);

            // Spawns the player on the first tile
            tilePane.get(1).getChildren().add(piece);
            recalculatePiecePlacement(tilePane.get(1));
            boardGame.addPlayer(player);

            // Oppdatere spillernavn label i begynnelsen
            if (!boardGame.getPlayers().isEmpty()) {
                boardGame.notifyTurnChanged(boardGame.getPlayers().getFirst());
            }
        });
        boardGame.startGame();
        LoggerToFile.log(Level.INFO, "Game have started successfully", getClass());
    }

    /**
     * Recalculates the placement of pieces in the stack pane.
     * This is used to ensure that pieces are not overlapping.
     *
     * @param pane The StackPane containing the pieces that are getting recalculated.
     */
    private void recalculatePiecePlacement(StackPane pane) {
        List<PlayerPieceView> piecesInPane = pane.getChildren().stream()
                .filter(node -> node instanceof PlayerPieceView)
                .map(node -> (PlayerPieceView) node)
                .toList();
        int n = piecesInPane.size();
        for (int i = 0; i < n; i++) {
            double deltaX = (i - (n - 1) / 2.0) * offset;
            piecesInPane.get(i).setTranslateX(deltaX);
            piecesInPane.get(i).setTranslateY(0);
        }
        LoggerToFile.log(Level.INFO, "Recalculation of pieces position has been completed", getClass());
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
