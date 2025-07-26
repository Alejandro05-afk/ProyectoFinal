package Main;

import Vista.Login;

import javax.swing.*;

public class Main {
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
