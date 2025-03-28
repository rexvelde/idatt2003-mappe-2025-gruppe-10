package edu.ntnu.idi.idatt.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenuView extends BorderPane {
  public MainMenuView() {
    Label title = new Label("Ladder Game");
    title.getStyleClass().add("title-label");

    Button play = new Button("Play");
    play.getStyleClass().add("play-button");

    Button editPlayers = new Button("Edit Players");
    editPlayers.getStyleClass().add("edit-players-button");

    VBox menuBox = new VBox(15, title, play, editPlayers);
    menuBox.setAlignment(Pos.CENTER);

    this.setCenter(menuBox);
  }
}
