package DAO;

import Modelo.Mentor;
import Conexion.ConexionRailway;
import Modelo.Mentoria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentoriaDAO {

    // Obtener todos los mentores con su información personal y especialidad
    public static List<Mentor> obtenerMentores() {
        List<Mentor> mentores = new ArrayList<>();
        String sql = """
            SELECT m.id AS mentor_id, m.persona_id, p.nombres, p.apellidos, m.especialidad 
            FROM mentores m 
            JOIN personas p ON m.persona_id = p.id
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mentor mentor = new Mentor(
                        rs.getInt("mentor_id"),
                        rs.getInt("persona_id"),
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

    // Asignar mentor a una idea de negocio (cambia estado de idea a 'aprobado')
    public static void asignarMentoria(int ideaId, int mentorId) {
        int estadoId = obtenerEstadoId("pendiente");

        if (estadoId == -1) {
            System.out.println(" Estado 'pendiente' no encontrado.");
            return;
        }

        try (Connection conn = ConexionRailway.getConnection()) {
            // Insertar mentoría
            String sql = "INSERT INTO mentorias (idea_id, mentor_id, fecha, estado) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, ideaId);
                ps.setInt(2, mentorId);
                ps.setInt(3, estadoId);
                ps.executeUpdate();
            }

            // Actualizar estado de la idea
            String updateIdea = "UPDATE ideas_negocio SET estado = 'aprobado' WHERE id = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(updateIdea)) {
                ps2.setInt(1, ideaId);
                ps2.executeUpdate();
            }

            System.out.println(" Mentoría asignada con éxito.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insertar una nueva mentoría con fecha explícita
    public static void insertarMentoria(int ideaId, int mentorId, String fechaTexto) {
        int estadoId = obtenerEstadoId("pendiente");
        if (estadoId == -1) {
            System.out.println("Estado 'pendiente' no encontrado.");
            return;
        }

        try {
            // Limpia la fecha de posibles comillas
            fechaTexto = fechaTexto.replace("\"", "").trim();

            // Formato esperado yyyy-MM-dd HH:mm (ej: 2025-07-28 15:30)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime fechaHora = LocalDateTime.parse(fechaTexto, formatter);

            Timestamp timestamp = Timestamp.valueOf(fechaHora);

            String sql = "INSERT INTO mentorias (idea_id, mentor_id, fecha, estado) VALUES (?, ?, ?, ?)";

            try (Connection conn = ConexionRailway.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, ideaId);
                stmt.setInt(2, mentorId);
                stmt.setTimestamp(3, timestamp);
                stmt.setInt(4, estadoId);
                stmt.executeUpdate();

                System.out.println("Mentoría registrada correctamente.");
            }

        } catch (Exception e) {
            System.err.println("Error al parsear la fecha o insertar mentoría. Formato esperado: 'yyyy-MM-dd HH:mm'.");
            e.printStackTrace();
        }
    }


    // Obtener el ID de un estado por nombre
    private static int obtenerEstadoId(String estado) {
        String sql = "SELECT id FROM tipo_estados WHERE LOWER(tipo) = LOWER(?)";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Obtener resumen de todas las mentorías con nombres y estado
    public static List<String> obtenerMentorias() {
        List<String> mentorias = new ArrayList<>();
        String sql = """
            SELECT i.id AS idea_id,
                   p.nombres || ' ' || p.apellidos AS mentor_nombre,
                   m.fecha,
                   te.tipo AS estado_nombre
            FROM mentorias m
            JOIN mentores mt ON m.mentor_id = mt.id
            JOIN personas p ON mt.persona_id = p.id
            JOIN ideas_negocio i ON m.idea_id = i.id
            JOIN tipo_estados te ON m.estado = te.id
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String linea = "Idea ID: " + rs.getInt("idea_id") +
                        " - Mentor: " + rs.getString("mentor_nombre") +
                        " - Fecha: " + rs.getTimestamp("fecha") +
                        " - Estado: " + rs.getString("estado_nombre");
                mentorias.add(linea);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mentorias;
    }

    // Obtener mentorías asignadas a un mentor específico
    public static List<Mentoria> obtenerMentoriasPorMentor(int mentorId) {
        List<Mentoria> lista = new ArrayList<>();
        String sql = """
            SELECT m.id, m.idea_id, m.mentor_id, m.fecha, te.tipo AS estado
            FROM mentorias m
            JOIN tipo_estados te ON m.estado = te.id
            WHERE m.mentor_id = ?
            ORDER BY m.fecha DESC
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mentorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Mentoria m = new Mentoria(
                        rs.getInt("id"),
                        rs.getInt("idea_id"),
                        rs.getInt("mentor_id"),
                        rs.getTimestamp("fecha").toString(),
                        rs.getString("estado")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
