package DAO;

import Conexion.ConexionRailway;
import Modelo.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonaDAO {

    /**
     * Inserta una nueva persona en la base de datos y retorna el ID generado.
     * @param persona objeto Persona con los datos a insertar.
     * @return el ID generado si la inserción fue exitosa, o -1 si hubo error.
     */
    public int insertarPersona(Persona persona) {
        int idGenerado = -1;  // Inicializamos el ID con -1 (valor de error)

        // Sentencia SQL con RETURNING para obtener el ID generado al insertar
        String sql = """
            INSERT INTO personas (nombres, apellidos, correo, telefono, direccion)
            VALUES (?, ?, ?, ?, ?) RETURNING id;
        """;

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Asignamos los parámetros para la consulta preparada en orden
            ps.setString(1, persona.getNombres());
            ps.setString(2, persona.getApellidos());
            ps.setString(3, persona.getCorreo());
            ps.setString(4, persona.getTelefono());
            ps.setString(5, persona.getDireccion());

            // Ejecutamos la consulta y obtenemos el ResultSet con el ID generado
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idGenerado = rs.getInt("id");  // Extraemos el ID generado
            }

        } catch (Exception e) {
            e.printStackTrace();  // Imprime la traza de error en caso de excepción
        }

        return idGenerado;  // Retornamos el ID generado o -1 si hubo error
    }

    /**
     * Busca el ID de una persona en la base de datos según su correo electrónico.
     * @param correo correo electrónico de la persona.
     * @return el ID si se encontró la persona, o -1 si no existe o hubo error.
     */
    public int obtenerIdPorCorreo(String correo) {
        String sql = "SELECT id FROM personas WHERE correo = ?";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);  // Asignamos el parámetro correo a la consulta
            ResultSet rs = ps.executeQuery();  // Ejecutamos la consulta

            if (rs.next()) {
                return rs.getInt("id");  // Retornamos el ID encontrado
            }

        } catch (Exception e) {
            e.printStackTrace();  // Imprime la traza de error si ocurre
        }

        return -1;  // Retornamos -1 si no se encontró o hubo error
    }
}
