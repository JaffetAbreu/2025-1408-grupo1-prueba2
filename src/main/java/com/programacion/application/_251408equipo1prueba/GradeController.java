package com.programacion.application._251408equipo1prueba;

import com.programacion.application._251408equipo1prueba.Grade;
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

public class GradeController {
    @FXML private TableView<Grade> gradesTable;
    @FXML private TableColumn<Grade, String> idColumn;
    @FXML private TableColumn<Grade, String> studentIdColumn;
    @FXML private TableColumn<Grade, String> subjectColumn;
    @FXML private TableColumn<Grade, Double> gradeColumn;
    @FXML private TableColumn<Grade, String> semesterColumn;

    @FXML private TextField idField;
    @FXML private ComboBox<String> studentComboBox;
    @FXML private TextField subjectField;
    @FXML private TextField gradeField;
    @FXML private TextField semesterField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;

    private ObservableList<Grade> gradesList = FXCollections.observableArrayList();
    private ObservableList<String> studentsIdList = FXCollections.observableArrayList();
    private static final String GRADES_PATH = "data/calificaciones.json";
    private static final String STUDENTS_PATH = "data/estudiantes.json";

    @FXML
    private void initialize() {
        loadStudents();
        setupTable();
        loadGrades();
        disableForm(true);

        gradesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectGrade(newValue));
    }

    private void loadStudents() {
        Type studentListType = new TypeToken<List<Student>>(){}.getType();
        List<Student> students = JsonFileManager.loadFromJson(STUDENTS_PATH, studentListType);

        if (students != null) {
            studentsIdList.clear();
            for (Student student : students) {
                studentsIdList.add(student.getId() + " - " + student.getName() + " " + student.getLastName());
            }
            studentComboBox.setItems(studentsIdList);
        }
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));

        gradesTable.setItems(gradesList);
    }

    private void loadGrades() {
        Type gradeListType = new TypeToken<List<Grade>>(){}.getType();
        List<Grade> grades = JsonFileManager.loadFromJson(GRADES_PATH, gradeListType);

        if (grades != null) {
            gradesList.clear();
            gradesList.addAll(grades);
        }
    }

    private void saveGrades() {
        JsonFileManager.saveToJson(gradesList, GRADES_PATH);
    }

    @FXML
    private void selectGrade(Grade grade) {
        if (grade != null) {
            idField.setText(grade.getId());

            for (int i = 0; i < studentsIdList.size(); i++) {
                if (studentsIdList.get(i).startsWith(grade.getStudentId())) {
                    studentComboBox.getSelectionModel().select(i);
                    break;
                }
            }

            subjectField.setText(grade.getSubject());
            gradeField.setText(String.valueOf(grade.getGrade()));
            semesterField.setText(grade.getSemester());
            disableForm(false);
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
            addButton.setDisable(true);
        }
    }

    @FXML
    private void handleAddGrade() {
        Grade grade = createGradeFromForm();
        if (grade != null) {
            gradesList.add(grade);
            saveGrades();
            clearForm();
        }
    }

    @FXML
    private void handleUpdateGrade() {
        Grade selectedGrade = gradesTable.getSelectionModel().getSelectedItem();
        if (selectedGrade != null) {
            Grade updatedGrade = createGradeFromForm();
            if (updatedGrade != null) {
                int selectedIndex = gradesTable.getSelectionModel().getSelectedIndex();
                gradesList.set(selectedIndex, updatedGrade);
                saveGrades();
                clearForm();
            }
        }
    }

    @FXML
    private void handleDeleteGrade() {
        Grade selectedGrade = gradesTable.getSelectionModel().getSelectedItem();
        if (selectedGrade != null) {
            gradesList.remove(selectedGrade);
            saveGrades();
            clearForm();
        }
    }

    @FXML
    private void handleNewGrade() {
        clearForm();
        disableForm(false);
        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        gradesTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() {
        NavigationManager.getInstance().navigateTo(NavigationManager.Screen.MAIN);
    }

    private Grade createGradeFromForm() {
        String id = idField.getText();
        String studentSelection = studentComboBox.getValue();
        String subject = subjectField.getText();
        String gradeText = gradeField.getText();
        String semester = semesterField.getText();

        if (id.isEmpty() || studentSelection == null || subject.isEmpty() || gradeText.isEmpty() || semester.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", "Todos los campos son obligatorios.");
            return null;
        }

        try {
            double gradeValue = Double.parseDouble(gradeText);
            if (gradeValue < 0 || gradeValue > 10) {
                showAlert(Alert.AlertType.ERROR, "Error de validación", "La calificación debe estar entre 0 y 10.");
                return null;
            }

            String studentId = studentSelection.split(" - ")[0];

            return new Grade(id, studentId, subject, gradeValue, semester);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", "La calificación debe ser un número válido.");
            return null;
        }
    }

    private void clearForm() {
        idField.clear();
        studentComboBox.getSelectionModel().clearSelection();
        subjectField.clear();
        gradeField.clear();
        semesterField.clear();
        disableForm(true);
        gradesTable.getSelectionModel().clearSelection();
        addButton.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void disableForm(boolean disabled) {
        idField.setDisable(disabled);
        studentComboBox.setDisable(disabled);
        subjectField.setDisable(disabled);
        gradeField.setDisable(disabled);
        semesterField.setDisable(disabled);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}