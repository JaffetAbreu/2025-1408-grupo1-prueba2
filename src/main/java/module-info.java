module com.programacion.application._251408equipo1prueba {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    opens com.programacion.inventario.util to com.google.gson;

    opens com.programacion.application._251408equipo1prueba to javafx.fxml;
    exports com.programacion.application._251408equipo1prueba;
}