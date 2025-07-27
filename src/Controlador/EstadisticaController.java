package Controlador;

import DAO.EstadisticaDAO;
import Modelo.FaseProyecto;

import java.util.List;

public class EstadisticaController {

    // Genera una nueva estadística para una idea (ejemplo básico)
    public static int generarEstadistica(int ideaId) {
        return EstadisticaDAO.generarEstadisticaParaIdea(ideaId);
    }

    // Asigna una fase a una mentoría
    public static void asignarFase(int mentoriaId, int faseId) {
        EstadisticaDAO.asignarFaseAMentoria(mentoriaId, faseId);
    }

    // Devuelve todas las fases disponibles
    public static List<FaseProyecto> listarFases() {
        return EstadisticaDAO.obtenerFasesProyecto();
    }

}
