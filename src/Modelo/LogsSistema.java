package Modelo;

import java.sql.Timestamp;

/**
 * Representa un registro de acción dentro del sistema (log), relacionado a un usuario.
 */
public class LogsSistema {
    private int id;
    private int usuarioId;
    private String accion;
    private Timestamp fecha;

    /**
     * Constructor para inicializar un log del sistema.
     * @param id ID del log.
     * @param usuarioId ID del usuario que realizó la acción.
     * @param accion Descripción de la acción realizada.
     * @param fecha Fecha y hora en que se realizó la acción.
     */
    public LogsSistema(int id, int usuarioId, String accion, Timestamp fecha) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.accion = accion;
        this.fecha = fecha;
    }

    /**
     * Obtiene la fecha del log.
     * @return fecha y hora de la acción.
     */
    public Timestamp getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del log.
     * @param fecha nueva fecha del log.
     */
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la acción registrada.
     * @return descripción de la acción.
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Establece una nueva acción.
     * @param accion descripción de la acción.
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * Obtiene el ID del usuario que generó el log.
     * @return ID del usuario.
     */
    public int getUsuarioId() {
        return usuarioId;
    }

    /**
     * Establece el ID del usuario que generó el log.
     * @param usuarioId ID del usuario.
     */
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Obtiene el ID del log.
     * @return ID del registro.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del log.
     * @param id nuevo ID del registro.
     */
    public void setId(int id) {
        this.id = id;
    }
}
