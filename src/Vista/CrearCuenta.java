package Vista;

import Controlador.RegistroController;

import javax.swing.*;

/**
 * Ventana para crear una nueva cuenta de usuario.
 * Permite ingresar datos personales, contraseña y seleccionar un rol.
 */
public class CrearCuenta extends JFrame {
    private JButton registrarButton;
    private JButton regresarButton;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldCorreo;
    private JTextField textFieldTelefono;
    private JTextField textFieldDireccion;
    private JComboBox comboBoxRol; // rol: Administrador, Mentor, Emprendedor
    private JPasswordField passwordFieldContra;
    private JPanel CrearCuenta;

    /**
     * Constructor que inicializa la ventana y configura los eventos.
     */
    public CrearCuenta() {
        setContentPane(CrearCuenta);
        setTitle("Crear Cuenta");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Acción al hacer clic en registrarButton
        registrarButton.addActionListener(e -> {
            // Llamar al controlador para registrar los datos ingresados
            RegistroController.registrarCuenta(
                    textFieldNombre.getText(),
                    textFieldApellido.getText(),
                    textFieldCorreo.getText(),
                    textFieldTelefono.getText(),
                    textFieldDireccion.getText(),
                    new String(passwordFieldContra.getPassword()),
                    comboBoxRol.getSelectedItem().toString()
            );

            // Limpiar los campos después del registro
            textFieldNombre.setText("");
            textFieldApellido.setText("");
            textFieldCorreo.setText("");
            textFieldTelefono.setText("");
            textFieldDireccion.setText("");
            passwordFieldContra.setText("");
            comboBoxRol.setSelectedIndex(0);
        });

        // Acción al hacer clic en regresarButton: cerrar esta ventana y abrir Login
        regresarButton.addActionListener(e -> {
            dispose();
            new Login();
        });
    }
}
