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
                    idea.setId(rs.getInt(1)); // Asignar el ID generado al objeto
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

            int filas = ps.executeUpdate();
            return filas > 0;

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
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para obtener resultados (enlaces, documentos, etc.) de una idea
    public static List<String> obtenerResultadosPorIdea(int ideaId) {
        List<String> resultados = new ArrayList<>();
        String sql = "SELECT tipo_resultado, enlace FROM resultados WHERE idea_id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ideaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo_resultado");
                    String enlace = rs.getString("enlace");
                    resultados.add(tipo + ": " + enlace);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }
    public static List<String> obtenerEstadisticasPorIdea(int ideaId) {
        List<String> estadisticas = new ArrayList<>();
        String sql = "SELECT visitas, evaluaciones_positivas, evaluaciones_negativas FROM estadisticas_idea WHERE idea_id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ideaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    estadisticas.add("Visitas: " + rs.getInt("visitas"));
                    estadisticas.add("Evaluaciones Positivas: " + rs.getInt("evaluaciones_positivas"));
                    estadisticas.add("Evaluaciones Negativas: " + rs.getInt("evaluaciones_negativas"));
                } else {
                    estadisticas.add("No hay estadísticas disponibles para esta idea.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            estadisticas.add("Error al obtener estadísticas.");
        }

        return estadisticas;
    }

}
