package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonBoardFileHandler {

  /**
   * Reads a board from a JSON file.
   *
   * @param filePath Path to the JSON file.
   * @return Board object with tiles from file.
   * @throws InvalidBoardException if the file is invalid or unreadable.
   */
  public Board readBoardFromJsonFile(Path filePath) throws InvalidBoardException {
    try (Reader reader = Files.newBufferedReader(filePath)) {
      Gson gson = new Gson();
      JsonObject json = gson.fromJson(reader, JsonObject.class);
      Board board = new Board();
      board.tiles.clear();
      JsonArray tilesArray = json.getAsJsonArray("tiles");
      for (JsonElement tileElement : tilesArray) {
        JsonObject tileObj = tileElement.getAsJsonObject();
        int id = tileObj.get("id").getAsInt();
        Tile tile = new Tile(id);
        if (tileObj.has("nextTile") && !tileObj.get("nextTile").isJsonNull()) {
          int nextTileId = tileObj.get("nextTile").getAsInt();
          tile.nextTile = board.getTile(nextTileId);
        }
        if (tileObj.has("action") && !tileObj.get("action").isJsonNull()) {
          JsonObject actionObj = tileObj.getAsJsonObject("action");
          String type = actionObj.get("type").getAsString();
          // TODO: Implement specific TileAction if necessary
        }
        board.addTile(tile);
      }
      return board;
    } catch (IOException | JsonParseException e) {
      throw new InvalidBoardException("Error reading board from JSON file: " + filePath, e);
    }
  }

  /**
   * Writes given board to a JSON file.
   *
   * @param board Board object to write to file.
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
      if (tile.nextTile != null) {
        tileObj.addProperty("nextTile", tile.nextTile.tileId);
      }
      if (tile.landAction != null) {
        JsonObject actionObj = new JsonObject();
        actionObj.addProperty("type", tile.landAction.getClass().getSimpleName());
        tileObj.add("action", actionObj);
      }
      tilesArray.add(tileObj);
    }
    return tilesArray;
  }
}