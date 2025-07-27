package Controlador;
import DAO.ReporteDAO;
public class ReporteController {
    public static boolean registrarReporte(int resultadoId) {
        return ReporteDAO.insertarReporte(resultadoId);
    }
}
