package Vista;

import javax.swing.*;

public class DashboardAdmin extends JFrame{
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
    private JList listLogs;
    private JButton resultadosButton;
    private JButton estadisticasButton;
    private JButton salirButton;

    public DashboardAdmin(){
        setContentPane(panelAdmin);
        setTitle("Administrador");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
