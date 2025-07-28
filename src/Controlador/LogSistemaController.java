package Controlador;

import DAO.LogsSistemaDAO;

import java.util.List;

public class LogSistemaController {

    // Obtener los logs como Strings (para mostrar en el JList)
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
