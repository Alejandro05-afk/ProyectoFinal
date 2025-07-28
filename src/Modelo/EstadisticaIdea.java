package Modelo;

/**
 * Representa una estadística asociada a una idea de negocio,
 * vinculada a una mentoría y un avance de fase.
 */
public class EstadisticaIdea {
    private int id;
    private int mentoriaId;
    private int avanceFaseId;

    /**
     * Constructor para crear una Estadística de Idea.
     * @param id identificador único de la estadística.
     * @param mentoriaId identificador de la mentoría relacionada.
     * @param avanceFaseId identificador del avance de fase asociado.
     */
    public EstadisticaIdea(int id, int mentoriaId, int avanceFaseId) {
        this.id = id;
        this.mentoriaId = mentoriaId;
        this.avanceFaseId = avanceFaseId;
    }

    /**
     * Obtiene el ID de la estadística.
     * @return ID de la estadística.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el ID de la mentoría asociada.
     * @return ID de la mentoría.
     */
    public int getMentoriaId() {
        return mentoriaId;
    }

    /**
     * Obtiene el ID del avance de fase asociado.
     * @return ID del avance de fase.
     */
    public int getAvanceFaseId() {
        return avanceFaseId;
    }

    /**
     * Establece el ID de la estadística.
     * @param id nuevo ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el ID de la mentoría asociada.
     * @param mentoriaId nuevo ID de mentoría.
     */
    public void setMentoriaId(int mentoriaId) {
        this.mentoriaId = mentoriaId;
    }

    /**
     * Establece el ID del avance de fase asociado.
     * @param avanceFaseId nuevo ID de avance de fase.
     */
    public void setAvanceFaseId(int avanceFaseId) {
        this.avanceFaseId = avanceFaseId;
    }
}
