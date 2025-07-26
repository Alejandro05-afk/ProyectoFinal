package Vista;

import Controlador.RegistroController;

import javax.swing.*;

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

    public CrearCuenta() {
        setContentPane(CrearCuenta);
        setTitle("Crear Cuenta");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        registrarButton.addActionListener(e -> {
            // Llamar al controlador para registrar
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
            comboBoxRol.setSelectedIndex(0); // O el índice que prefieras como "Por defecto"
        });


        regresarButton.addActionListener(e -> {
            dispose();
            new Login();
        });
    }
}
