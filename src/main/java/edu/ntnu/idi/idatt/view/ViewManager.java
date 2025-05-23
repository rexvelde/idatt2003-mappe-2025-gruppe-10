package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.controller.menu.MainMenuController;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.player.Player;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import edu.ntnu.idi.idatt.view.menu.MainMenuView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The ViewManager class is responsible for managing the main view of the application.
 * It initializes the primary stage, sets the root view, and handles CSS and font loading.
 */
public class ViewManager extends Application {

  // Shared player list. Put here to avoid list from being reset when a view is refreshed.
  public static final List<Player> players = new ArrayList<>();

  private static Stage primaryStage;
  private static Scene mainScene;

  private static Media media;
  private static MediaPlayer mediaPlayer;

    /**
     * Initializes the ViewManager with the given stage.
     *
     * @param stage The primary stage for the application.
     */
  public static void init(Stage stage) {
    primaryStage = stage;
    mainScene = new Scene(new javafx.scene.Group(),1100, 850);
    applyCss();
    loadFonts();
    primaryStage.setScene(mainScene);
    primaryStage.show();

    URL resource = ViewManager.class.getResource("/soundtrack/8_bit_retro_forest.mp3");
    if (resource != null) {
      media = new Media(resource.toExternalForm());
      mediaPlayer = new MediaPlayer(media);
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      mediaPlayer.setVolume(0.1);
      mediaPlayer.play();
    }
  }

  private static void loadFonts() {
    Font.loadFont(Objects.requireNonNull(ViewManager.class.getResourceAsStream("/fonts/Kablammo-Regular-VariableFont_MORF.ttf")), 14);
  }

    /**
     * The primary stage of the application.
     */
  public static void setRoot(Parent root) {
    mainScene.setRoot(root);
  }

  private static void applyCss() {
    String css = Objects.requireNonNull(ViewManager.class.getResource("/style/style.css")).toExternalForm();
    mainScene.getStylesheets().add(css);
  }

    /**
     * Starting the primary stage.
     */
  @Override
  public void start(Stage primaryStage) {
    ViewManager.init(primaryStage);

    MainMenuView mainView = new MainMenuView();
    MainMenuController mainMenuController = new MainMenuController(mainView);
    ViewManager.setRoot(mainView);

    LoggerToFile.log(Level.INFO, "ViewManager have started the application", getClass());
  }

    /**
     * The main method to launch the application.
     *
     * @param args arguments.
     */
  public static void main(String[] args) {
    launch(args);
  }
}
