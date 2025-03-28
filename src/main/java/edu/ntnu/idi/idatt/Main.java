package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.view.MainMenuView;
import edu.ntnu.idi.idatt.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

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
