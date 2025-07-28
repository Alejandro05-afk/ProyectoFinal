package Modelo;

/**
 * Representa una mentoría asignada a una idea y un mentor, con fecha y estado.
 */
public class Mentoria {
    private int id, ideaId, mentorId;
    private String fecha;  // Fecha en formato String
    private String estado;

    /**
     * Constructor vacío para crear una instancia sin inicializar atributos.
     */
    public Mentoria() {
    }

    /**
     * Constructor para inicializar todos los campos de una mentoría.
     * @param id ID de la mentoría.
     * @param ideaId ID de la idea de negocio.
     * @param mentorId ID del mentor asignado.
     * @param fecha Fecha de la mentoría.
     * @param estado Estado de la mentoría (por ejemplo: pendiente, realizada).
     */
    public Mentoria(int id, int ideaId, int mentorId, String fecha, String estado) {
        this.id = id;
        this.ideaId = ideaId;
        this.mentorId = mentorId;
        this.fecha = fecha;
        this.estado = estado;
    }

    /**
     * Obtiene el ID de la mentoría.
     * @return ID de la mentoría.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la mentoría.
     * @param id ID de la mentoría.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el ID de la idea relacionada.
     * @return ID de la idea.
     */
    public int getIdeaId() {
        return ideaId;
    }

    /**
     * Establece el ID de la idea relacionada.
     * @param ideaId ID de la idea.
     */
    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    /**
     * Obtiene el ID del mentor asignado.
     * @return ID del mentor.
     */
    public int getMentorId() {
        return mentorId;
    }

    /**
     * Establece el ID del mentor asignado.
     * @param mentorId ID del mentor.
     */
    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    /**
     * Obtiene la fecha de la mentoría.
     * @return Fecha de la mentoría.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la mentoría.
     * @param fecha Fecha de la mentoría.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el estado actual de la mentoría.
     * @return Estado de la mentoría.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual de la mentoría.
     * @param estado Estado de la mentoría.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
