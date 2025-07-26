package Controlador;

import DAO.IdeaNegocioDAO;
import DAO.LogsSistemaDAO;
import Modelo.IdeaNegocio;

import java.util.List;

public class IdeaNegocioController {

    public static List<IdeaNegocio> listarIdeas() {
        return IdeaNegocioDAO.listarIdeas();
    }

    public static IdeaNegocio buscarPorId(int id) {
        return IdeaNegocioDAO.buscarPorId(id);
    }

    public static boolean eliminar(int id) {
        boolean eliminado = IdeaNegocioDAO.eliminar(id);
        if (eliminado) {
            LogsSistemaDAO.insertarLog(0, "Eliminó la idea con ID " + id); // Idealmente pasar usuario real
        }
        return eliminado;
    }

    public static boolean crear(IdeaNegocio idea) {
        boolean creado = IdeaNegocioDAO.crear(idea);
        if (creado) {
            LogsSistemaDAO.insertarLog(0, "Creó una nueva idea con ID " + idea.getId());
        }
        return creado;
    }

    public static boolean editar(IdeaNegocio idea) {
        boolean actualizado = IdeaNegocioDAO.editar(idea);
        if (actualizado) {
            LogsSistemaDAO.insertarLog(0, "Actualizó la idea con ID " + idea.getId());
        }
        return actualizado;
    }

    public static List<String> obtenerResultados(int ideaId) {
        return IdeaNegocioDAO.obtenerResultadosPorIdea(ideaId);
    }

    public static List<String> obtenerEstadisticas(int ideaId) {
        return IdeaNegocioDAO.obtenerEstadisticasPorIdea(ideaId);
    }

    public static List<String> obtenerLogs() {
        return LogsSistemaDAO.obtenerTodosLosLogs();
    }
}
