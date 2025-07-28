package Vista;

import Controlador.RegistroController;

import javax.swing.*;

/**
 * Ventana para que un administrador pueda crear nuevas cuentas de usuario.
 * Incluye validación básica de campos obligatorios y limpia los campos después del registro.
 */
public class CrearCuentaDesdeAdmin extends JFrame {
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

    private int usuarioId;

    /**
     * Constructor que inicializa la ventana con la información del usuario administrador que crea la cuenta.
     * Configura los botones para registrar un nuevo usuario y regresar al dashboard del administrador.
     *
     * @param usuarioId ID del usuario administrador que está creando la cuenta.
     */
    public CrearCuentaDesdeAdmin(int usuarioId) {
        this.usuarioId = usuarioId;

        setContentPane(CrearCuenta);
        setTitle("Crear Cuenta");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        registrarButton.addActionListener(e -> {
            // Obtener datos ingresados y validar campos obligatorios
            String nombre = textFieldNombre.getText().trim();
            String apellido = textFieldApellido.getText().trim();
            String correo = textFieldCorreo.getText().trim();
            String telefono = textFieldTelefono.getText().trim();
            String direccion = textFieldDireccion.getText().trim();
            String contra = new String(passwordFieldContra.getPassword());
            String rol = comboBoxRol.getSelectedItem().toString();

            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete los campos obligatorios.");
                return;
            }

            // Registrar la cuenta usando el controlador
            RegistroController.registrarCuenta(nombre, apellido, correo, telefono, direccion, contra, rol);

            // Limpiar campos luego del intento de registro
            textFieldNombre.setText("");
            textFieldApellido.setText("");
            textFieldCorreo.setText("");
            textFieldTelefono.setText("");
            textFieldDireccion.setText("");
            passwordFieldContra.setText("");
            comboBoxRol.setSelectedIndex(0);
        });

        regresarButton.addActionListener(e -> {
            // Cierra esta ventana y abre el dashboard del administrador
            dispose();
            new DashboardAdmin(usuarioId);
        });

        setVisible(true);
    }
}
