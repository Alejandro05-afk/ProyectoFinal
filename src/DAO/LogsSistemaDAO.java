package DAO;

import Conexion.ConexionRailway;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogsSistemaDAO {

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

    public static List<String> obtenerTodosLosLogs() {
        List<String> logs = new ArrayList<>();
        String sql = """
                SELECT ls.id, u.usuario_id, p.nombres, p.apellidos, ls.accion, ls.fecha
                FROM logs_sistema ls
                JOIN usuarios u ON ls.usuario_id = u.usuario_id
                JOIN personas p ON u.persona_id = p.persona_id
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
