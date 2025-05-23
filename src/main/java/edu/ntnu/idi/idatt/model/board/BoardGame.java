package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.dice.Dice;
import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.tile.Tile;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

import java.util.*;
import java.util.logging.Level;

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
     * Adds an observer to the list of observers.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(BoardGameObserver observer) {
        observers.add(observer);

        LoggerToFile.log(Level.INFO, "Observer " + observer.getClass() + " has been added", getClass());
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer The observer to be removed.
     */
    public void removeObserver(BoardGameObserver observer) {
        observers.remove(observer);
        LoggerToFile.log(Level.INFO, "Observer " + observer.getClass() + " has been removed", getClass());
    }

    /**
     * Notifies all observers that the turn has changed.
     *
     * @param player The player whose turn has changed.
     */
    public void notifyTurnChanged(Player player) {
        observers.forEach(observer -> observer.onTurnChanged(player));
        LoggerToFile.log(Level.INFO, "Player " + player.getName() + " switched turns", getClass());
    }

    /**
     * Notifies all observers that a player has moved.
     *
     * @param player The player who moved.
     * @param from   The tile ID the player moved from.
     * @param to     The tile ID the player moved to.
     */
    public void notifyPlayerMoved(Player player, int from, int to) {
        observers.forEach(observer -> observer.onPlayerMoved(player, from, to));
        LoggerToFile.log(Level.INFO, "Player " + player.getName() + " moved", getClass());
    }

    /**
     * Notifies all observers that the game has ended.
     *
     * @param winner The player who won the game.
     */
    private void notifyGameEnded(Player winner) {
        observers.forEach(observer -> observer.onGameEnded(winner));
        LoggerToFile.log(Level.INFO, "Game ended", getClass());
    }

    /**
     * Adds a player to the game.
     *
     * @param player The player to be added.
     */
    public void addPlayer(Player player) {
        players.add(player);
        player.placeOnTile(board.getTile(1));

        LoggerToFile.log(Level.INFO, "Player " + player.getName() + " has been added to the game", getClass());
    }

    /**
     * Returns an unmodifiable list of players in the game.
     *
     * @return An unmodifiable list of players.
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Checks if there are players in the game.
     * Logs a message if player list is empty.
     *
     * @return true if there are players, false otherwise.
     */
    private boolean checkForNoPlayers() {
        if (players.isEmpty()) {
            LoggerToFile.log(Level.INFO, "Game has no players!", getClass());
            return true;
        }
        return false;
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
        if (checkForNoPlayers()) {
            return;
        }
        iterator = players.iterator();
        currentPlayer = iterator.next();
        notifyTurnChanged(currentPlayer);
    }

    public void playTurn() {
        if (checkForNoPlayers()) {
            return;
        }
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
            target = board.getTile(combined).getLandAction();
        } else {
            target = combined;
        }

        LoggerToFile.log(Level.INFO, currentPlayer.getCurrentTile().toString(), getClass());
        LoggerToFile.log(Level.INFO,
                "Roll " + roll + " from " + from + " to " + target
                        + " ( " + targetBeforeActions + " ) ",
                getClass());

        List<Integer> whereToGo = java.util.stream.IntStream.rangeClosed(from + 1, combined).boxed().toList();

        pieceAnimation(currentPlayer, whereToGo, () -> {
            currentPlayer.placeOnTile(board.getTile(target));

        if (target == board.getMaxTileId()) {
            notifyGameEnded(currentPlayer);
        }

        // Readying for next player
        currentPlayer = nextPlayer();;
        });
        notifyTurnChanged(currentPlayer);
    }

    public Board getBoard() {
        return board;
    }

    public Dice getDice() {
        return dice;
    }
}
