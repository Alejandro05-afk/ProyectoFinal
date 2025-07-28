package DAO;

import Conexion.ConexionRailway;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReporteDAO {

    /**
     * Inserta un nuevo reporte asociado a un resultado específico.
     *
     * @param resultadoId El ID del resultado al cual se asocia el reporte.
     * @return true si el reporte fue insertado correctamente; false en caso contrario.
     */
    public static boolean insertarReporte(int resultadoId) {
        String sql = "INSERT INTO reportes (resultado_id) VALUES (?)";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, resultadoId);
            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
