package Modelo;

import java.sql.Timestamp;

public class Mentoria {
    private int id, ideaId, mentorId;
    private String fecha, estado;

    public Mentoria(int id, int ideaId, int mentorId, String fecha, String estado) {
        this.id = id;
        this.ideaId = ideaId;
        this.mentorId = mentorId;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}


