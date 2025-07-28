package Vista;

import Controlador.EstadisticaController;
import Controlador.IdeaNegocioController;
import Controlador.ObservacionController;
import Modelo.FaseProyecto;
import Modelo.IdeaNegocio;
import Modelo.Mentoria;
import Modelo.Observacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.util.List;

public class DashboardEmprendedor extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelEmprendedor;
    private JTable tableMisIdeas;
    private JButton eliminarButton;
    private JButton verObservacionesButton;
    private JButton guardarButton;
    private JComboBox<String> comboBoxFase;
    private JTextField textFieldID;
    private JTextField textFieldPorcentaje;
    private JTextField textFechaMentoria;
    private JButton salirbutton;
    private JList<String> listMentorias;
    private JButton registrarNuevaIdeaButton;
    private JButton agragarEstadisticasButton;
    private JTextField textFieldFecha;

    private int usuarioId;

    public DashboardEmprendedor(int usuarioId) {
        this.usuarioId = usuarioId;

        setContentPane(panelEmprendedor);
        setTitle("Dashboard Emprendedor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cargarMisIdeas();
        cargarComboFases();
        cargarMentorias();
        agregarListeners();

        setVisible(true);
    }

    private void agregarListeners() {
        guardarButton.addActionListener(e -> {
            try {
                int ideaId = Integer.parseInt(textFieldID.getText());
                int porcentaje = Integer.parseInt(textFieldPorcentaje.getText());
                String fechaBase = textFieldFecha.getText(); // Debe ser: yyyy-MM-dd

                // Validar que no esté vacío
                if (fechaBase == null || fechaBase.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese una fecha.");
                    return;
                }

                String fecha = fechaBase + " 00:00:00"; // Añade hora fija

                String faseSeleccionada = (String) comboBoxFase.getSelectedItem();

                if (faseSeleccionada == null || faseSeleccionada.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Seleccione una fase.");
                    return;
                }

                int faseId = EstadisticaController.obtenerFaseIdPorNombre(faseSeleccionada);
                if (faseId == -1) {
                    JOptionPane.showMessageDialog(this, "Fase no válida.");
                    return;
                }

                boolean registrado = EstadisticaController.registrarAvance(ideaId, faseId, porcentaje, fecha);
                if (registrado) {
                    JOptionPane.showMessageDialog(this, "Avance registrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar avance.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.");
            }
        });


        verObservacionesButton.addActionListener(e -> {
            String fechaStr = textFechaMentoria.getText();
            if (fechaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese la fecha de la mentoría.");
                return;
            }

            try {
                Date fecha = Date.valueOf(fechaStr); // formato yyyy-MM-dd
                List<Observacion> observaciones = ObservacionController.listarPorFecha(fecha);
                if (observaciones.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontró una observación para esa fecha.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Observacion o : observaciones) {
                        sb.append("- ").append(o.getComentario()).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, "Observaciones:\n" + sb);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Fecha inválida. Use formato yyyy-MM-dd");
            }
        });

        registrarNuevaIdeaButton.addActionListener(e -> {
            CrearIdeaNegocio crear = new CrearIdeaNegocio(usuarioId, this::cargarMisIdeas);
            crear.setVisible(true);
        });

        eliminarButton.addActionListener(e -> {
            int selectedRow = tableMisIdeas.getSelectedRow();
            if (selectedRow != -1) {
                int ideaId = (int) tableMisIdeas.getValueAt(selectedRow, 0);
                boolean eliminado = IdeaNegocioController.eliminar(ideaId);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Idea eliminada con éxito.");
                    cargarMisIdeas();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la idea.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una idea para eliminar.");
            }
        });

        agragarEstadisticasButton.addActionListener(e -> asignarFase());


        salirbutton.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro que desea salir?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                dispose(); // Cierra el DashboardEmprendedor (o el JFrame actual)
                new Login(); // Abre la ventana de Login (asegúrate que Login() sea el constructor que muestra el formulario)
            }
        });

    }

    private void cargarMisIdeas() {
        List<IdeaNegocio> ideas = IdeaNegocioController.listarIdeasPorUsuario(usuarioId);
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

    private void cargarComboFases() {
        List<FaseProyecto> fases = EstadisticaController.listarFases();
        comboBoxFase.removeAllItems();
        for (FaseProyecto f : fases) {
            comboBoxFase.addItem(f.getNombre());
        }
    }

    private void cargarMentorias() {
        List<Mentoria> mentorias = EstadisticaController.listarMentoriasPorUsuario(usuarioId);
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Mentoria mentoria : mentorias) {
            model.addElement("ID: " + mentoria.getId() + ", Fecha: " + mentoria.getFecha());
        }
        listMentorias.setModel(model);
    }

    private void asignarFase() {
        int selectedIndex = listMentorias.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mentoría.");
            return;
        }

        List<Mentoria> mentorias = EstadisticaController.listarMentoriasPorUsuario(usuarioId);
        int mentoriaId = mentorias.get(selectedIndex).getId();

        List<FaseProyecto> fases = EstadisticaController.listarFases();
        if (fases.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay fases registradas en el sistema.");
            return;
        }

        String[] opciones = fases.stream().map(FaseProyecto::getNombre).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione la fase del proyecto:",
                "Fase", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            int faseId = fases.stream()
                    .filter(f -> f.getNombre().equals(seleccion))
                    .findFirst()
                    .get()
                    .getId();

            EstadisticaController.asignarFase(mentoriaId, faseId);
            JOptionPane.showMessageDialog(this, "Fase asignada.");
        }
    }
}
