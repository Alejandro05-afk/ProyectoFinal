package Modelo;

/**
 * Representa una observación realizada durante una mentoría.
 */
public class Observacion {
    private int id;
    private int mentoriaId;
    private String comentario;

    /**
     * Constructor vacío para crear una observación sin inicializar atributos.
     */
    public Observacion() {
    }

    /**
     * Constructor para crear una observación sin ID (por ejemplo, al insertar una nueva).
     * @param mentoriaId ID de la mentoría asociada.
     * @param comentario Comentario de la observación.
     */
    public Observacion(int mentoriaId, String comentario) {
        this.mentoriaId = mentoriaId;
        this.comentario = comentario;
    }

    /**
     * Constructor completo para inicializar todos los campos de una observación.
     * @param id ID de la observación.
     * @param mentoriaId ID de la mentoría asociada.
     * @param comentario Comentario de la observación.
     */
    public Observacion(int id, int mentoriaId, String comentario) {
        this.id = id;
        this.mentoriaId = mentoriaId;
        this.comentario = comentario;
    }

    /**
     * Obtiene el ID de la observación.
     * @return ID de la observación.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la observación.
     * @param id ID de la observación.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el ID de la mentoría asociada.
     * @return ID de la mentoría.
     */
    public int getMentoriaId() {
        return mentoriaId;
    }

    /**
     * Establece el ID de la mentoría asociada.
     * @param mentoriaId ID de la mentoría.
     */
    public void setMentoriaId(int mentoriaId) {
        this.mentoriaId = mentoriaId;
    }

    /**
     * Obtiene el comentario de la observación.
     * @return Comentario de la observación.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Establece el comentario de la observación.
     * @param comentario Comentario de la observación.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
