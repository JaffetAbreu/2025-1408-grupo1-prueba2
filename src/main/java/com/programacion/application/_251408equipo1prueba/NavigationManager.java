package com.programacion.inventario.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NavigationManager {
    private static NavigationManager instance;
    private Stage primaryStage;
    private Map<String, Object> controllerCache;

    private static final String VIEWS_PATH = "/com/programacion/inventario/view/";

    public enum Screen {
        LOGIN("login-view.fxml", "Login"),
        MAIN("main-view.fxml", "Sistema Principal"),
        ESTUDIANTES("estudiantes-view.fxml", "Gestión de Estudiantes"),
        CALIFICACIONES("calificaciones-view.fxml", "Gestión de Calificaciones");

        private final String fxmlFile;
        private final String title;

        Screen(String fxmlFile, String title) {
            this.fxmlFile = fxmlFile;
            this.title = title;
        }

        public String getFxmlFile() { return fxmlFile; }
        public String getTitle() { return title; }
    }

    private NavigationManager() {
        controllerCache = new HashMap<>();
    }

    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void navigateTo(Screen screen, boolean clearCache) {
        if (clearCache) {
            controllerCache.clear();
        }
        navigateTo(screen);
    }

    public void navigateTo(Screen screen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + screen.getFxmlFile()));
            Parent root = loader.load();

            Object controller = loader.getController();
            controllerCache.put(screen.name(), controller);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(screen.getTitle());
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla: " + screen.name());
            e.printStackTrace();
        }
    }

    public void navigateTo(Screen screen, Map<String, Object> parameters) {
        navigateTo(screen);

        Object controller = controllerCache.get(screen.name());
        if (controller instanceof ParameterReceiver) {
            ((ParameterReceiver) controller).receiveParameters(parameters);
        }
    }

    public Object getController(Screen screen) {
        return controllerCache.get(screen.name());
    }

    public void clearCache() {
        controllerCache.clear();
    }

    public interface ParameterReceiver {
        void receiveParameters(Map<String, Object> parameters);
    }
}