package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Conexion.ConexionRailway;

public class TipoEstadoDAO {

    // ✅ Método para obtener el ID de un tipo de estado por su nombre
    public static int getIdPorNombre(String nombreEstado) {
        String sql = "SELECT id FROM tipo_estados WHERE LOWER(tipo) = LOWER(?)";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreEstado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // No encontrado
    }

    // ✅ Método para insertar un tipo de estado si no existe
    public static void insertarSiNoExiste(String nombreEstado) {
        String checkSql = "SELECT id FROM tipo_estados WHERE LOWER(tipo) = LOWER(?)";
        String insertSql = "INSERT INTO tipo_estados (tipo) VALUES (?)";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, nombreEstado);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, nombreEstado);
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Método para listar todos los tipos de estado
    public static List<String> listarTodos() {
        List<String> estados = new ArrayList<>();
        String sql = "SELECT tipo FROM tipo_estados";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                estados.add(rs.getString("tipo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estados;
    }
}
