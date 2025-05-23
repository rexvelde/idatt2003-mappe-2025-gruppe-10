package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.fileHandler.JsonBoardFileHandler;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;

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

        LoggerToFile.log(Level.INFO, "BoardGame loaded from stored JSON-file", getClass());

        return new BoardGame(board, diceAmount);
    }

    public BoardGame createBoardGameFromUploadedFile(File uploadedFile) throws InvalidBoardException {
        Board board = createBoardFromFile(uploadedFile.getPath());
        LoggerToFile.log(Level.INFO, "BoardGame loaded from uploaded JSON-file", getClass());
        return new BoardGame(board, 2);
    }
}