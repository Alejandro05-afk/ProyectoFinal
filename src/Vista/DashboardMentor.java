package Vista;

import javax.swing.*;

public class DashboardMentor extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelMentor;
    private JTable table1;
    private JButton mentoriasButton;
    private JButton resultadosButton;
    private JButton observacionesButton;
    private JButton estadisticasButton;
    private JButton salirButton;

    public DashboardMentor(){
        setContentPane(panelMentor);
        setTitle("Mentor");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
