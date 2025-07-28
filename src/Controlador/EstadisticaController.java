package Controlador;

import DAO.EstadisticaDAO;
import Modelo.FaseProyecto;
import Modelo.EstadisticaIdea;
import Modelo.Mentoria;

import java.util.List;

/**
 * Controlador que gestiona las operaciones relacionadas con las estadísticas de ideas de negocio
 * y fases del proyecto asociadas a mentorías.
 */
public class EstadisticaController {

    /**
     * Genera una nueva estadística para una mentoría y fase específicas.
     *
     * @param mentoriaId ID de la mentoría.
     * @param faseId ID de la fase del proyecto.
     * @return ID de la estadística generada.
     */
    public static int generarEstadistica(int mentoriaId, int faseId) {
        return EstadisticaDAO.generarEstadisticaParaMentoria(mentoriaId, faseId);
    }

    /**
     * Asigna una fase específica a una mentoría.
     *
     * @param mentoriaId ID de la mentoría.
     * @param faseId ID de la fase del proyecto.
     * @return {@code true} si la asignación fue exitosa, {@code false} en caso contrario.
     */
    public static boolean asignarFase(int mentoriaId, int faseId) {
        return EstadisticaDAO.asignarFaseAMentoria(mentoriaId, faseId);
    }

    /**
     * Obtiene la lista de todas las fases del proyecto disponibles.
     *
     * @return Lista de objetos {@link FaseProyecto}.
     */
    public static List<FaseProyecto> listarFases() {
        return EstadisticaDAO.obtenerFasesProyecto();
    }

    /**
     * Lista todas las estadísticas registradas.
     *
     * @return Lista de objetos {@link EstadisticaIdea}.
     */
    public static List<EstadisticaIdea> listarTodasEstadisticas() {
        return EstadisticaDAO.listarTodas();
    }

    /**
     * Lista las estadísticas asociadas a una mentoría específica.
     *
     * @param mentoriaId ID de la mentoría.
     * @return Lista de estadísticas de la mentoría.
     */
    public static List<EstadisticaIdea> listarPorMentoria(int mentoriaId) {
        return EstadisticaDAO.listarPorMentoria(mentoriaId);
    }

    /**
     * Busca una estadística por su ID.
     *
     * @param id ID de la estadística.
     * @return Objeto {@link EstadisticaIdea} correspondiente.
     */
    public static EstadisticaIdea buscarPorId(int id) {
        return EstadisticaDAO.buscarPorId(id);
    }

    /**
     * Obtiene el ID de una fase del proyecto dado su nombre.
     *
     * @param nombreFase Nombre de la fase.
     * @return ID de la fase.
     */
    public static int obtenerFaseIdPorNombre(String nombreFase) {
        return EstadisticaDAO.obtenerFaseIdPorNombre(nombreFase);
    }

    /**
     * Registra un avance de una idea en una fase determinada.
     *
     * @param ideaId ID de la idea.
     * @param faseId ID de la fase.
     * @param porcentaje Porcentaje de avance.
     * @param fecha Fecha del registro del avance.
     * @return {@code true} si el avance se registró correctamente, {@code false} en caso contrario.
     */
    public static boolean registrarAvance(int ideaId, int faseId, int porcentaje, String fecha) {
        return EstadisticaDAO.registrarAvance(ideaId, faseId, porcentaje, fecha);
    }

    /**
     * Lista todas las mentorías asociadas a un usuario.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de objetos {@link Mentoria}.
     */
    public static List<Mentoria> listarMentoriasPorUsuario(int usuarioId) {
        return EstadisticaDAO.listarMentoriasPorUsuario(usuarioId);
    }

}
