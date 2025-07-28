package Vista;

import Controlador.RegistroController;

import javax.swing.*;

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

    public CrearCuentaDesdeAdmin(int usuarioId) {
        this.usuarioId = usuarioId;

        setContentPane(CrearCuenta);
        setTitle("Crear Cuenta");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        registrarButton.addActionListener(e -> {
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

            // Llamada a controlador (no devuelve boolean ni lanza excepciones, muestra mensajes ahí)
            RegistroController.registrarCuenta(nombre, apellido, correo, telefono, direccion, contra, rol);

            // Limpiar campos siempre después del intento de registro (puedes cambiar si quieres)
            textFieldNombre.setText("");
            textFieldApellido.setText("");
            textFieldCorreo.setText("");
            textFieldTelefono.setText("");
            textFieldDireccion.setText("");
            passwordFieldContra.setText("");
            comboBoxRol.setSelectedIndex(0);
        });

        regresarButton.addActionListener(e -> {
            dispose();
            new DashboardAdmin(usuarioId);
        });

        setVisible(true);
    }
}
