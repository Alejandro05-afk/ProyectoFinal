package DAO;

import Conexion.ConexionRailway;
import Modelo.Persona;
import Modelo.Usuario;
import Modelo.UsuarioCompleto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Insertar un nuevo usuario (en tabla usuarios)
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (persona_id, contraseña, tipo_usuario) VALUES (?, ?, ?)";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuario.getPersonaId());
            ps.setString(2, usuario.getContraseña());
            ps.setString(3, usuario.getRol());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verificar login (correo, contraseña y rol)
    public Usuario verificarLogin(String correo, String contraseña, String rol) {
        Usuario usuario = null;

        String sql = """
            SELECT u.id, u.persona_id, u.contraseña, u.tipo_usuario
            FROM usuarios u
            JOIN personas p ON u.persona_id = p.id
            WHERE p.correo = ?
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String contraseñaBD = rs.getString("contraseña");
                    String rolBD = rs.getString("tipo_usuario");

                    if (contraseña.equals(contraseñaBD) && rol.equalsIgnoreCase(rolBD)) {
                        usuario = new Usuario();
                        usuario.setId(rs.getInt("id"));
                        usuario.setPersonaId(rs.getInt("persona_id"));
                        usuario.setContraseña(contraseñaBD);
                        usuario.setRol(rolBD);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    // Obtener usuario por personaId (para obtener rol y contraseña)
    public Usuario obtenerPorPersonaId(int personaId) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE persona_id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setPersonaId(rs.getInt("persona_id"));
                    usuario.setContraseña(rs.getString("contraseña"));
                    usuario.setRol(rs.getString("tipo_usuario"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    // Listar personas (sin rol)
    public List<Persona> listarUsuarios() {
        List<Persona> usuarios = new ArrayList<>();

        String sql = """
            SELECT p.id, p.nombres, p.apellidos, p.correo, p.telefono, p.direccion
            FROM personas p
            JOIN usuarios u ON p.id = u.persona_id
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona persona = new Persona();
                persona.setId(rs.getInt("id"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setCorreo(rs.getString("correo"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setDireccion(rs.getString("direccion"));
                usuarios.add(persona);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    // Buscar persona por nombre (sin rol)
    public Persona buscarPorNombre(String nombre) {
        String sql = """
            SELECT p.id, p.nombres, p.apellidos, p.correo, p.telefono, p.direccion
            FROM personas p
            JOIN usuarios u ON p.id = u.persona_id
            WHERE LOWER(p.nombres) LIKE LOWER(?)
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setId(rs.getInt("id"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApellidos(rs.getString("apellidos"));
                    persona.setCorreo(rs.getString("correo"));
                    persona.setTelefono(rs.getString("telefono"));
                    persona.setDireccion(rs.getString("direccion"));
                    return persona;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Buscar persona con rol por nombre (solo devuelve 1 resultado)
    public static List<UsuarioCompleto> buscarPorNombreConRol(String nombre) {
        List<UsuarioCompleto> lista = new ArrayList<>();

        String sql = "SELECT u.id, u.persona_id, p.nombres, p.apellidos, p.correo, p.telefono, p.direccion, u.tipo_usuario " +
                "FROM usuarios u " +
                "JOIN personas p ON u.persona_id = p.id " +
                "WHERE p.nombres ILIKE ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UsuarioCompleto usuario = new UsuarioCompleto(
                        rs.getInt("id"),
                        rs.getInt("persona_id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("tipo_usuario")
                );
                lista.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    // Editar un campo (puede ser en personas o usuarios)
    public void editarCampo(int personaId, String campo, String nuevoValor) {
        String sql;
        if (campo.equalsIgnoreCase("contraseña") || campo.equalsIgnoreCase("tipo_usuario")) {
            sql = "UPDATE usuarios SET " + campo + " = ? WHERE persona_id = ?";
        } else {
            sql = "UPDATE personas SET " + campo + " = ? WHERE id = ?";
        }

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoValor);
            ps.setInt(2, personaId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar usuario (de ambas tablas)
    public void eliminarUsuario(int personaId) {
        String sqlUsuarios = "DELETE FROM usuarios WHERE persona_id = ?";
        String sqlPersonas = "DELETE FROM personas WHERE id = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sqlUsuarios);
             PreparedStatement ps2 = conn.prepareStatement(sqlPersonas)) {

            ps1.setInt(1, personaId);
            ps1.executeUpdate();

            ps2.setInt(1, personaId);
            ps2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Listar usuarios completos (persona + rol)
    public List<UsuarioCompleto> listarUsuariosConRol() {
        List<UsuarioCompleto> lista = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nombres, p.apellidos, p.correo, p.telefono, p.direccion, u.tipo_usuario
            FROM personas p
            JOIN usuarios u ON p.id = u.persona_id
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UsuarioCompleto u = new UsuarioCompleto(
                        rs.getInt("id"),
                        rs.getInt("id"),  // personaId igual al id persona
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("tipo_usuario")
                );
                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
