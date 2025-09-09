package com.programacion.application._251408equipo1prueba;

import com.programacion.inventario.util.NavigationManager;
import javafx.fxml.FXML;

public class MainController {
    @FXML
    private void handleStudentManagement() {
        NavigationManager.getInstance().navigateTo(NavigationManager.Screen.ESTUDIANTES);
    }

    @FXML
    private void handleGradeManagement() {
        NavigationManager.getInstance().navigateTo(NavigationManager.Screen.CALIFICACIONES);
    }

    @FXML
    private void handleLogout() {
        NavigationManager.getInstance().navigateTo(NavigationManager.Screen.LOGIN, true);
    }
}