package edu.ntnu.idi.idatt.view;

import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewManager {

  private static Stage primaryStage;
  private static Scene mainScene;

  public static void init(Stage stage) {
    primaryStage = stage;
    mainScene = new Scene(new javafx.scene.Group(),1000, 800);
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
