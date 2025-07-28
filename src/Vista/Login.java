package Vista;

import Controlador.LoginController;

import javax.swing.*;
/**
 * Clase que representa la ventana de Login para la aplicación.
 * Permite ingresar con correo, contraseña y seleccionar un rol.
 * También brinda opción para crear una nueva cuenta.
 */
public class Login extends JFrame {

    /**
     * Campo de texto para ingresar el correo electrónico del usuario.
     */
    private JTextField textFieldUsuario;

    /**
     * Campo de contraseña para ingresar la contraseña del usuario.
     */
    private JPasswordField passwordField;

    /**
     * ComboBox para seleccionar el rol del usuario (Ej: Administrador, Mentor, Emprendedor).
     */
    private JComboBox comboBoxRol;

    /**
     * Botón para intentar iniciar sesión con las credenciales ingresadas.
     */
    private JButton ingresarButton;

    /**
     * Botón para abrir el formulario de creación de cuenta.
     */
    private JButton crearCuentaButton;

    /**
     * Panel principal que contiene los componentes del Login.
     */
    private JPanel Login;
    /**
     * Constructor que inicializa la interfaz gráfica de login,
     * configura los eventos de los botones y muestra la ventana.
     */
    public Login() {
        setContentPane(Login);
        setTitle("Login");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        ingresarButton.addActionListener(e -> {
            String correo = textFieldUsuario.getText();
            String contraseña = new String(passwordField.getPassword());
            String rol = comboBoxRol.getSelectedItem().toString();

            LoginController.iniciarSesion(correo, contraseña, rol,this);

        });

        crearCuentaButton.addActionListener(e -> {
            dispose();
            new CrearCuenta();
        });
    }
}
