package Modelo;

public class EstadisticaIdea {
    private int id;
    private int mentoriaId;
    private int avanceFaseId;

    // Constructor
    public EstadisticaIdea(int id, int mentoriaId, int avanceFaseId) {
        this.id = id;
        this.mentoriaId = mentoriaId;
        this.avanceFaseId = avanceFaseId;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public int getMentoriaId() {
        return mentoriaId;
    }

    public int getAvanceFaseId() {
        return avanceFaseId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMentoriaId(int mentoriaId) {
        this.mentoriaId = mentoriaId;
    }

    public void setAvanceFaseId(int avanceFaseId) {
        this.avanceFaseId = avanceFaseId;
    }
}
