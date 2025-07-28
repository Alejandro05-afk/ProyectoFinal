package Controlador;

import DAO.ObservacionDAO;
import Modelo.Observacion;

import java.sql.Date;
import java.util.List;

public class ObservacionController {

    // Agrega una observación a una mentoría
    public static void agregarObservacion(int mentoriaId, String comentario) {
        Observacion obs = new Observacion();
        obs.setMentoriaId(mentoriaId);
        obs.setComentario(comentario);
        ObservacionDAO.insertarObservacion(obs);
    }

    // Listar observaciones por mentoría
    public static List<Observacion> listarPorMentoria(int mentoriaId) {
        return ObservacionDAO.listarPorMentoria(mentoriaId);
    }

    // Listar observaciones por usuario (emprendedor)
    public static List<Observacion> listarPorUsuario(int usuarioId) {
        return ObservacionDAO.listarPorUsuario(usuarioId);
    }

    // Listar observaciones por fecha
    public static List<Observacion> listarPorFecha(Date fecha) {
        return ObservacionDAO.listarPorFecha(fecha);
    }
}
