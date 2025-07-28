package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Conexion.ConexionRailway;
/**
 * DAO para gestionar las operaciones relacionadas con los resultados.
 */
public class ResultadoDAO {

    /**
     * Inserta un nuevo resultado en la base de datos y devuelve el ID generado.
     *
     * @param usuarioId     ID del usuario asociado al resultado.
     * @param estadisticaId ID de la estadística asociada al resultado.
     * @return El ID generado para el nuevo resultado, o -1 si hubo un error.
     */
    public static int insertarResultado(int usuarioId, int estadisticaId) {
        String sql = "INSERT INTO resultados (usuario_id, estadisticas_id) VALUES (?, ?) RETURNING id";
        int idGenerado = -1;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setInt(2, estadisticaId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idGenerado = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    /**
     * Obtiene una lista de resultados en formato de texto para un usuario específico.
     *
     * @param usuarioId ID del usuario cuyos resultados se quieren obtener.
     * @return Lista de cadenas con la información detallada de cada resultado.
     */
    public static List<String> obtenerResultadosPorUsuario(int usuarioId) {
        List<String> resultados = new ArrayList<>();
        String sql = """
            SELECT
                r.id AS resultado_id,
                p.nombres || ' ' || p.apellidos AS usuario,
                f.fase AS nombre_fase,
                af.porcentaje_avance,
                af.fecha_avance,
                i.titulo AS idea_titulo
            FROM resultados r
            JOIN estadisticas_idea e ON r.estadisticas_id = e.id
            JOIN avance_fases af ON e.avance_fase_id = af.id
            JOIN fases_proyecto f ON af.fase_id = f.id
            JOIN mentorias m ON e.mentoria_id = m.id
            JOIN ideas_negocio i ON m.idea_id = i.id
            JOIN usuarios u ON r.usuario_id = u.id
            JOIN personas p ON u.persona_id = p.id
            WHERE r.usuario_id = ?;
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String linea = "Resultado ID: " + rs.getInt("resultado_id") +
                        ", Usuario: " + rs.getString("usuario") +
                        ", Fase: " + rs.getString("nombre_fase") +
                        ", Porcentaje: " + rs.getInt("porcentaje_avance") + "%" +
                        ", Fecha de Avance: " + rs.getDate("fecha_avance") +
                        ", Idea: " + rs.getString("idea_titulo");
                resultados.add(linea);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    /**
     * Obtiene una lista de resultados en formato de texto para un mentor específico.
     *
     * @param mentorId ID del mentor cuyos resultados asociados se desean obtener.
     * @return Lista de cadenas con información detallada de cada resultado asociado a las ideas mentoradas.
     */
    public static List<String> obtenerResultadosDelMentor(int mentorId) {
        List<String> resultados = new ArrayList<>();
        String sql = """
            SELECT 
                r.id AS resultado_id,
                p.nombres || ' ' || p.apellidos AS nombre_emprendedor,
                f.fase AS nombre_fase,
                af.porcentaje_avance,
                af.fecha_avance,
                i.titulo AS idea_titulo
            FROM resultados r
            JOIN estadisticas_idea e ON r.estadisticas_id = e.id
            JOIN avance_fases af ON e.avance_fase_id = af.id
            JOIN fases_proyecto f ON af.fase_id = f.id
            JOIN mentorias m ON e.mentoria_id = m.id
            JOIN ideas_negocio i ON m.idea_id = i.id
            JOIN usuarios u ON i.usuario_id = u.id
            JOIN personas p ON u.persona_id = p.id
            WHERE m.mentor_id = ?;
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mentorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String linea = "Resultado ID: " + rs.getInt("resultado_id") +
                        ", Emprendedor: " + rs.getString("nombre_emprendedor") +
                        ", Fase: " + rs.getString("nombre_fase") +
                        ", Porcentaje: " + rs.getInt("porcentaje_avance") + "%" +
                        ", Fecha de Avance: " + rs.getDate("fecha_avance") +
                        ", Idea: " + rs.getString("idea_titulo");
                resultados.add(linea);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
}
