package DAO;

import Modelo.Mentor;
import Conexion.ConexionRailway;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentoriaDAO {

    // Obtener todos los mentores con su información personal y especialidad
    public static List<Mentor> obtenerMentores() {
        List<Mentor> mentores = new ArrayList<>();
        String sql = """
                SELECT p.id, p.nombres, p.apellidos, m.especialidad 
                FROM mentores m 
                JOIN personas p ON m.persona_id = p.id
                """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mentor mentor = new Mentor(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("especialidad")
                );
                mentores.add(mentor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentores;
    }

    // Asignar mentor a una idea de negocio
    public static void asignarMentoria(int ideaId, int mentorId) {
        String sql = "INSERT INTO mentorias (idea_id, mentor_id, fecha, estado) VALUES (?, ?, CURRENT_TIMESTAMP, 'pendiente')";
        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ideaId);
            ps.setInt(2, mentorId);
            ps.executeUpdate();

            // Opcional: también puedes actualizar el estado de la idea a "aprobado"
            String updateIdea = "UPDATE ideas_negocio SET estado = 'aprobado' WHERE id = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(updateIdea)) {
                ps2.setInt(1, ideaId);
                ps2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obtener todas las mentorías (opcional si lo necesitas en otra parte)
    public static List<String> obtenerMentorias() {
        List<String> mentorias = new ArrayList<>();
        String sql = """
                SELECT i.id AS idea_id, p.nombres || ' ' || p.apellidos AS mentor_nombre, m.fecha, m.estado
                FROM mentorias m
                JOIN mentores mt ON m.mentor_id = mt.persona_id
                JOIN personas p ON mt.persona_id = p.id
                JOIN ideas_negocio i ON m.idea_id = i.id
                """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String linea = "Idea ID: " + rs.getInt("idea_id") +
                        " - Mentor: " + rs.getString("mentor_nombre") +
                        " - Fecha: " + rs.getTimestamp("fecha") +
                        " - Estado: " + rs.getString("estado");
                mentorias.add(linea);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mentorias;
    }
}
