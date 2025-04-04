package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewManager {

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
}
