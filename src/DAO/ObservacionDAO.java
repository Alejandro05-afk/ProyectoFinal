package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.ConexionRailway;

public class ObservacionDAO {

    // Inserta una nueva observación
    public static void insertarObservacion(int mentoriaId, String comentario) {
        String sql = "INSERT INTO observaciones (mentoria_id, comentario) VALUES (?, ?)";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mentoriaId);
            stmt.setString(2, comentario);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
