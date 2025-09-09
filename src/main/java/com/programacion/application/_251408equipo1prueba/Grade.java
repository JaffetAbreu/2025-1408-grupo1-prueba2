package com.programacion.application._251408equipo1prueba;

public class Grade {
    private String id;
    private String studentId;
    private String subject;
    private double grade;
    private String semester;

    public Grade() {}

    public Grade(String id, String studentId, String subject, double grade, String semester) {
        this.id = id;
        this.studentId = studentId;
        this.subject = subject;
        this.grade = grade;
        this.semester = semester;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}