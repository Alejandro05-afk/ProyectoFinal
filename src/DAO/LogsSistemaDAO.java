package DAO;

import Conexion.ConexionRailway;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para manejar los logs del sistema.
 * Permite insertar logs y obtener la lista de logs con detalles del usuario.
 */
public class LogsSistemaDAO {

    /**
     * Inserta un nuevo registro de log en la base de datos.
     * @param usuarioId ID del usuario que realiza la acción.
     * @param accion Descripción de la acción realizada.
     * @return true si la inserción fue exitosa, false si hubo error.
     */
    public static boolean insertarLog(int usuarioId, String accion) {
        String sql = "INSERT INTO logs_sistema(usuario_id, accion, fecha) VALUES (?, ?, NOW())";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setString(2, accion);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserta un nuevo registro de log sin retornar resultado.
     * @param usuarioId ID del usuario que realiza la acción.
     * @param accion Descripción de la acción realizada.
     */
    public static void registrarLog(int usuarioId, String accion) {
        String sql = "INSERT INTO logs_sistema(usuario_id, accion, fecha) VALUES (?, ?, NOW())";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setString(2, accion);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene todos los logs del sistema con información del usuario.
     * @return Lista de strings con la información del log formateada.
     */
    public static List<String> obtenerTodosLosLogs() {
        List<String> logs = new ArrayList<>();
        String sql = """
            SELECT 
                ls.id AS log_id, 
                u.id AS usuario_id, 
                p.nombres, 
                p.apellidos, 
                ls.accion, 
                ls.fecha
            FROM logs_sistema ls
            JOIN usuarios u ON ls.usuario_id = u.id
            JOIN personas p ON u.persona_id = p.id
            ORDER BY ls.fecha DESC
            """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String log = String.format("🕒 %s - %s %s (%d): %s",
                        rs.getTimestamp("fecha"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getInt("usuario_id"),
                        rs.getString("accion"));
                logs.add(log);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }
}
