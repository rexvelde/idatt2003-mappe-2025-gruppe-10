package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.model.fileHandler.CsvPlayerFileHandler;
import edu.ntnu.idi.idatt.model.dice.Dice;
import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.player.Player;

import java.util.*;

public class BoardGame {
    private final Board board;
    private final List<Player> players = new ArrayList<>();
    public Dice dice = new Dice(2);

    private Player currentPlayer;
    private Iterator<Player> iterator;

    private final List<CsvPlayerFileHandler.BoardGameObserver> observers = new ArrayList<>();

    public BoardGame() {
        this(new Board());
    }

    public BoardGame(Board board) {
        this.board = Objects.requireNonNull(board, "Board can not be null!");
    }

    /**
     * Fikser javadoc snart :)
     * @param observer
     */
    public void addObserver(CsvPlayerFileHandler.BoardGameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CsvPlayerFileHandler.BoardGameObserver observer) {
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

    public void playTurn() {
        checkIfPlayers();
        currentPlayer = nextPlayer();
        notifyTurnChanged(currentPlayer);
        currentPlayer.setMoveType(MoveType.PRIMARY_MOVE);

        int roll = dice.roll();
        int from = currentPlayer.getCurrentTile().getTileId();
        int targetBeforeActions = Math.min(from + roll, board.getMaxTileId());
        int target;
        if (currentPlayer.getCurrentTile().isLandAction()) {
            target = currentPlayer.getCurrentTile().landAction;
            System.out.println("CHECK HAS ACTION");
        } else {
            target = targetBeforeActions;
            System.out.println("CHECK HAS NO ACTION");
        }

        System.out.println(currentPlayer.getCurrentTile());
        System.out.println("Roll " + roll + " from " + from + " to " + target + " ( " + targetBeforeActions + " ) ");

        currentPlayer.placeOnTile(board.getTile(target));
        notifyPlayerMoved(currentPlayer, from, target);

        if (target == board.getMaxTileId()) {
            notifyGameEnded(currentPlayer);
        }
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
