package com.programacion.application._251408equipo1prueba;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenNavigator {
    private static Stage mainStage;

    // Inicializar con el Stage principal
    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    // Método para cambiar de pantalla
    public static void loadScreen(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenNavigator.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainStage.setTitle(title);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

