package Vista;

import Controlador.IdeaNegocioController;
import Controlador.MentoriaController;
import Controlador.UsuarioController;
import DAO.LogsSistemaDAO;
import Modelo.IdeaNegocio;
import Modelo.UsuarioCompleto;
import Controlador.LogSistemaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Ventana principal para el rol Administrador.
 * Contiene pestañas para gestión de usuarios, ideas y visualización de logs del sistema.
 */
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
    private JScrollPane ScrollTableUsuarios;
    private int usuarioActualId;

    // Instancias de controladores
    private UsuarioController usuarioController = new UsuarioController();
    private IdeaNegocioController ideaNegocioController = new IdeaNegocioController();
    private MentoriaController mentoriaController = new MentoriaController();

    /**
     * Constructor que inicializa el dashboard del administrador y configura los eventos.
     * @param usuarioId ID del usuario administrador actual (para logs y navegación).
     */
    public DashboardAdmin(int usuarioId) {
        this.usuarioActualId = usuarioId;

        setContentPane(panelAdmin);
        setTitle("Administrador");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Carga inicial de datos
        cargarUsuarios();
        cargarIdeas();
        cargarLogs();

        // Configuración de eventos
        configurarEventos();

        setVisible(true);
    }

    /**
     * Configura todos los ActionListener de los botones y componentes interactivos.
     */
    private void configurarEventos() {
        crearButton.addActionListener(e -> {
            new CrearCuentaDesdeAdmin(usuarioActualId);
            dispose();
        });

        buscarButton.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Nombre:");
            List<UsuarioCompleto> usuarios = usuarioController.buscarPorNombreConRol(nombre);
            if (usuarios != null && !usuarios.isEmpty()) {
                DefaultTableModel model = new DefaultTableModel(
                        new Object[]{"ID", "Nombres", "Apellidos", "Correo", "Rol"}, 0);
                for (UsuarioCompleto u : usuarios) {
                    model.addRow(new Object[]{u.getPersonaId(), u.getNombres(), u.getApellidos(), u.getCorreo(), u.getRol()});
                }
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
            int row = tableUsuarios.getSelectedRow();
            if (row >= 0) {
                int personaId = Integer.parseInt(tableUsuarios.getValueAt(row, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas eliminar este usuario?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    usuarioController.eliminarUsuario(personaId);
                    LogsSistemaDAO.insertarLog(usuarioActualId, "Eliminó usuario con persona_id = " + personaId);
                    cargarUsuarios();
                    JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un usuario para eliminar.");
            }
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

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas salir?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    new Login();
                    dispose();
                }
            }
        });
    }

    /**
     * Carga y muestra todos los usuarios completos (con rol) en la tabla de usuarios.
     */
    private void cargarUsuarios() {
        List<UsuarioCompleto> usuarios = usuarioController.listarUsuariosConRol();

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Nombres", "Apellidos", "Correo", "Teléfono", "Dirección", "Rol"});

        for (UsuarioCompleto u : usuarios) {
            model.addRow(new Object[]{
                    u.getId(),
                    u.getNombres(),
                    u.getApellidos(),
                    u.getCorreo(),
                    u.getTelefono(),
                    u.getDireccion(),
                    u.getRol()
            });
        }

        tableUsuarios.setModel(model);
    }

    /**
     * Carga y muestra todas las ideas de negocio en la tabla de ideas.
     */
    private void cargarIdeas() {
        String[] columnas = {"ID", "Usuario", "Categoría", "Título", "Descripción", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        List<IdeaNegocio> ideas = IdeaNegocioController.listarIdeas();

        for (IdeaNegocio idea : ideas) {
            Object[] fila = {
                    idea.getId(),
                    idea.getUsuarioId(),
                    idea.getCategoriaId(),
                    idea.getTitulo(),
                    idea.getDescripcion(),
                    idea.getEstado()
            };
            model.addRow(fila);
        }

        tableIdeas.setModel(model);
    }

    /**
     * Carga y muestra los logs del sistema en el componente JList.
     */
    private void cargarLogs() {
        List<String> logs = LogSistemaController.obtenerLogsComoTexto();

        DefaultListModel<String> model = new DefaultListModel<>();
        for (String log : logs) {
            model.addElement(log);
        }

        listLogs.setModel(model);
        listLogs.repaint();
        listLogs.revalidate();
    }
}
