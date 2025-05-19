package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewManager extends Application {

  // Shared player list. Put here to avoid list from being reset when a view is refreshed.
  public static final List<Player> players = new ArrayList<>();

  private static Stage primaryStage;
  private static Scene mainScene;

  public static void init(Stage stage) {
    primaryStage = stage;
    mainScene = new Scene(new javafx.scene.Group(),1100, 850);
    applyCss();
    loadFonts();
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  private static void loadFonts() {
    Font.loadFont(Objects.requireNonNull(ViewManager.class.getResourceAsStream("/fonts/Kablammo-Regular-VariableFont_MORF.ttf")), 14);
  }

  public static void setRoot(Parent root) {
    mainScene.setRoot(root);
  }

  private static void applyCss() {
    String css = Objects.requireNonNull(ViewManager.class.getResource("/style/style.css")).toExternalForm();
    mainScene.getStylesheets().add(css);
  }

  @Override
  public void start(Stage primaryStage) {
    ViewManager.init(primaryStage);

    MainMenuView mainView = new MainMenuView();
    ViewManager.setRoot(mainView);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
