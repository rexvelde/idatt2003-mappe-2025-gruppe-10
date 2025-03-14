package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardGame {
    public Board board;
    public Player currentPlayer;
    public List<Player> players;
    public Dice dice;

    /**
     * Default constructor for BoardGame
     */
    public BoardGame() {
        this.players = new ArrayList<>();
        this.createBoard();
        this.createDice();
    }

    /**
     * Constructor for loading board from JSON file.
     * @param board The board to be used in the game.
     */
    public BoardGame(Board board) {
        this.board = board;
        this.players = new ArrayList<>();
        this.createDice();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void createBoard() {
        this.board = new Board();
    }

    public void createDice() {
        dice = new Dice(2);
    }

    public void play() {
        addPlayerCLI();
        gameLoopCLI();
    }

    public void addPlayerCLI() {
        Scanner scanner = new Scanner(System.in);
        boolean addMorePlayers = true;

        while (addMorePlayers) {
            System.out.println("Enter player name: ");
            String playerName = scanner.nextLine();
            addPlayer(new Player(playerName, board.getTile(0)));

            System.out.println("Add more players [y/n]: ");
            addMorePlayers = scanner.nextLine().equalsIgnoreCase("y");
        }
        scanner.close();
    }

    public void gameLoopCLI() {
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        int maxTileId = board.getMaxTileId();
        while (!done) {
            System.out.println("Play next round [press enter to continue]: ");
            for (Player player : players) {
                currentPlayer = player;
                dice = new Dice(2);
                int roll = dice.roll();
                if (currentPlayer.getCurrentTile().tileId + roll >= maxTileId) {
                    currentPlayer.placeOnTile(board.getTile(maxTileId));
                } else {
                    currentPlayer.placeOnTile(board.getTile(currentPlayer.getCurrentTile().tileId+roll));
                }
                System.out.println(currentPlayer.getName() + " landed on tile " + currentPlayer.getCurrentTile().tileId);

                done = getWinner()!=null;
            }
        }
        System.out.println("Winner is " + currentPlayer.getName() + "!");
        scanner.close();
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
}
