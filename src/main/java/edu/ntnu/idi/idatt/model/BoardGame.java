package edu.ntnu.idi.idatt.model;

import java.util.List;

public class BoardGame {
    public Board board;
    public Player currentPlayer;
    public List<Player> players;
    public Dice dice;

    public BoardGame(Board board, Player currentPlayer, List<Player> players) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void createBoard() {
        this.board = new Board();
    }

    public void createDice() {
        dice = new Dice();
    }

    public void play() {
        // TODO: play-method
    }

    public Player getWinner() {
        // TODO: getWinner-method
        return null;
    }
}
