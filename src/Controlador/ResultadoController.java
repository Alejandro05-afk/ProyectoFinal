package Controlador;

import DAO.ResultadoDAO;

import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con los resultados.
 */
public class ResultadoController {

    /**
     * Registra un nuevo resultado asociado a un usuario y una estadística.
     *
     * @param usuarioId ID del usuario que genera el resultado.
     * @param estadisticaId ID de la estadística vinculada.
     * @return ID del resultado insertado o valor indicativo.
     */
    public static int registrarResultado(int usuarioId, int estadisticaId) {
        return ResultadoDAO.insertarResultado(usuarioId, estadisticaId);
    }

    /**
     * Obtiene una lista de resultados en formato texto para mostrar en el dashboard del mentor.
     *
     * @param usuarioId ID del usuario mentor.
     * @return Lista de resultados en formato {@link String}.
     */
    public static List<String> obtenerResultadosDelMentor(int usuarioId) {
        return ResultadoDAO.obtenerResultadosDelMentor(usuarioId);
    }
}
