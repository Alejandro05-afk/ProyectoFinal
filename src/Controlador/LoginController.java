package Controlador;

import Conexion.ConexionRailway;
import Vista.DashboardAdmin;
import Vista.DashboardEmprendedor;
import Vista.DashboardMentor;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    public static void iniciarSesion(String correo, String contraseña, String rol) {
        try (Connection conn = ConexionRailway.getConnection()) {
            String sql = """
                SELECT u.contraseña, u.tipo_usuario
                FROM usuarios u
                JOIN personas p ON u.persona_id = p.id
                WHERE p.correo = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String contraseñaDB = rs.getString("contraseña");
                String rolDB = rs.getString("tipo_usuario");

                if (contraseña.equals(contraseñaDB) && rol.equalsIgnoreCase(rolDB)) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso como " + rol);
                    lanzarDashboard(rol);
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña o rol incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Correo no registrado", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
        }
    }

    private static void lanzarDashboard(String rol) {
        switch (rol.toLowerCase()) {
            case "administrador" -> new DashboardAdmin();
            case "emprendedor" -> new DashboardEmprendedor();
            case "mentor" -> new DashboardMentor();
            default -> JOptionPane.showMessageDialog(null, "Rol desconocido");
        }
    }
}
