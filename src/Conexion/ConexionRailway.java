package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de establecer la conexión con la base de datos PostgreSQL alojada en Railway.
 */
public class ConexionRailway {

    /** URL de conexión a la base de datos. */
    private static final String URL = "jdbc:postgresql://crossover.proxy.rlwy.net:50424/railway";

    /** Usuario de la base de datos. */
    private static final String USER = "postgres";

    /** Contraseña del usuario de la base de datos. */
    private static final String PASSWORD = "clKdPZgGHtQRvPFVnhTcHDTclHnlPBpM";

    /**
     * Establece y devuelve una conexión a la base de datos.
     *
     * @return una instancia de {@link Connection} si la conexión fue exitosa; {@code null} si ocurrió un error.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
