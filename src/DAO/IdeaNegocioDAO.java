package DAO;

import Conexion.ConexionRailway;
import Modelo.IdeaNegocio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IdeaNegocioDAO {

    public static List<IdeaNegocio> listarIdeas() {
        List<IdeaNegocio> lista = new ArrayList<>();
        String sql = "SELECT id, usuario_id, categoria_id, titulo, descripcion, estado FROM ideas_negocio";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                IdeaNegocio idea = new IdeaNegocio();
                idea.setId(rs.getInt("id"));
                idea.setUsuarioId(rs.getInt("usuario_id"));
                idea.setCategoriaId(rs.getInt("categoria_id"));
                idea.setTitulo(rs.getString("titulo"));
                idea.setDescripcion(rs.getString("descripcion"));
                idea.setEstado(rs.getString("estado"));
                lista.add(idea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static IdeaNegocio buscarPorId(int id) {
        String sql = "SELECT id, usuario_id, categoria_id, titulo, descripcion, estado FROM ideas_negocio WHERE id = ?";
        IdeaNegocio idea = null;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idea = new IdeaNegocio();
                    idea.setId(rs.getInt("id"));
                    idea.setUsuarioId(rs.getInt("usuario_id"));
                    idea.setCategoriaId(rs.getInt("categoria_id"));
                    idea.setTitulo(rs.getString("titulo"));
                    idea.setDescripcion(rs.getString("descripcion"));
                    idea.setEstado(rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idea;
    }

    public static boolean crear(IdeaNegocio idea) {
        String sql = "INSERT INTO ideas_negocio (usuario_id, categoria_id, titulo, descripcion, estado) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idea.getUsuarioId());
            ps.setInt(2, idea.getCategoriaId());
            ps.setString(3, idea.getTitulo());
            ps.setString(4, idea.getDescripcion());
            ps.setString(5, idea.getEstado());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idea.setId(rs.getInt(1));  // Asignar el id generado
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean editar(IdeaNegocio idea) {
        String sql = "UPDATE ideas_negocio SET usuario_id = ?, categoria_id = ?, titulo = ?, descripcion = ?, estado = ? WHERE id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idea.getUsuarioId());
            ps.setInt(2, idea.getCategoriaId());
            ps.setString(3, idea.getTitulo());
            ps.setString(4, idea.getDescripcion());
            ps.setString(5, idea.getEstado());
            ps.setInt(6, idea.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean eliminar(int id) {
        String sql = "DELETE FROM ideas_negocio WHERE id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Resultados asociados a una idea específica
    public static List<String> obtenerResultadosPorIdea(int ideaId) {
        List<String> resultados = new ArrayList<>();
        String sql = """
            SELECT r.id AS resultado_id, r.usuario_id, r.estadisticas_id
            FROM resultados r
            JOIN estadisticas_idea ei ON r.estadisticas_id = ei.id
            JOIN mentorias m ON ei.mentoria_id = m.id
            WHERE m.idea_id = ?
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ideaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("resultado_id");
                    int userId = rs.getInt("usuario_id");
                    int estadisticasId = rs.getInt("estadisticas_id");
                    resultados.add("Resultado #" + id + " - Usuario ID: " + userId + " - Estadística ID: " + estadisticasId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultados.add("Error al obtener resultados.");
        }
        return resultados;
    }

    // Estadísticas asociadas a una idea
    public static List<String> obtenerEstadisticasPorIdea(int ideaId) {
        List<String> estadisticas = new ArrayList<>();
        String sql = """
            SELECT ei.id, ei.fase, ei.mentoria_id
            FROM estadisticas_idea ei
            JOIN mentorias m ON ei.mentoria_id = m.id
            WHERE m.idea_id = ?
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ideaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int estadisticaId = rs.getInt("id");
                    int faseId = rs.getInt("fase");
                    int mentoriaId = rs.getInt("mentoria_id");

                    estadisticas.add("Estadística ID: " + estadisticaId +
                            ", Fase ID: " + faseId +
                            ", Mentoria ID: " + mentoriaId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            estadisticas.add("Error al obtener estadísticas.");
        }

        return estadisticas;
    }

    // Ideas asignadas a un mentor
    public static List<IdeaNegocio> obtenerIdeasAsignadasAlMentor(int mentorId) {
        List<IdeaNegocio> lista = new ArrayList<>();
        String sql = """
            SELECT i.id, i.usuario_id, i.categoria_id, i.titulo, i.descripcion, i.estado
            FROM ideas_negocio i
            INNER JOIN mentorias m ON i.id = m.idea_id
            WHERE m.mentor_id = ?
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mentorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    IdeaNegocio idea = new IdeaNegocio();
                    idea.setId(rs.getInt("id"));
                    idea.setUsuarioId(rs.getInt("usuario_id"));
                    idea.setCategoriaId(rs.getInt("categoria_id"));
                    idea.setTitulo(rs.getString("titulo"));
                    idea.setDescripcion(rs.getString("descripcion"));
                    idea.setEstado(rs.getString("estado"));
                    lista.add(idea);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
