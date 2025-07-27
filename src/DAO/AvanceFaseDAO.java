package DAO;

import Conexion.ConexionRailway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvanceFaseDAO {

    /**
     * Inserta un avance de fase y retorna el ID generado.
     */
    public static int insertarAvanceFase(int ideaId, int faceId, int porcentajeAvance) {
        String sql = "INSERT INTO avance_fases (idea_id, fase_id, porcentaje_avance) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ideaId);
            stmt.setInt(2, faceId);
            stmt.setInt(3, porcentajeAvance);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");  // ID generado del avance_fases
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
