package DAO;

import Conexion.ConexionRailway;

import java.sql.*;

/**
 * DAO para operaciones relacionadas con mentores.
 */
public class MentorDAO {

    /**
     * Obtiene el ID del mentor asociado a una persona por su personaId.
     * @param personaId ID de la persona.
     * @return El ID del mentor si se encuentra, o null si no existe o hubo un error.
     */
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
