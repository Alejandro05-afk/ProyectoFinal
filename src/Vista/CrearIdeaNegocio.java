package Vista;

import Controlador.IdeaNegocioController;
import Modelo.Categoria;
import Util.Actualizable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CrearIdeaNegocio extends JFrame {
    private JPanel panelCrearIdea;
    private JButton guardarIdeaButton;
    private JTextField textFieldTitulo;
    private JTextField textFieldDescripcion;
    private JComboBox<Categoria> comboBoxCotegorias;

    private int usuarioId;
    private Actualizable callback;

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

    private void cargarCategorias() {
        List<Categoria> categorias = IdeaNegocioController.listarCategorias();
        for (Categoria categoria : categorias) {
            comboBoxCotegorias.addItem(categoria);
        }
    }

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
                    callback.actualizar(); // <---- actualizar tabla en el Dashboard
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CrearIdeaNegocio.this, "Error al registrar la idea.");
                }
            }
        });
    }
}
