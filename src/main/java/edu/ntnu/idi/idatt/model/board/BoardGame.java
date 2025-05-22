package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.model.dice.Dice;
import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.tile.Tile;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

import java.util.*;

public class BoardGame {
    private final Board board;
    private final List<Player> players = new ArrayList<>();
    public Dice dice;

    private Player currentPlayer;
    private Iterator<Player> iterator;

    private final List<BoardGameObserver> observers = new ArrayList<>();

    public BoardGame(Board board, int diceAmount) {
        this.board = Objects.requireNonNull(board, "Board can not be null!");
        dice = new Dice(diceAmount);
    }

    /**
     * Fikser javadoc snart :)
     *
     * @param observer
     */
    public void addObserver(BoardGameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BoardGameObserver observer) {
        observers.remove(observer);
    }

    public void notifyTurnChanged(Player player) {
        observers.forEach(observer -> observer.onTurnChanged(player));
    }

    public void notifyPlayerMoved(Player player, int from, int to) {
        observers.forEach(observer -> observer.onPlayerMoved(player, from, to));
    }

    private void notifyGameEnded(Player winner) {
        observers.forEach(observer -> observer.onGameEnded(winner));
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.placeOnTile(board.getTile(1)); // legge til på start tile
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    private void checkIfPlayers() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Game needs players to start!");
        }
    }

    private Player nextPlayer() {
        if (iterator == null || !iterator.hasNext()) {
            iterator = players.iterator();
        }
        return iterator.next();
    }

    private void pieceAnimation(Player player, List<Integer> whereToGo, Runnable done) {
        if (whereToGo.isEmpty()) {
            done.run();
        }
        SequentialTransition sequentialTransition = new SequentialTransition();

        for (int toTile : whereToGo) {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(100));
            pauseTransition.setOnFinished(event -> {
                int fromTile = player.getCurrentTile().getTileId();
                Tile goTo = board.getTile(toTile);
                player.move(goTo);
                notifyPlayerMoved(player, fromTile, toTile);
            });
            sequentialTransition.getChildren().add(pauseTransition);
        }
        sequentialTransition.setOnFinished(event -> done.run());
        sequentialTransition.play();
    }

    public void startGame() {
        if (players.isEmpty()) {
            throw new IllegalStateException("Game needs players");
        }
        iterator = players.iterator();
        currentPlayer = iterator.next();
        notifyTurnChanged(currentPlayer);
    }

    public void playTurn() {
        checkIfPlayers();
        currentPlayer.setMoveType(MoveType.PRIMARY_MOVE);

        int roll = dice.roll();
        int from = currentPlayer.getCurrentTile().getTileId();
        int targetBeforeActions = Math.min(from + roll, board.getMaxTileId());
        int combined = from + roll;
        int target;

        if (combined > board.getMaxTileId()) {
            combined = board.getMaxTileId();
        }

        if (board.getTile(combined).isLandAction()) {
            System.out.println("CHECK HAS ACTION");
            target = board.getTile(combined).getLandAction();
        } else {
            System.out.println("CHECK HAS NO ACTION");
            target = combined;
        }

        System.out.println(currentPlayer.getCurrentTile());
        System.out.println("Roll " + roll + " from " + from + " to " + target + " ( " + targetBeforeActions + " ) ");

        List<Integer> whereToGo = java.util.stream.IntStream.rangeClosed(from + 1, target).boxed().toList();

        pieceAnimation(currentPlayer, whereToGo, () -> {
            currentPlayer.placeOnTile(board.getTile(target));

            if (target == board.getMaxTileId()) {
                notifyGameEnded(currentPlayer);
            }
            // Klargjøre for neste spiller
            currentPlayer = nextPlayer();
            notifyTurnChanged(currentPlayer);
        });
    }

    /**
     * The check is perfectly fine as the first player to move has the advantage of starting first,
     * which is reflected in the check, where the players are moving according to their position.
     *
     * @return Player that have won or null
     */

    public Player getWinner() {
        int maxTileId = board.getMaxTileId();
        return currentPlayer.getCurrentTile().tileId >= maxTileId ? currentPlayer : null;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Dice getDice() {
        return dice;
    }
}
