package Controlador;

import DAO.ResultadoDAO;

import java.util.List;

public class ResultadoController {

    // Registra un nuevo resultado
    public static int registrarResultado(int usuarioId, int estadisticaId) {
        return ResultadoDAO.insertarResultado(usuarioId, estadisticaId);
    }


    // Devuelve una lista de resultados en formato String para mostrar en el DashboardMentor
    public static List<String> obtenerResultadosDelMentor(int usuarioId) {
        return ResultadoDAO.obtenerResultadosDelMentor(usuarioId);
    }

}
