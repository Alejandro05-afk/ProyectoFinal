package DAO;
import Conexion.ConexionRailway;
import Modelo.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReporteDAO {
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
