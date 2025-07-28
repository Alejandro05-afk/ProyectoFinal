package Controlador;

import Conexion.ConexionRailway;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Controlador encargado de registrar nuevas cuentas de usuarios en el sistema.
 */
public class RegistroController {

    /**
     * Registra una nueva cuenta insertando datos en las tablas personas y usuarios.
     *
     * @param nombres Nombres del usuario.
     * @param apellidos Apellidos del usuario.
     * @param correo Correo electrónico.
     * @param telefono Número telefónico.
     * @param direccion Dirección del usuario.
     * @param contraseña Contraseña para la cuenta.
     * @param rol Rol del usuario (tipo_usuario_enum).
     */
    public static void registrarCuenta(String nombres, String apellidos, String correo, String telefono, String direccion,
                                       String contraseña, String rol) {
        try (Connection conn = ConexionRailway.getConnection()) {
            conn.setAutoCommit(false);

            // Insertar en personas
            String sqlPersona = """
                INSERT INTO personas (nombres, apellidos, correo, telefono, direccion)
                VALUES (?, ?, ?, ?, ?) RETURNING id;
            """;

            PreparedStatement psPersona = conn.prepareStatement(sqlPersona);
            psPersona.setString(1, nombres);
            psPersona.setString(2, apellidos);
            psPersona.setString(3, correo);
            psPersona.setString(4, telefono);
            psPersona.setString(5, direccion);

            ResultSet rs = psPersona.executeQuery();
            int personaId = -1;

            if (rs.next()) {
                personaId = rs.getInt("id");
            }

            if (personaId != -1) {
                // Insertar en usuarios
                String sqlUsuario = """
                    INSERT INTO usuarios (persona_id, contraseña, tipo_usuario)
                    VALUES (?, ?, ?::tipo_usuario_enum);
                """;

                PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
                psUsuario.setInt(1, personaId);
                psUsuario.setString(2, contraseña);
                psUsuario.setString(3, rol);
                psUsuario.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Error al registrar persona");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en el registro: " + e.getMessage());
        }
    }
}
