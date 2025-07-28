package DAO;

import Conexion.ConexionRailway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO para gestionar las operaciones relacionadas con los avances de fase.
 */
public class AvanceFaseDAO {

    /**
     * Inserta un nuevo avance de fase en la base de datos y retorna el ID generado.
     *
     * @param ideaId ID de la idea de negocio.
     * @param faceId ID de la fase del proyecto.
     * @param porcentajeAvance Porcentaje de avance registrado.
     * @return ID generado del registro insertado, o -1 si ocurrió un error.
     */
    public static int insertarAvanceFase(int ideaId, int faceId, int porcentajeAvance) {
        String sql = "INSERT INTO avance_fases (idea_id, fase_id, porcentaje_avance) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = ConexionRailway.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ideaId);
            stmt.setInt(2, faceId);
            stmt.setInt(3, porcentajeAvance);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");  // ID generado del avance_fases
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
