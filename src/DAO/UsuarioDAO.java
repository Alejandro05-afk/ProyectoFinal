package DAO;

import Conexion.ConexionRailway;
import Modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

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

    public Usuario verificarLogin(String correo, String contraseña, String rol) {
        Usuario usuario = null;

        String sql = """
            SELECT u.id, u.persona_id, u.contraseña, u.tipo_usuario
            FROM usuarios u
            JOIN personas p ON u.persona_id = p.id
            WHERE p.correo = ?;
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
