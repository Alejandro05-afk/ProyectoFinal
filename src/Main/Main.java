package Main;

import Vista.Login;

import javax.swing.*;

/**
 * Clase principal que inicia la aplicación.
 */
public class Main {

    /**
     * Método principal que establece el look and feel del sistema y lanza la interfaz de inicio de sesión.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en esta aplicación).
     */
    public static void main(String[] args) {
        // Establece el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mostrar la ventana de Login
        SwingUtilities.invokeLater(() -> {
            new Login();
        });
    }
}
