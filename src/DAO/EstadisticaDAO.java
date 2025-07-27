package DAO;

import Modelo.FaseProyecto;
import Conexion.ConexionRailway;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadisticaDAO {

    // Generar nueva estadística para una idea
    public static int generarEstadisticaParaIdea(int ideaId) {
        String sql = "INSERT INTO estadisticas_idea (mentoria_id, avance_fase_id) VALUES (?, ?) RETURNING id";
        int estadisticaId = -1;

        try (Connection conn = ConexionRailway.getConnection()) {
            // Buscar la última mentoría para esta idea
            String consultaMentoria = "SELECT id FROM mentorias WHERE idea_id = ? ORDER BY fecha DESC LIMIT 1";
            PreparedStatement stmtMentoria = conn.prepareStatement(consultaMentoria);
            stmtMentoria.setInt(1, ideaId);
            ResultSet rsMentoria = stmtMentoria.executeQuery();

            if (rsMentoria.next()) {
                int mentoriaId = rsMentoria.getInt("id");

                // Buscar un avance_fase válido para esta idea
                String consultaAvance = "SELECT id FROM avance_fases WHERE idea_id = ? ORDER BY fecha_avance DESC LIMIT 1";
                PreparedStatement stmtAvance = conn.prepareStatement(consultaAvance);
                stmtAvance.setInt(1, ideaId);
                ResultSet rsAvance = stmtAvance.executeQuery();

                int avanceFaseId = -1;
                if (rsAvance.next()) {
                    avanceFaseId = rsAvance.getInt("id");
                } else {
                    // Crear avance_fase inicial si no existe
                    String insertAvance = "INSERT INTO avance_fases (idea_id, fase_id, porcentaje_avance, fecha_avance) VALUES (?, ?, ?, ?) RETURNING id";
                    PreparedStatement stmtInsertAvance = conn.prepareStatement(insertAvance);
                    stmtInsertAvance.setInt(1, ideaId);
                    stmtInsertAvance.setInt(2, 1); // Asume fase_id=1 es fase inicial
                    stmtInsertAvance.setInt(3, 0); // porcentaje 0%
                    stmtInsertAvance.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));

                    ResultSet rsInsert = stmtInsertAvance.executeQuery();
                    if (rsInsert.next()) {
                        avanceFaseId = rsInsert.getInt("id");
                    }
                }

                if (avanceFaseId != -1) {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, mentoriaId);
                    stmt.setInt(2, avanceFaseId);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        estadisticaId = rs.getInt("id");
                    }
                } else {
                    System.out.println("No se pudo obtener ni crear un avance_fase válido.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticaId;
    }


    // Asignar fase a mentoría
    public static void asignarFaseAMentoria(int mentoriaId, int avanceFaseId) {
        String sql = "INSERT INTO estadisticas_idea (mentoria_id, avance_fase_id) VALUES (?, ?)";
        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mentoriaId);
            stmt.setInt(2, avanceFaseId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Listar fases disponibles
    public static List<FaseProyecto> obtenerFasesProyecto() {
        List<FaseProyecto> fases = new ArrayList<>();
        String sql = "SELECT id, fase FROM fases_proyecto";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FaseProyecto fase = new FaseProyecto(
                        rs.getInt("id"),
                        rs.getString("fase")
                );
                fases.add(fase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fases;
    }
}
