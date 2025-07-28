package Controlador;

import DAO.IdeaNegocioDAO;
import DAO.LogsSistemaDAO;
import Modelo.Categoria;
import Modelo.IdeaNegocio;

import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con las ideas de negocio.
 */
public class IdeaNegocioController {

    /**
     * Lista todas las ideas de negocio registradas.
     *
     * @return Lista de objetos {@link IdeaNegocio}.
     */
    public static List<IdeaNegocio> listarIdeas() {
        return IdeaNegocioDAO.listarIdeas();
    }

    /**
     * Lista todas las ideas de negocio asociadas a un usuario específico.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de ideas del usuario.
     */
    public static List<IdeaNegocio> listarIdeasPorUsuario(int usuarioId) {
        return IdeaNegocioDAO.listarIdeasPorUsuario(usuarioId);
    }

    /**
     * Busca una idea de negocio por su ID.
     *
     * @param id ID de la idea.
     * @return Objeto {@link IdeaNegocio} encontrado.
     */
    public static IdeaNegocio buscarPorId(int id) {
        return IdeaNegocioDAO.buscarPorId(id);
    }

    /**
     * Elimina una idea de negocio por su ID.
     *
     * @param id ID de la idea a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public static boolean eliminar(int id) {
        boolean eliminado = IdeaNegocioDAO.eliminar(id);
        if (eliminado) {
            LogsSistemaDAO.insertarLog(0, "Eliminó la idea con ID " + id); // Reemplaza 0 por ID real si está disponible
        }
        return eliminado;
    }

    /**
     * Crea una nueva idea de negocio.
     *
     * @param idea Objeto {@link IdeaNegocio} con la información a registrar.
     * @return {@code true} si la creación fue exitosa, {@code false} en caso contrario.
     */
    public static boolean crear(IdeaNegocio idea) {
        boolean creado = IdeaNegocioDAO.crear(idea);
        if (creado) {
            LogsSistemaDAO.insertarLog(0, "Creó una nueva idea con ID " + idea.getId());
        }
        return creado;
    }

    /**
     * Edita la información de una idea de negocio existente.
     *
     * @param idea Objeto {@link IdeaNegocio} con los datos actualizados.
     * @return {@code true} si la edición fue exitosa, {@code false} en caso contrario.
     */
    public static boolean editar(IdeaNegocio idea) {
        boolean actualizado = IdeaNegocioDAO.editar(idea);
        if (actualizado) {
            LogsSistemaDAO.insertarLog(0, "Actualizó la idea con ID " + idea.getId());
        }
        return actualizado;
    }

    /**
     * Obtiene los resultados registrados para una idea específica.
     *
     * @param ideaId ID de la idea.
     * @return Lista de resultados en formato {@link String}.
     */
    public static List<String> obtenerResultados(int ideaId) {
        return IdeaNegocioDAO.obtenerResultadosPorIdea(ideaId);
    }

    /**
     * Obtiene las estadísticas registradas para una idea específica.
     *
     * @param ideaId ID de la idea.
     * @return Lista de estadísticas en formato {@link String}.
     */
    public static List<String> obtenerEstadisticas(int ideaId) {
        return IdeaNegocioDAO.obtenerEstadisticasPorIdea(ideaId);
    }

    /**
     * Obtiene todos los logs registrados del sistema.
     *
     * @return Lista de logs como {@link String}.
     */
    public static List<String> obtenerLogs() {
        return LogsSistemaDAO.obtenerTodosLosLogs();
    }

    /**
     * Obtiene las ideas asignadas a un mentor específico.
     *
     * @param mentorId ID del mentor.
     * @return Lista de ideas asignadas.
     */
    public static List<IdeaNegocio> obtenerIdeasPorMentor(int mentorId) {
        return IdeaNegocioDAO.obtenerIdeasAsignadasAlMentor(mentorId);
    }

    /**
     * Registra una nueva idea de negocio con sus datos básicos.
     *
     * @param usuarioId ID del usuario que registra la idea.
     * @param categoriaId ID de la categoría.
     * @param titulo Título de la idea.
     * @param descripcion Descripción de la idea.
     * @return {@code true} si el registro fue exitoso, {@code false} en caso contrario.
     */
    public static boolean registrarIdea(int usuarioId, int categoriaId, String titulo, String descripcion) {
        return IdeaNegocioDAO.registrarIdea(usuarioId, categoriaId, titulo, descripcion);
    }

    /**
     * Lista todas las categorías disponibles para ideas de negocio.
     *
     * @return Lista de objetos {@link Categoria}.
     */
    public static List<Categoria> listarCategorias() {
        return IdeaNegocioDAO.obtenerCategorias();
    }

    /**
     * Obtiene el ID del usuario que creó una idea específica.
     *
     * @param ideaId ID de la idea.
     * @return ID del usuario propietario.
     */
    public static int obtenerUsuarioIdPorIdea(int ideaId) {
        return IdeaNegocioDAO.obtenerUsuarioIdPorIdea(ideaId);
    }
}
