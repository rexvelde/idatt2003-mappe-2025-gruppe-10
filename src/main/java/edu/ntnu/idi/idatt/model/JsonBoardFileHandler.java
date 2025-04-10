package edu.ntnu.idi.idatt.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import edu.ntnu.idi.idatt.Main;
import edu.ntnu.idi.idatt.exception.InvalidBoardException;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonBoardFileHandler {

  /**
   * Reads a board from a JSON file.
   *
   * @param filePath Path to the JSON file.
   * @return Board object with tiles from file.
   * @throws InvalidBoardException if the file is invalid or unreadable.
   */
  public Board readBoardFromJsonFile(String filePath) throws InvalidBoardException {
    Board board;
    boolean alteredBoard = false;

    try {
      InputStream inputStream = Main.class.getResourceAsStream(filePath);
      assert inputStream != null;
      InputStreamReader reader = new InputStreamReader(inputStream);

      JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
      JsonArray tileArray = jsonObject.getAsJsonArray("tiles");

      System.out.println(tileArray);
      Type tileListType = new TypeToken<List<Tile>>() {}.getType();
      Gson gson = new Gson();

      List<Tile> tileList = new ArrayList<>();
      for (JsonElement element : tileArray) {
        Tile tile = gson.fromJson(element, Tile.class);
        tileList.add(tile);
      }
      // List<Tile> tileList = gson.fromJson(tileArray, tileListType);

      board = new Board(tileList);
      alteredBoard = true;

      for (JsonElement element : tileArray) {
        JsonObject tileObj = element.getAsJsonObject();
        if (tileObj.has("tileId")) {
          System.out.println("JSON tileId: " + tileObj.get("tileId").getAsString());
        } else {
          System.out.println("JSON has no tileId field");
        }
      }
      reader.close();
    } catch (JsonParseException e) {
      throw new InvalidBoardException(e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (!alteredBoard) {
        board = new Board();
      }
    }

    System.out.println(board.tiles.size()+ " tile(-s) loaded");
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
      tileObj.addProperty("nextTile", tile.nextTile);
      JsonObject actionObj = new JsonObject();
      actionObj.addProperty("type", tile.landAction);
      tileObj.add("action", actionObj);
      tilesArray.add(tileObj);
    }
    return tilesArray;
  }
}