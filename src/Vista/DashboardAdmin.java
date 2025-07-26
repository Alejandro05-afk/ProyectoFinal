package Vista;

import Controlador.IdeaNegocioController;
import Controlador.MentoriaController;
import Controlador.UsuarioController;
import Modelo.IdeaNegocio;
import Modelo.Persona;
import Modelo.UsuarioCompleto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DashboardAdmin extends JFrame {
    private JPanel panelAdmin;
    private JTabbedPane tabbedPane1;
    private JTable tableUsuarios;
    private JButton buscarButton;
    private JButton crearButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JTable tableIdeas;
    private JButton buscarButton1;
    private JButton eliminarButton1;
    private JButton aprobarButton;
    private JList<String> listLogs;
    private JButton resultadosButton;
    private JButton estadisticasButton;
    private JButton salirButton;

    // Crea instancias de los controladores
    private UsuarioController usuarioController = new UsuarioController();
    private IdeaNegocioController ideaNegocioController = new IdeaNegocioController();
    private MentoriaController mentoriaController = new MentoriaController();

    public DashboardAdmin() {
        setContentPane(panelAdmin);
        setTitle("Administrador");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Cargar datos al iniciar
        cargarUsuarios();
        cargarIdeas();
        cargarLogs();

        // ------------------ EVENTOS ------------------

        crearButton.addActionListener(e -> new CrearCuenta());

        buscarButton.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Nombre:");
            UsuarioCompleto u = usuarioController.buscarPorNombreConRol(nombre);
            if (u != null) {
                DefaultTableModel model = new DefaultTableModel(
                        new Object[]{"ID", "Nombres", "Apellidos", "Correo", "Rol"}, 0);
                model.addRow(new Object[]{u.getPersonaId(), u.getNombres(), u.getApellidos(), u.getCorreo(), u.getRol()});
                tableUsuarios.setModel(model);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            }
        });


        editarButton.addActionListener(e -> {
            int row = tableUsuarios.getSelectedRow();
            if (row >= 0) {
                int id = Integer.parseInt(tableUsuarios.getValueAt(row, 0).toString());
                String campo = JOptionPane.showInputDialog("Campo a editar:");
                String valor = JOptionPane.showInputDialog("Nuevo valor:");
                usuarioController.editarCampo(id, campo, valor);
                cargarUsuarios();
            }
        });

        eliminarButton.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID a eliminar:"));
            usuarioController.eliminar(id);
            cargarUsuarios();
        });

        buscarButton1.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID de la idea:"));
            IdeaNegocio idea = ideaNegocioController.buscarPorId(id);
            if (idea != null) {
                DefaultTableModel model = new DefaultTableModel(
                        new Object[]{"ID", "Usuario", "Categoría", "Título", "Descripción", "Estado"}, 0);
                model.addRow(new Object[]{
                        idea.getId(),
                        idea.getUsuarioId(),
                        idea.getCategoriaId(),
                        idea.getTitulo(),
                        idea.getDescripcion(),
                        idea.getEstado()
                });
                tableIdeas.setModel(model);
            } else {
                JOptionPane.showMessageDialog(null, "Idea no encontrada");
            }
        });

        resultadosButton.addActionListener(e -> {
            int row = tableIdeas.getSelectedRow();
            if (row >= 0) {
                int ideaId = Integer.parseInt(tableIdeas.getValueAt(row, 0).toString());
                List<String> resultados = ideaNegocioController.obtenerResultados(ideaId);
                JOptionPane.showMessageDialog(null, String.join("\n", resultados));
            }
        });

        estadisticasButton.addActionListener(e -> {
            int row = tableIdeas.getSelectedRow();
            if (row >= 0) {
                int ideaId = Integer.parseInt(tableIdeas.getValueAt(row, 0).toString());
                List<String> stats = ideaNegocioController.obtenerEstadisticas(ideaId);
                JOptionPane.showMessageDialog(null, String.join("\n", stats));
            }
        });

        aprobarButton.addActionListener(e -> {
            int row = tableIdeas.getSelectedRow();
            if (row >= 0) {
                int ideaId = Integer.parseInt(tableIdeas.getValueAt(row, 0).toString());
                List<String> mentores = mentoriaController.obtenerMentores();
                String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un mentor:",
                        "Mentores", JOptionPane.QUESTION_MESSAGE, null,
                        mentores.toArray(), mentores.get(0));
                if (seleccion != null) {
                    int mentorId = Integer.parseInt(seleccion.split("-")[0].trim());
                    mentoriaController.asignar(ideaId, mentorId);
                    cargarIdeas();
                }
            }
        });

        eliminarButton1.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID de idea a eliminar:"));
            ideaNegocioController.eliminar(id);
            cargarIdeas();
        });

        salirButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void cargarUsuarios() {
        List<UsuarioCompleto> usuarios = usuarioController.listarUsuariosConRol();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nombres", "Apellidos", "Correo", "Rol"}, 0);
        for (UsuarioCompleto u : usuarios) {
            model.addRow(new Object[]{
                    u.getPersonaId(),
                    u.getNombres(),
                    u.getApellidos(),
                    u.getCorreo(),
                    u.getRol()
            });
        }
        tableUsuarios.setModel(model);
    }


    private void cargarIdeas() {
        List<IdeaNegocio> ideas = ideaNegocioController.listarIdeas();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Usuario", "Categoría", "Título", "Descripción", "Estado"}, 0);
        for (IdeaNegocio idea : ideas) {
            model.addRow(new Object[]{
                    idea.getId(),
                    idea.getUsuarioId(),
                    idea.getCategoriaId(),
                    idea.getTitulo(),
                    idea.getDescripcion(),
                    idea.getEstado()
            });
        }
        tableIdeas.setModel(model);
    }

    private void cargarLogs() {
        DefaultListModel<String> model = new DefaultListModel<>();
        List<String> logs = ideaNegocioController.obtenerLogs();
        for (String log : logs) {
            model.addElement(log);
        }
        listLogs.setModel(model);
    }
}
