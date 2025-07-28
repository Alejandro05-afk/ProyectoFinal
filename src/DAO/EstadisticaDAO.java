package DAO;

import Modelo.EstadisticaIdea;
import Modelo.FaseProyecto;
import Conexion.ConexionRailway;
import Modelo.Mentoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadisticaDAO {

    // Generar nueva estadística para una mentoría y fase (versión mejorada)
    public static int generarEstadisticaParaMentoria(int mentoriaId, int faseId) {
        int estadisticaId = -1;

        try (Connection conn = ConexionRailway.getConnection()) {
            // Crear o buscar avance_fase para la mentoría y fase
            String sqlBuscarAvance = "SELECT id FROM avance_fases WHERE idea_id = (SELECT idea_id FROM mentorias WHERE id = ?) AND fase_id = ? LIMIT 1";
            PreparedStatement psBuscar = conn.prepareStatement(sqlBuscarAvance);
            psBuscar.setInt(1, mentoriaId);
            psBuscar.setInt(2, faseId);
            ResultSet rsBuscar = psBuscar.executeQuery();

            int avanceFaseId;
            if (rsBuscar.next()) {
                avanceFaseId = rsBuscar.getInt("id");
            } else {
                // Insertar nuevo avance_fase con 0% y fecha actual
                String sqlInsertAvance = "INSERT INTO avance_fases (idea_id, fase_id, porcentaje_avance, fecha_avance) VALUES ((SELECT idea_id FROM mentorias WHERE id = ?), ?, 0, CURRENT_TIMESTAMP) RETURNING id";
                PreparedStatement psInsert = conn.prepareStatement(sqlInsertAvance);
                psInsert.setInt(1, mentoriaId);
                psInsert.setInt(2, faseId);
                ResultSet rsInsert = psInsert.executeQuery();
                if (rsInsert.next()) {
                    avanceFaseId = rsInsert.getInt("id");
                } else {
                    return -1; // Error al insertar avance_fase
                }
            }

            // Insertar estadistica_idea con mentoría y avance_fase
            String sqlInsertEstadistica = "INSERT INTO estadisticas_idea (mentoria_id, avance_fase_id) VALUES (?, ?) RETURNING id";
            PreparedStatement psEstadistica = conn.prepareStatement(sqlInsertEstadistica);
            psEstadistica.setInt(1, mentoriaId);
            psEstadistica.setInt(2, avanceFaseId);
            ResultSet rsEstadistica = psEstadistica.executeQuery();

            if (rsEstadistica.next()) {
                estadisticaId = rsEstadistica.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticaId;
    }

    // Asignar fase a mentoría actualizando la estadística (retorna true si tuvo éxito)
    public static boolean asignarFaseAMentoria(int mentoriaId, int avanceFaseId) {
        String sql = "UPDATE estadisticas_idea SET avance_fase_id = ? WHERE mentoria_id = ?";
        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, avanceFaseId);
            stmt.setInt(2, mentoriaId);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    // Listar estadísticas de un mentor (por mentor_id)
    public static List<EstadisticaIdea> listarTodas() {
        List<EstadisticaIdea> lista = new ArrayList<>();
        String sql = "SELECT id, mentoria_id, avance_fase_id FROM estadisticas_idea ORDER BY id DESC";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EstadisticaIdea estadistica = new EstadisticaIdea(
                        rs.getInt("id"),
                        rs.getInt("mentoria_id"),
                        rs.getInt("avance_fase_id")
                );
                lista.add(estadistica);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static List<EstadisticaIdea> listarPorMentoria(int mentoriaId) {
        List<EstadisticaIdea> lista = new ArrayList<>();
        String sql = "SELECT id, mentoria_id, avance_fase_id FROM estadisticas_idea WHERE mentoria_id = ? ORDER BY id DESC";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mentoriaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EstadisticaIdea estadistica = new EstadisticaIdea(
                        rs.getInt("id"),
                        rs.getInt("mentoria_id"),
                        rs.getInt("avance_fase_id")
                );
                lista.add(estadistica);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static EstadisticaIdea buscarPorId(int id) {
        EstadisticaIdea estadistica = null;
        String sql = "SELECT id, mentoria_id, avance_fase_id FROM estadisticas_idea WHERE id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                estadistica = new EstadisticaIdea(
                        rs.getInt("id"),
                        rs.getInt("mentoria_id"),
                        rs.getInt("avance_fase_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadistica;
    }
    public static int obtenerFaseIdPorNombre(String nombreFase) {
        int id = -1;
        String sql = "SELECT id FROM fases_proyecto WHERE fase = ?::fase_enum LIMIT 1";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreFase);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public static boolean registrarAvance(int ideaId, int faseId, int porcentaje, String fecha) {
        String sql = "INSERT INTO avance_fases (idea_id, fase_id, porcentaje_avance, fecha_avance) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ideaId);
            stmt.setInt(2, faseId);
            stmt.setInt(3, porcentaje);
            stmt.setTimestamp(4, Timestamp.valueOf(fecha)); // El formato fecha debe ser "yyyy-MM-dd HH:mm:ss"

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Mentoria> listarMentoriasPorUsuario(int usuarioId) {
        List<Mentoria> mentorias = new ArrayList<>();
        String sql = """
        SELECT m.id, m.idea_id, m.mentor_id, m.fecha, m.estado
        FROM mentorias m
        JOIN ideas_negocio i ON m.idea_id = i.id
        WHERE i.usuario_id = ?
        ORDER BY m.fecha DESC
    """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Mentoria mentoria = new Mentoria();
                mentoria.setId(rs.getInt("id"));
                mentoria.setIdeaId(rs.getInt("idea_id"));
                mentoria.setMentorId(rs.getInt("mentor_id"));

                Timestamp ts = rs.getTimestamp("fecha");
                String fechaStr = (ts != null) ? ts.toString() : null;
                mentoria.setFecha(fechaStr);

                mentoria.setEstado(rs.getString("estado"));

                mentorias.add(mentoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mentorias;
    }




}
