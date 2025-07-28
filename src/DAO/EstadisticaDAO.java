package DAO;

import Modelo.EstadisticaIdea;
import Modelo.FaseProyecto;
import Conexion.ConexionRailway;
import Modelo.Mentoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar las operaciones relacionadas con las estadísticas de mentorías y fases.
 */
public class EstadisticaDAO {

    /**
     * Genera una nueva estadística para una mentoría y una fase específica.
     * Si no existe un avance de fase para esa combinación, lo crea con 0% de avance.
     *
     * @param mentoriaId ID de la mentoría.
     * @param faseId ID de la fase del proyecto.
     * @return ID de la estadística creada, o -1 si ocurrió un error.
     */
    public static int generarEstadisticaParaMentoria(int mentoriaId, int faseId) {
        int estadisticaId = -1;

        try (Connection conn = ConexionRailway.getConnection()) {
            // Buscar avance_fase existente
            String sqlBuscarAvance = "SELECT id FROM avance_fases WHERE idea_id = (SELECT idea_id FROM mentorias WHERE id = ?) AND fase_id = ? LIMIT 1";
            PreparedStatement psBuscar = conn.prepareStatement(sqlBuscarAvance);
            psBuscar.setInt(1, mentoriaId);
            psBuscar.setInt(2, faseId);
            ResultSet rsBuscar = psBuscar.executeQuery();

            int avanceFaseId;
            if (rsBuscar.next()) {
                avanceFaseId = rsBuscar.getInt("id");
            } else {
                // Insertar nuevo avance_fase con 0% de avance y fecha actual
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

            // Insertar estadistica_idea vinculando mentoría y avance_fase
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

    /**
     * Actualiza la fase asignada a una mentoría en la tabla estadisticas_idea.
     *
     * @param mentoriaId ID de la mentoría.
     * @param avanceFaseId ID del avance de fase a asignar.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
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

    /**
     * Obtiene todas las fases disponibles del proyecto.
     *
     * @return Lista de objetos {@link FaseProyecto}.
     */
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

    /**
     * Lista todas las estadísticas registradas, ordenadas por ID descendente.
     *
     * @return Lista de objetos {@link EstadisticaIdea}.
     */
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

    /**
     * Lista todas las estadísticas asociadas a una mentoría específica.
     *
     * @param mentoriaId ID de la mentoría.
     * @return Lista de objetos {@link EstadisticaIdea}.
     */
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

    /**
     * Busca una estadística por su ID.
     *
     * @param id ID de la estadística.
     * @return Objeto {@link EstadisticaIdea} si se encuentra, o {@code null} si no.
     */
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

    /**
     * Obtiene el ID de una fase a partir de su nombre.
     *
     * @param nombreFase Nombre de la fase.
     * @return ID de la fase, o -1 si no se encuentra.
     */
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

    /**
     * Registra un avance en la tabla avance_fases con porcentaje y fecha específicos.
     *
     * @param ideaId ID de la idea de negocio.
     * @param faseId ID de la fase.
     * @param porcentaje Porcentaje de avance.
     * @param fecha Fecha del avance en formato "yyyy-MM-dd HH:mm:ss".
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
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

    /**
     * Lista todas las mentorías asociadas a un usuario (emprendedor).
     *
     * @param usuarioId ID del usuario.
     * @return Lista de objetos {@link Mentoria}.
     */
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
