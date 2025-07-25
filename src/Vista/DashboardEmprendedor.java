package Vista;

import javax.swing.*;

public class DashboardEmprendedor extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panelEmprendedor;
    private JTable tableMisIdeas;
    private JButton rigistrarButton;
    private JButton eliminarButton;
    private JButton agregarResultadosButton;
    private JTextArea textAreaMentorias;
    private JButton verObservacionesButton;
    private JButton guardarButton;
    private JComboBox comboBoxFace;
    private JTextField textFieldID;
    private JTextField textFieldPorcentaje;
    private JTextField textFechaMentoria;
    private JButton salirButton;

    public DashboardEmprendedor(){
        setContentPane(panelEmprendedor);
        setTitle("Emprendedor");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
