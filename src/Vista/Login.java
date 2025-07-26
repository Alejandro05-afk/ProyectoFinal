package Vista;

import Controlador.LoginController;

import javax.swing.*;

public class Login extends JFrame {
    private JTextField textFieldUsuario; // correo
    private JPasswordField passwordField; // contraseña
    private JComboBox comboBoxRol; // rol
    private JButton ingresarButton;
    private JButton crearCuentaButton;
    private JPanel Login;

    public Login() {
        setContentPane(Login);
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        ingresarButton.addActionListener(e -> {
            String correo = textFieldUsuario.getText();
            String contraseña = new String(passwordField.getPassword());
            String rol = comboBoxRol.getSelectedItem().toString();

            LoginController.iniciarSesion(correo, contraseña, rol);
            dispose();
        });

        crearCuentaButton.addActionListener(e -> {
            dispose();
            new CrearCuenta();
        });
    }
}
