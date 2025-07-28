package DAO;

import Conexion.ConexionRailway;
import Modelo.Observacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObservacionDAO {

    /**
     * Inserta una nueva observación asociada a una mentoría.
     * @param obs objeto Observacion con mentoriaId y comentario
     * @return true si se insertó correctamente, false si hubo error
     */
    public static boolean insertarObservacion(Observacion obs) {
        String sql = "INSERT INTO observaciones (mentoria_id, comentario) VALUES (?, ?)";
        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, obs.getMentoriaId());  // ID de la mentoría a la que pertenece la observación
            stmt.setString(2, obs.getComentario()); // Texto de la observación
            stmt.executeUpdate(); // Ejecuta el insert
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar observación: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas las observaciones asociadas a una mentoría específica.
     * @param mentoriaId ID de la mentoría
     * @return lista de observaciones para esa mentoría
     */
    public static List<Observacion> listarPorMentoria(int mentoriaId) {
        List<Observacion> observaciones = new ArrayList<>();
        String sql = "SELECT * FROM observaciones WHERE mentoria_id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mentoriaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Observacion obs = new Observacion(
                        rs.getInt("id"),
                        rs.getInt("mentoria_id"),
                        rs.getString("comentario")
                );
                observaciones.add(obs);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar observaciones por mentoría: " + e.getMessage());
        }

        return observaciones;
    }

    /**
     * Lista todas las observaciones asociadas a un usuario (emprendedor).
     * Para ello, se hace join desde observaciones -> mentorias -> ideas_negocio,
     * filtrando por usuario_id en ideas_negocio.
     * @param usuarioId ID del usuario
     * @return lista de observaciones asociadas al usuario
     */
    public static List<Observacion> listarPorUsuario(int usuarioId) {
        List<Observacion> observaciones = new ArrayList<>();
        String sql = """
            SELECT o.id, o.mentoria_id, o.comentario
            FROM observaciones o
            JOIN mentorias m ON o.mentoria_id = m.id
            JOIN ideas_negocio i ON m.idea_id = i.id
            WHERE i.usuario_id = ?
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Observacion obs = new Observacion(
                        rs.getInt("id"),
                        rs.getInt("mentoria_id"),
                        rs.getString("comentario")
                );
                observaciones.add(obs);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar observaciones por usuario: " + e.getMessage());
        }

        return observaciones;
    }

    /**
     * Lista las observaciones realizadas en una fecha específica.
     * Se asume que la tabla observaciones tiene una columna 'fecha_observacion' de tipo timestamp o date.
     * @param fecha fecha para filtrar las observaciones (java.sql.Date)
     * @return lista de observaciones en esa fecha
     */
    public static List<Observacion> listarPorFecha(Date fecha) {
        List<Observacion> observaciones = new ArrayList<>();
        String sql = "SELECT * FROM observaciones WHERE DATE(fecha_observacion) = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, fecha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Observacion obs = new Observacion(
                        rs.getInt("id"),
                        rs.getInt("mentoria_id"),
                        rs.getString("comentario")
                );
                observaciones.add(obs);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar observaciones por fecha: " + e.getMessage());
        }

        return observaciones;
    }
}
