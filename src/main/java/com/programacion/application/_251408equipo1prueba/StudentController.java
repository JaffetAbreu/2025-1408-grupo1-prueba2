package com.programacion.application._251408equipo1prueba;

import com.programacion.application._251408equipo1prueba.Student;
import com.programacion.application._251408equipo1prueba.JsonFileManager;
import com.programacion.inventario.util.NavigationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.reflect.TypeToken;

public class StudentController {
    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, String> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, String> emailColumn;
    @FXML private TableColumn<Student, String> phoneColumn;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;

    private ObservableList<Student> studentsList = FXCollections.observableArrayList();
    private static final String STUDENTS_PATH = "data/estudiantes.json";

    @FXML
    private void initialize() {
        setupTable();
        loadStudents();
        disableForm(true);

        studentsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectStudent(newValue));
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        studentsTable.setItems(studentsList);
    }

    private void loadStudents() {
        Type studentListType = new TypeToken<List<Student>>(){}.getType();
        List<Student> students = JsonFileManager.loadFromJson(STUDENTS_PATH, studentListType);

        if (students != null) {
            studentsList.clear();
            studentsList.addAll(students);
        }
    }

    private void saveStudents() {
        JsonFileManager.saveToJson(studentsList, STUDENTS_PATH);
    }

    @FXML
    private void selectStudent(Student student) {
        if (student != null) {
            idField.setText(student.getId());
            nameField.setText(student.getName());
            lastNameField.setText(student.getLastName());
            emailField.setText(student.getEmail());
            phoneField.setText(student.getPhone());
            disableForm(false);
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
            addButton.setDisable(true);
        }
    }

    @FXML
    private void handleAddStudent() {
        Student student = createStudentFromForm();
        if (student != null) {
            studentsList.add(student);
            saveStudents();
            clearForm();
        }
    }

    @FXML
    private void handleUpdateStudent() {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Student updatedStudent = createStudentFromForm();
            if (updatedStudent != null) {
                int selectedIndex = studentsTable.getSelectionModel().getSelectedIndex();
                studentsList.set(selectedIndex, updatedStudent);
                saveStudents();
                clearForm();
            }
        }
    }

    @FXML
    private void handleDeleteStudent() {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            studentsList.remove(selectedStudent);
            saveStudents();
            clearForm();
        }
    }

    @FXML
    private void handleNewStudent() {
        clearForm();
        disableForm(false);
        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        studentsTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() {
        NavigationManager.getInstance().navigateTo(NavigationManager.Screen.MAIN);
    }

    private Student createStudentFromForm() {
        String id = idField.getText();
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (id.isEmpty() || name.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", "Todos los campos son obligatorios.");
            return null;
        }

        return new Student(id, name, lastName, email, phone);
    }

    private void clearForm() {
        idField.clear();
        nameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        disableForm(true);
        studentsTable.getSelectionModel().clearSelection();
        addButton.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void disableForm(boolean disabled) {
        idField.setDisable(disabled);
        nameField.setDisable(disabled);
        lastNameField.setDisable(disabled);
        emailField.setDisable(disabled);
        phoneField.setDisable(disabled);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}