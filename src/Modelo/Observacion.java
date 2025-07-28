package Modelo;

public class Observacion {
    private int id; // Suponiendo que la tabla tiene una PK llamada id
    private int mentoriaId;
    private String comentario;

    // Constructores
    public Observacion() {
    }

    public Observacion(int mentoriaId, String comentario) {
        this.mentoriaId = mentoriaId;
        this.comentario = comentario;
    }

    public Observacion(int id, int mentoriaId, String comentario) {
        this.id = id;
        this.mentoriaId = mentoriaId;
        this.comentario = comentario;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMentoriaId() {
        return mentoriaId;
    }

    public void setMentoriaId(int mentoriaId) {
        this.mentoriaId = mentoriaId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
