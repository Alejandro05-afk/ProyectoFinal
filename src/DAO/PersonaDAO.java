package DAO;

import Conexion.ConexionRailway;
import Modelo.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonaDAO {

    public int insertarPersona(Persona persona) {
        int idGenerado = -1;
        String sql = """
            INSERT INTO personas (nombres, apellidos,correo, telefono, direccion)
            VALUES (?, ?, ?, ?, ?) RETURNING id;
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, persona.getNombres());
            ps.setString(2, persona.getApellidos());
            ps.setString(3, persona.getCorreo());
            ps.setString(4, persona.getTelefono());
            ps.setString(5, persona.getDireccion());


            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idGenerado = rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    public int obtenerIdPorCorreo(String correo) {
        String sql = "SELECT id FROM personas WHERE correo = ?";
        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
