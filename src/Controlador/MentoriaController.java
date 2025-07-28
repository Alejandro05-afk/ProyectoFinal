package Controlador;

import DAO.MentoriaDAO;
import Modelo.Mentor;
import Modelo.Mentoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con mentorías.
 */
public class MentoriaController {

    /**
     * Obtiene una lista de mentores en formato de texto para visualización.
     * El formato es: "ID - NOMBRE (ESPECIALIDAD)".
     *
     * @return Lista de mentores formateada como {@link String}.
     */
    public static List<String> obtenerMentores() {
        List<Mentor> listaMentores = MentoriaDAO.obtenerMentores();
        List<String> resultado = new ArrayList<>();

        for (Mentor m : listaMentores) {
            resultado.add(m.getId() + " - " + m.getNombres() + " (" + m.getEspecialidad() + ")");
        }
        return resultado;
    }

    /**
     * Asigna un mentor a una idea de negocio (inserta una entrada en la tabla mentorías).
     *
     * @param ideaId ID de la idea de negocio.
     * @param mentorId ID del mentor a asignar.
     */
    public static void asignar(int ideaId, int mentorId) {
        MentoriaDAO.asignarMentoria(ideaId, mentorId);
    }

    /**
     * Crea una nueva mentoría con una fecha específica.
     *
     * @param ideaId ID de la idea de negocio.
     * @param mentorId ID del mentor asignado.
     * @param fecha Fecha programada para la mentoría.
     */
    public static void crearMentoria(int ideaId, int mentorId, String fecha) {
        MentoriaDAO.insertarMentoria(ideaId, mentorId, fecha);
    }

    /**
     * Obtiene todas las mentorías asignadas a un mentor.
     *
     * @param mentorId ID del mentor.
     * @return Lista de objetos {@link Mentoria}.
     */
    public static List<Mentoria> obtenerMentoriasPorMentor(int mentorId) {
        return MentoriaDAO.obtenerMentoriasPorMentor(mentorId);
    }

    /**
     * Obtiene el ID de la mentoría asociada a una idea específica.
     *
     * @param ideaId ID de la idea de negocio.
     * @return ID de la mentoría asociada.
     */
    public static int obtenerMentoriaIdPorIdea(int ideaId) {
        return MentoriaDAO.obtenerMentoriaIdPorIdea(ideaId);
    }
}
