package controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SceneLoader {
    /**
     * Load a new scene in the primary stage (replaces current UI).
     */
    public static void load(String fxmlPath, String title) {
    	try {
            Parent root = FXMLLoader.load(SceneLoader.class.getResource(fxmlPath));
            Stage stage = (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
            stage.setTitle(title);
            stage.getScene().setRoot(root);

            // ← Auto-resize the window to fit the new scene
            stage.sizeToScene();
            // ← Optional: re-center it on screen
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a modal (dialog) window.
     */
    public static void loadModal(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(SceneLoader.class.getResource(fxmlPath));
            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle(title);
            modal.setScene(new Scene(root));
            modal.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}