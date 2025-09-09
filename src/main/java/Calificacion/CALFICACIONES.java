package Calificacion;

public class Calificacion {
    private String id;
    private String estudianteId;
    private String materia;
    private double nota;

    public Calificacion() {}

    public Calificacion(String id, String estudianteId, String materia, double nota) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.materia = materia;
        this.nota = nota;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEstudianteId() { return estudianteId; }
    public void setEstudianteId(String estudianteId) { this.estudianteId = estudianteId; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
}