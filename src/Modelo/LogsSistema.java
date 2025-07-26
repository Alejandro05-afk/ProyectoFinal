package Modelo;

import java.sql.Timestamp;

public class LogsSistema {
    private int id;
    private int usuarioId;
    private String accion;
    private Timestamp fecha;

    public LogsSistema(int id, int usuarioId, String accion, Timestamp fecha) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.accion = accion;
        this.fecha = fecha;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
