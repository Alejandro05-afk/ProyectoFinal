package Modelo;

public class Mentoria {
    private int id, ideaId, mentorId;
    private String fecha;  // Si quieres mantener fecha como String
    private String estado;

    // Constructor vacío
    public Mentoria() {
    }

    // Constructor con parámetros
    public Mentoria(int id, int ideaId, int mentorId, String fecha, String estado) {
        this.id = id;
        this.ideaId = ideaId;
        this.mentorId = mentorId;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
