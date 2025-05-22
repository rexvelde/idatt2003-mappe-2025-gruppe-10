package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.model.fileHandler.JsonBoardFileHandler;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class BoardGameFactory {
    private final ArrayList<String> paths;
    private final JsonBoardFileHandler boardFileHandler = new JsonBoardFileHandler();

    public BoardGameFactory() {
        this.paths = new ArrayList<>();
        this.paths.add("/boards/slipperyslope.json");
        this.paths.add("/boards/snakepit.json");
        this.paths.add("/boards/goosegame.json");
    }

    /**
     * Reads JSON from file and builds a Board object.
     *
     * @param filePath Path to file containing board definition.
     *                 The file should be in JSON format.
     * @return Board object with fields from file.
     */
    public Board createBoardFromFile(String filePath) throws InvalidBoardException {
        return boardFileHandler.readBoardFromJsonFile(filePath);
    }

    /**
     * Creates board game from file.
     * Initiates board, dice and empty player list.
     *
     * @param boardId Integer determining file containing board definition.
     *                The file should be in JSON format.
     * @return BoardGame object with board from file.
     */
    public BoardGame createBoardGameFromFile(int boardId, int diceAmount) throws InvalidBoardException {
        Board board = createBoardFromFile(paths.get(boardId));

        return new BoardGame(board, diceAmount);
    }
}