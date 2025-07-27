package DAO;
import Conexion.ConexionRailway;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorDAO {

    public static Integer obtenerMentorIdPorPersonaId(int personaId) {
        String sql = "SELECT id FROM mentores WHERE persona_id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personaId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int mentorId = rs.getInt("id");
                if (mentorId > 0) {
                    return mentorId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Retorna null si no se encontró o error
    }

}


