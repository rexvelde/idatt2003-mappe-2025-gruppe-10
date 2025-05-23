package edu.ntnu.idi.idatt.model.fileHandler;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.ntnu.idi.idatt.Main;
import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.board.Board;
import edu.ntnu.idi.idatt.model.tile.Tile;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class JsonBoardFileHandler {

  /**
   * Reads a board from a JSON file.
   * ChatGPT have been used to troubleshoot how to add "action" in the Tile element.
   *
   * @param filePath Path to the JSON file.
   * @return Board object with tiles from file.
   * @throws InvalidBoardException if the file is invalid or unreadable.
   */
  public Board readBoardFromJsonFile(String filePath) throws InvalidBoardException {
    Board board;
    boolean alteredBoard = false;

    try {
      Reader reader;
      InputStream inputStream = Main.class.getResourceAsStream(filePath);
      if (inputStream != null) {
        reader = new InputStreamReader(inputStream);
      } else {
        reader = Files.newBufferedReader(Path.of(filePath));
      }

      JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
      JsonArray tileArray = jsonObject.getAsJsonArray("tiles");

      Type tileListType = new TypeToken<List<Tile>>() {}.getType();
      Gson gson = new Gson();

      List<Tile> tileList = new ArrayList<>();
      for (JsonElement element : tileArray) {
        Tile tile = gson.fromJson(element, Tile.class);
        if (element.getAsJsonObject().has("action")) {
          JsonElement actionEl = element.getAsJsonObject().get("action");
          if (actionEl.isJsonPrimitive() && actionEl.getAsJsonPrimitive().isNumber()) {
            int action = actionEl.getAsInt();
            tile.setLandAction(action);
          }
        }

        tileList.add(tile);
      }
      // List<Tile> tileList = gson.fromJson(tileArray, tileListType);

      board = new Board(tileList);
      alteredBoard = true;
      reader.close();
      LoggerToFile.log(Level.INFO, "Loaded JSON file correct", getClass());

    } catch (JsonParseException e) {
      LoggerToFile.log(Level.WARNING, "JSON file " + filePath + " could not be parsed", getClass());
      throw new InvalidBoardException(e.getMessage());
    } catch (IOException e) {
      LoggerToFile.log(Level.WARNING, "IOException while reading JSON file " + filePath, getClass());
      throw new RuntimeException(e);
    } finally {
      if (!alteredBoard) {
        // Defaults to a board with 90 tiles
        board = new Board(90);
        LoggerToFile.log(Level.INFO,
                "Because no successfully loaded JSON-files, a standard board has been loaded",
                getClass());
      }
    }

    return board;
  }

  /**
   * Writes given board to a JSON file.
   *
   * @param board    Board object to write to file.
   * @param filePath Path to the JSON file.
   * @throws InvalidBoardException if the file is invalid or not writable.
   */
  public void writeBoardToJsonFile(Board board, Path filePath) throws InvalidBoardException {
    try (Writer writer = Files.newBufferedWriter(filePath)) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      JsonObject json = new JsonObject();
      JsonArray tilesArray = getJsonElements(board);
      json.add("tiles", tilesArray);
      gson.toJson(json, writer);
    } catch (IOException e) {
      LoggerToFile.log(Level.WARNING, "Error writing board to JSON file" + filePath, getClass());
      throw new InvalidBoardException("Error writing board to JSON file: " + filePath, e);
    }
  }

  /**
   * Converts the tiles in a board to a JSON array.
   *
   * @param board Board object to convert.
   * @return JSON array with tiles.
   */
  private static JsonArray getJsonElements(Board board) {
    JsonArray tilesArray = new JsonArray();
    for (Tile tile : board.tiles.values()) {
      JsonObject tileObj = new JsonObject();
      tileObj.addProperty("id", tile.tileId);
      tileObj.addProperty("nextTile", tile.nextTileId);
      JsonObject actionObj = new JsonObject();
      actionObj.addProperty("type", tile.landAction);
      tileObj.add("action", actionObj);
      tilesArray.add(tileObj);
    }
    LoggerToFile.log(Level.INFO, "All tiles converted to JSON file", JsonBoardFileHandler.class);
    return tilesArray;
  }
}