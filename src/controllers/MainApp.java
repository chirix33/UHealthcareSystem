package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(
          FXMLLoader.load(getClass().getResource("/resources/views/LoginView.fxml"))
        );
        scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
        primaryStage.setTitle("Universal Health Information System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
