package com.programacion.application._251408equipo1prueba;

import com.programacion.inventario.util.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        NavigationManager navigationManager = NavigationManager.getInstance();
        navigationManager.initialize(stage);
        navigationManager.navigateTo(NavigationManager.Screen.LOGIN);
    }

    public static void main(String[] args) {
        launch();
    }
}