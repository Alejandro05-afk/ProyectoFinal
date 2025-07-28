package Vista;

import Controlador.IdeaNegocioController;
import Modelo.Categoria;
import Util.Actualizable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Ventana para crear una nueva idea de negocio.
 * Permite al usuario seleccionar categoría, ingresar título y descripción.
 * Al guardar, llama al controlador para registrar la idea y notifica a través del callback.
 */
public class CrearIdeaNegocio extends JFrame {
    private JPanel panelCrearIdea;
    private JButton guardarIdeaButton;
    private JTextField textFieldTitulo;
    private JTextField textFieldDescripcion;
    private JComboBox<Categoria> comboBoxCotegorias;

    private int usuarioId;
    private Actualizable callback;

    /**
     * Constructor que inicializa la ventana para crear una nueva idea de negocio.
     *
     * @param usuarioId ID del usuario que crea la idea.
     * @param callback  Interfaz para actualizar la vista (por ejemplo, tabla en dashboard) después de crear la idea.
     */
    public CrearIdeaNegocio(int usuarioId, Actualizable callback) {
        this.usuarioId = usuarioId;
        this.callback = callback;

        setTitle("Crear Nueva Idea");
        setContentPane(panelCrearIdea);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cargarCategorias();
        agregarListeners();

        setVisible(true);
    }

    /**
     * Carga las categorías desde el controlador y las agrega al JComboBox.
     */
    private void cargarCategorias() {
        List<Categoria> categorias = IdeaNegocioController.listarCategorias();
        for (Categoria categoria : categorias) {
            comboBoxCotegorias.addItem(categoria);
        }
    }

    /**
     * Agrega el listener al botón de guardar para validar datos y registrar la idea.
     * En caso de éxito, actualiza la vista vía callback y cierra la ventana.
     */
    private void agregarListeners() {
        guardarIdeaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = textFieldTitulo.getText().trim();
                String descripcion = textFieldDescripcion.getText().trim();
                Categoria categoriaSeleccionada = (Categoria) comboBoxCotegorias.getSelectedItem();

                if (titulo.isEmpty() || descripcion.isEmpty() || categoriaSeleccionada == null) {
                    JOptionPane.showMessageDialog(CrearIdeaNegocio.this, "Todos los campos son obligatorios.");
                    return;
                }

                boolean exito = IdeaNegocioController.registrarIdea(usuarioId, categoriaSeleccionada.getId(), titulo, descripcion);
                if (exito) {
                    JOptionPane.showMessageDialog(CrearIdeaNegocio.this, "Idea registrada correctamente.");
                    callback.actualizar(); // Actualiza tabla en el dashboard
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CrearIdeaNegocio.this, "Error al registrar la idea.");
                }
            }
        });
    }
}
