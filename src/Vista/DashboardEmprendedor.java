package Vista;

import Controlador.IdeaNegocioController; // Asegúrate de tener un controlador adecuado
import Modelo.IdeaNegocio; // Asegúrate de que esta clase exista

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardEmprendedor extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelEmprendedor;
    private JTable tableMisIdeas;
    private JButton registrarButton; // Corrige el nombre a "registrarButton"
    private JButton eliminarButton;
    private JButton agregarResultadosButton;
    private JTextArea textAreaMentorias;
    private JButton verObservacionesButton;
    private JButton guardarButton;
    private JComboBox<String> comboBoxFace; // Asegúrate de definir el tipo genérico
    private JTextField textFieldID;
    private JTextField textFieldPorcentaje;
    private JTextField textFechaMentoria;
    private JButton salirButton;

    public DashboardEmprendedor() {
        setContentPane(panelEmprendedor);
        setTitle("Emprendedor");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Cargar ideas en la tabla
        cargarMisIdeas();

        // Agregar listeners a los botones
        agregarListeners();

        setVisible(true);
    }

    private void agregarListeners() {
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar la lógica para registrar una nueva idea
                String idStr = textFieldID.getText();
                String porcentajeStr = textFieldPorcentaje.getText();
                // Aquí puedes agregar más lógica para manejar la entrada
                // Por ejemplo, validaciones y llamada al controlador
                JOptionPane.showMessageDialog(DashboardEmprendedor.this, "Registro exitoso!");
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableMisIdeas.getSelectedRow();
                if (selectedRow != -1) {
                    int ideaId = (int) tableMisIdeas.getValueAt(selectedRow, 0); // Suponiendo que la ID está en la primera columna
                    boolean eliminado = IdeaNegocioController.eliminar(ideaId); // Llama al controlador para eliminar
                    if (eliminado) {
                        JOptionPane.showMessageDialog(DashboardEmprendedor.this, "Idea eliminada con éxito.");
                        cargarMisIdeas(); // Recargar ideas después de eliminar
                    } else {
                        JOptionPane.showMessageDialog(DashboardEmprendedor.this, "Error al eliminar la idea.");
                    }
                } else {
                    JOptionPane.showMessageDialog(DashboardEmprendedor.this, "Seleccione una idea para eliminar.");
                }
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });
    }

    private void cargarMisIdeas() {
        List<IdeaNegocio> ideas = IdeaNegocioController.listarIdeas(); // Llama al controlador para obtener las ideas
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Título", "Descripción", "Estado"}, 0);
        for (IdeaNegocio idea : ideas) {
            model.addRow(new Object[]{
                    idea.getId(),
                    idea.getTitulo(),
                    idea.getDescripcion(),
                    idea.getEstado()
            });
        }
        tableMisIdeas.setModel(model);
    }
}
