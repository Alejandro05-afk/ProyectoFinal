package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionRailway {
    private static final String URL = "jdbc:postgresql://crossover.proxy.rlwy.net:50424/railway";
    private static final String USER = "postgres";
    private static final String PASSWORD = "clKdPZgGHtQRvPFVnhTcHDTclHnlPBpM";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
