package Controlador;

import DAO.ReporteDAO;

/**
 * Controlador encargado de gestionar la creación de reportes.
 */
public class ReporteController {

    /**
     * Registra un nuevo reporte asociado a un resultado específico.
     *
     * @param resultadoId ID del resultado al que se vincula el reporte.
     * @return {@code true} si el reporte se insertó correctamente; {@code false} en caso contrario.
     */
    public static boolean registrarReporte(int resultadoId) {
        return ReporteDAO.insertarReporte(resultadoId);
    }
}
