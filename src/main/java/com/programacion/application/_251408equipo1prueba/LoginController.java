package com.programacion.application._251408equipo1prueba;

import com.programacion.application._251408equipo1prueba.User;
import com.programacion.application._251408equipo1prueba.JsonFileManager;
import com.programacion.inventario.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.reflect.TypeToken;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static final String CREDENTIALS_PATH = "data/credenciales.json";

    @FXML
    private void initialize() {
        File file = new File(CREDENTIALS_PATH);
        if (!file.exists()) {
            createDefaultCredentials();
        }
    }

    private void createDefaultCredentials() {
        List<User> defaultUsers = List.of(
                new User("admin", "admin123", "administrador"),
                new User("profesor", "profesor123", "profesor"),
                new User("estudiante", "estudiante123", "estudiante")
        );
        JsonFileManager.saveToJson(defaultUsers, CREDENTIALS_PATH);
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", "Por favor, complete todos los campos.");
            return;
        }

        try {
            Type userListType = new TypeToken<List<User>>(){}.getType();
            List<User> users = JsonFileManager.loadFromJson(CREDENTIALS_PATH, userListType);

            if (users != null) {
                for (User user : users) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        NavigationManager.getInstance().navigateTo(NavigationManager.Screen.MAIN);
                        return;
                    }
                }
            }

            showAlert(Alert.AlertType.ERROR, "Error de autenticación", "Credenciales incorrectas.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error al leer el archivo de credenciales.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}