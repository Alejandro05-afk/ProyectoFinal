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
            Login loginFrame = new Login();
            loginFrame.setTitle("Inicio de Sesión");
            loginFrame.setSize(400, 300);
            loginFrame.setLocationRelativeTo(null); // Centrar ventana
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setVisible(true);
        });
    }
}
