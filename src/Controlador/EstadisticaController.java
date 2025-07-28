package Controlador;

import DAO.EstadisticaDAO;
import Modelo.FaseProyecto;
import Modelo.EstadisticaIdea;
import Modelo.Mentoria;

import java.util.List;

public class EstadisticaController {

    // Genera una nueva estadística para una idea y devuelve su ID
    public static int generarEstadistica(int mentoriaId, int faseId) {
        return EstadisticaDAO.generarEstadisticaParaMentoria(mentoriaId, faseId);
    }

    // Asigna una fase a una mentoría (útil si se actualiza después de creada)
    public static boolean asignarFase(int mentoriaId, int faseId) {
        return EstadisticaDAO.asignarFaseAMentoria(mentoriaId, faseId);
    }

    // Devuelve todas las fases del proyecto disponibles
    public static List<FaseProyecto> listarFases() {
        return EstadisticaDAO.obtenerFasesProyecto();
    }

    // Lista todas las estadísticas registradas
    public static List<EstadisticaIdea> listarTodasEstadisticas() {
        return EstadisticaDAO.listarTodas();
    }

    // Lista las estadísticas de una mentoría específica
    public static List<EstadisticaIdea> listarPorMentoria(int mentoriaId) {
        return EstadisticaDAO.listarPorMentoria(mentoriaId);
    }

    // Buscar una estadística por ID
    public static EstadisticaIdea buscarPorId(int id) {
        return EstadisticaDAO.buscarPorId(id);
    }
    public static int obtenerFaseIdPorNombre(String nombreFase) {
        return EstadisticaDAO.obtenerFaseIdPorNombre(nombreFase);
    }
    public static boolean registrarAvance(int ideaId, int faseId, int porcentaje, String fecha) {
        return EstadisticaDAO.registrarAvance(ideaId, faseId, porcentaje, fecha);
    }
    public static List<Mentoria> listarMentoriasPorUsuario(int usuarioId) {
        return EstadisticaDAO.listarMentoriasPorUsuario(usuarioId);
    }

}
