package Controlador;

import DAO.LogsSistemaDAO;

import java.util.List;

/**
 * Controlador encargado de gestionar la recuperación de los registros (logs) del sistema.
 */
public class LogSistemaController {

    /**
     * Obtiene todos los logs del sistema en formato de texto para su visualización en la interfaz (por ejemplo, en un JList).
     *
     * @return Lista de logs como {@link String}.
     */
    public static List<String> obtenerLogsComoTexto() {
        List<String> logs = LogsSistemaDAO.obtenerTodosLosLogs();
        if (logs == null || logs.isEmpty()) {
            System.out.println("No se encontraron logs en la base de datos.");
        } else {
            System.out.println("Logs obtenidos: " + logs.size());
        }
        return logs;
    }
}
