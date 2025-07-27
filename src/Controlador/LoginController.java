package Controlador;

import Conexion.ConexionRailway;
import Modelo.Persona;
import Modelo.Usuario;
import Vista.DashboardAdmin;
import Vista.DashboardEmprendedor;
import Vista.DashboardMentor;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    // Retorna el usuario autenticado con sus datos completos
    public static Usuario autenticarUsuario(String correo, String contraseña, String rol) {
        String sql = """
        SELECT u.id AS usuario_id, u.contraseña, u.tipo_usuario, p.id AS persona_id, p.nombres, p.apellidos, p.correo
        FROM usuarios u
        JOIN personas p ON u.persona_id = p.id
        WHERE p.correo = ?
    """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String contraseñaDB = rs.getString("contraseña");
                String rolDB = rs.getString("tipo_usuario");

                if (contraseña.equals(contraseñaDB) && rol.equalsIgnoreCase(rolDB)) {
                    Persona persona = new Persona();
                    persona.setId(rs.getInt("persona_id"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApellidos(rs.getString("apellidos"));
                    persona.setCorreo(rs.getString("correo"));

                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("usuario_id"));  // <---- Aquí asignas el ID del usuario
                    usuario.setRol(rolDB);
                    usuario.setPersona(persona);

                    return usuario;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
        }

        return null; // si no autenticó correctamente
    }


    public static void iniciarSesion(String correo, String contraseña, String rol, JFrame loginFrame) {
        if (correo.isEmpty() || contraseña.isEmpty() || rol.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = autenticarUsuario(correo, contraseña, rol);

        if (usuario != null) {
            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso como " + rol);
            loginFrame.dispose(); // Cierra solo el Login
            lanzarDashboard(usuario); // Abre el dashboard correspondiente
        } else {
            JOptionPane.showMessageDialog(null, "Correo, contraseña o rol incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private static void lanzarDashboard(Usuario usuario) {
        switch (usuario.getRol().toLowerCase()) {
            case "administrador" -> new DashboardAdmin().setVisible(true);
            case "emprendedor" -> new DashboardEmprendedor().setVisible(true);
            case "mentor" -> new DashboardMentor(usuario).setVisible(true);  // Aquí pasamos el usuario al constructor
            default -> JOptionPane.showMessageDialog(null, "Rol desconocido");
        }
    }
}
