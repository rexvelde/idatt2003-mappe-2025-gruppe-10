package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.dice.Dice;
import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.player.Player;

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
     * Fikser javadoc snart :)
     * @param observer
     */
    public void addObserver(BoardGameObserver observer) {
        observers.add(observer);

        LoggerToFile.log(Level.INFO, "Observer " + observer.getClass() + " has been added", getClass());
    }

    public void removeObserver(BoardGameObserver observer) {
        observers.remove(observer);
        LoggerToFile.log(Level.INFO, "Observer " + observer.getClass() + " has been removed", getClass());
    }

    public void notifyTurnChanged(Player player) {
        observers.forEach(observer -> observer.onTurnChanged(player));
        LoggerToFile.log(Level.INFO, "Player " + player.getName() + " switched turns", getClass());
    }

    public void notifyPlayerMoved(Player player, int from, int to) {
        observers.forEach(observer -> observer.onPlayerMoved(player, from, to));
        LoggerToFile.log(Level.INFO, "Player " + player.getName() + " moved", getClass());
    }

    private void notifyGameEnded(Player winner) {
        observers.forEach(observer -> observer.onGameEnded(winner));
        LoggerToFile.log(Level.INFO, "Game ended", getClass());
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.placeOnTile(board.getTile(1));

        LoggerToFile.log(Level.INFO, "Player " + player.getName() + " has been added to the game", getClass());
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    private boolean checkIfPlayers() {
        if (players.isEmpty()) {
            LoggerToFile.log(Level.INFO, "Game has no players!", getClass());
            return false;
        }
        return true;
    }

    private Player nextPlayer() {
        if (iterator == null || !iterator.hasNext()) {
            iterator = players.iterator();
        }
        return iterator.next();
    }

    public void startGame() {
        if (!checkIfPlayers()) {
            return;
        }
        iterator = players.iterator();
        currentPlayer = iterator.next();
        notifyTurnChanged(currentPlayer);
    }

    public void playTurn() {
        if (!checkIfPlayers()) {
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

        currentPlayer.placeOnTile(board.getTile(target));
        notifyPlayerMoved(currentPlayer, from, target);

        if (target == board.getMaxTileId()) {
            notifyGameEnded(currentPlayer);
        }

        // Readying for next player
        currentPlayer = nextPlayer();
        notifyTurnChanged(currentPlayer);
    }

    public Board getBoard() {
        return board;
    }

    public Dice getDice() {
        return dice;
    }
}
