package Controlador;

import DAO.ObservacionDAO;
import Modelo.Observacion;

import java.sql.Date;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con las observaciones de mentorías.
 */
public class ObservacionController {

    /**
     * Agrega una nueva observación a una mentoría específica.
     *
     * @param mentoriaId ID de la mentoría a la que se agrega la observación.
     * @param comentario Texto de la observación.
     */
    public static void agregarObservacion(int mentoriaId, String comentario) {
        Observacion obs = new Observacion();
        obs.setMentoriaId(mentoriaId);
        obs.setComentario(comentario);
        ObservacionDAO.insertarObservacion(obs);
    }

    /**
     * Lista todas las observaciones asociadas a una mentoría.
     *
     * @param mentoriaId ID de la mentoría.
     * @return Lista de objetos {@link Observacion}.
     */
    public static List<Observacion> listarPorMentoria(int mentoriaId) {
        return ObservacionDAO.listarPorMentoria(mentoriaId);
    }

    /**
     * Lista todas las observaciones realizadas a un usuario (emprendedor) específico.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de objetos {@link Observacion}.
     */
    public static List<Observacion> listarPorUsuario(int usuarioId) {
        return ObservacionDAO.listarPorUsuario(usuarioId);
    }

    /**
     * Lista todas las observaciones realizadas en una fecha específica.
     *
     * @param fecha Fecha para filtrar las observaciones.
     * @return Lista de objetos {@link Observacion}.
     */
    public static List<Observacion> listarPorFecha(Date fecha) {
        return ObservacionDAO.listarPorFecha(fecha);
    }
}
