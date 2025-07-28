package Vista;

import Controlador.*;
import DAO.MentorDAO;
import Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DashboardMentor extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelMentor;
    private JTable tableideas;
    private JButton mentoriasButton;
    private JButton salirButton;
    private JButton generarReporteButton;
    private JTable tableMentorias;
    private JButton observacionesButton;
    private JButton estadisticasButton1;
    private JButton resultadosButton;
    private JButton agregarAvanceButton;

    private int mentorId;
    private Usuario mentor;

    public DashboardMentor(Usuario mentor) {
        this.mentor = mentor;
        int personaId = mentor.getPersona().getId();

        Integer mentorIdReal = MentorDAO.obtenerMentorIdPorPersonaId(personaId);
        if (mentorIdReal == null) {
            JOptionPane.showMessageDialog(this, "No se encontró mentor para esta persona.");
            dispose();
            return;
        }

        this.mentorId = mentorIdReal;

        setContentPane(panelMentor);
        setTitle("Mentor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        cargarIdeasAsignadas();
        cargarMentorias();

        mentoriasButton.addActionListener(e -> agendarMentoria());
        generarReporteButton.addActionListener(e -> generarReporte());
        observacionesButton.addActionListener(e -> agregarObservacion());
        estadisticasButton1.addActionListener(e -> asignarFase());
        resultadosButton.addActionListener(e -> mostrarResultados());
        agregarAvanceButton.addActionListener(e -> agregarAvanceFase());
        salirButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro que desea cerrar sesión?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Login();
            }
        });

    }

    private void cargarIdeasAsignadas() {
        List<IdeaNegocio> ideas = IdeaNegocioController.obtenerIdeasPorMentor(mentorId);
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Título", "Descripción", "Estado"}, 0);

        for (IdeaNegocio idea : ideas) {
            model.addRow(new Object[]{
                    idea.getId(), idea.getTitulo(), idea.getDescripcion(), idea.getEstado()
            });
        }

        tableideas.setModel(model);
    }

    private void cargarMentorias() {
        List<Mentoria> mentorias = MentoriaController.obtenerMentoriasPorMentor(mentorId);
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Idea ID", "Fecha", "Estado"}, 0);

        for (Mentoria m : mentorias) {
            model.addRow(new Object[]{
                    m.getId(), m.getIdeaId(), m.getFecha(), m.getEstado()
            });
        }

        tableMentorias.setModel(model);
    }

    private void agendarMentoria() {
        int fila = tableideas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una idea primero.");
            return;
        }

        int ideaId = (int) tableideas.getValueAt(fila, 0);
        String fecha = JOptionPane.showInputDialog(this, "Ingrese la fecha (YYYY-MM-DD HH:MM):");

        if (fecha != null && !fecha.isEmpty()) {
            MentoriaController.crearMentoria(ideaId, mentorId, fecha);
            JOptionPane.showMessageDialog(this, "Mentoría agendada.");
            cargarMentorias();
        }
    }

    private void generarReporte() {
        int fila = tableideas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una idea primero.");
            return;
        }

        int ideaId = (int) tableideas.getValueAt(fila, 0);

        // Obtener mentoría asociada a la idea (método debe existir en MentoriaController)
        int mentoriaId = MentoriaController.obtenerMentoriaIdPorIdea(ideaId);
        if (mentoriaId == -1) {
            JOptionPane.showMessageDialog(this, "No se encontró una mentoría para esta idea.");
            return;
        }

        // Obtener fase seleccionada con método propio
        int faseId = obtenerFaseIdSeleccionada();
        if (faseId == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fase válida.");
            return;
        }

        int estadisticaId = EstadisticaController.generarEstadistica(mentoriaId, faseId);
        if (estadisticaId == -1) {
            JOptionPane.showMessageDialog(this, "Error al generar la estadística.");
            return;
        }

        int usuarioIdDueño = IdeaNegocioController.obtenerUsuarioIdPorIdea(ideaId);
        int resultadoId = ResultadoController.registrarResultado(usuarioIdDueño, estadisticaId);

        if (resultadoId == -1) {
            JOptionPane.showMessageDialog(this, "Error al registrar el resultado.");
            return;
        }

        boolean exito = ReporteController.registrarReporte(resultadoId);
        if (!exito) {
            JOptionPane.showMessageDialog(this, "Error al guardar el reporte.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Reporte generado y guardado correctamente.");
    }

    private void agregarObservacion() {
        int fila = tableMentorias.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mentoría.");
            return;
        }

        int mentoriaId = (int) tableMentorias.getValueAt(fila, 0);
        String comentario = JOptionPane.showInputDialog(this, "Ingrese su observación:");

        if (comentario != null && !comentario.trim().isEmpty()) {
            ObservacionController.agregarObservacion(mentoriaId, comentario);
            JOptionPane.showMessageDialog(this, "Observación registrada.");
        }
    }

    private void asignarFase() {
        int fila = tableMentorias.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mentoría.");
            return;
        }

        int mentoriaId = (int) tableMentorias.getValueAt(fila, 0);
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

    private void mostrarResultados() {
        List<String> resultados = ResultadoController.obtenerResultadosDelMentor(mentorId);

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay resultados aún.");
        } else {
            JTextArea area = new JTextArea();
            resultados.forEach(r -> area.append(r + "\n"));
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new java.awt.Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scroll, "Resultados del Mentor", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void agregarAvanceFase() {
        int fila = tableideas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una idea.");
            return;
        }

        int ideaId = (int) tableideas.getValueAt(fila, 0);
        String faseInput = JOptionPane.showInputDialog(this, "Ingrese el ID de la fase:");
        String porcentajeInput = JOptionPane.showInputDialog(this, "Ingrese el porcentaje de avance (0-100):");

        if (faseInput == null || porcentajeInput == null || faseInput.isEmpty() || porcentajeInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
            return;
        }

        try {
            int faseId = Integer.parseInt(faseInput);
            int porcentaje = Integer.parseInt(porcentajeInput);

            if (porcentaje < 0 || porcentaje > 100) {
                JOptionPane.showMessageDialog(this, "El porcentaje debe estar entre 0 y 100.");
                return;
            }

            int idGenerado = AvanceFaseController.registrarAvanceFase(ideaId, faseId, porcentaje);
            if (idGenerado != -1) {
                JOptionPane.showMessageDialog(this, "Avance registrado correctamente. ID: " + idGenerado);
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el avance.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.");
        }
    }

    // Método que obtienes el id de fase a partir del JComboBox o similar
    private int obtenerFaseIdSeleccionada() {
        // Obtiene la lista de fases disponibles desde el controlador
        List<FaseProyecto> fases = EstadisticaController.listarFases();

        if (fases.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay fases registradas en el sistema.");
            return -1;
        }

        // Convierte la lista de fases a un arreglo de nombres para mostrar en el diálogo
        String[] opciones = fases.stream()
                .map(FaseProyecto::getNombre)
                .toArray(String[]::new);

        // Muestra un diálogo para que el usuario seleccione la fase
        String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione la fase:",
                "Fase",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0] // opción por defecto
        );

        // Si el usuario hizo una selección válida, busca y devuelve el ID de la fase
        if (seleccion != null) {
            return fases.stream()
                    .filter(f -> f.getNombre().equals(seleccion))
                    .findFirst()
                    .map(FaseProyecto::getId)
                    .orElse(-1);
        }

        // Si no seleccionó nada o canceló, devuelve -1
        return -1;
    }
}
