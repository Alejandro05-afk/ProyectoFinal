package Vista;

import Controlador.IdeaNegocioController;
import Controlador.MentoriaController;
import Controlador.UsuarioController;
import Modelo.IdeaNegocio;
import Modelo.UsuarioCompleto;
import Controlador.LogSistemaController;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                // Si elige NO, no hace nada
            }
        });


        setVisible(true);
    }

    private void cargarUsuarios() {
        // Crear instancia del controlador
        UsuarioController usuarioController = new UsuarioController();

        // Llamar al método no estático
        List<UsuarioCompleto> usuarios = usuarioController.listarUsuariosConRol();

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Nombres", "Apellidos", "Correo", "Teléfono", "Dirección", "Rol"});

        for (UsuarioCompleto u : usuarios) {
            model.addRow(new Object[]{
                    u.getId(),
                    u.getNombres(),
                    u.getApellidos(),
                    u.getCorreo(),
                    u.getTelefono(),    // Asegúrate que UsuarioCompleto tiene estos campos
                    u.getDireccion(),
                    u.getRol()
            });
        }

        tableUsuarios.setModel(model);
    }



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
