import java.awt.EventQueue;

        import javax.swing.JFrame;
        import javax.swing.JLabel;
        import java.awt.Font;
        import javax.swing.SwingConstants;
        import javax.swing.JTextField;
        import javax.swing.JPanel;
        import javax.swing.JScrollPane;
        import javax.swing.JTable;
        import javax.swing.table.DefaultTableModel;
        import javax.swing.JButton;

public class Billing {

    private JFrame frame;
    private JTextField textField;
    private JTable table;
    private JTable table_1;
    private JTextField textField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Billing window = new Billing();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Billing() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 440, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Billing");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        lblNewLabel.setBounds(186, 15, 70, 22);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Item Name");
        lblNewLabel_1.setBounds(34, 50, 80, 15);
        frame.getContentPane().add(lblNewLabel_1);

        textField = new JTextField();
        textField.setBounds(140, 50, 140, 19);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JPanel panel = new JPanel();
        panel.setBounds(34, 65, 369, 80);
        frame.getContentPane().add(panel);
        panel.setLayout(null);


        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {
                        {"sdgds", "dgfdf"},
                        {"dgfds", "dsgd"},
                        {"dfg", null},
                        {null, null},
                },
                new String[] {
                        "Id", "Name"
                }
        ));
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(0, 20, 369, 80);
        panel.add(sp);

        JLabel lblNewLabel_2 = new JLabel("Cost");
        lblNewLabel_2.setBounds(34, 170, 70, 15);
        frame.getContentPane().add(lblNewLabel_2);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(34, 180, 369, 92);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        table_1 = new JTable();
        table_1.setModel(new DefaultTableModel(
                new Object[][] {
                        {"nkfd", "jknm", "jbbm", "jhndfm"},
                        {"vhnbhj", "jhbjh", "bjbj", "jbjb"},
                        {"bjjbj", "jbjb", "bhjbb", null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                },
                new String[] {
                        "Item", "Nos", "Price", "Total"
                }
        ));
        JScrollPane sp1=new JScrollPane(table_1);
        sp1.setBounds(0, 20, 369, 92);
        panel_1.add(sp1);

        JLabel lblNewLabel_3 = new JLabel("Grand Total");
        lblNewLabel_3.setBounds(34, 290, 90, 30);
        frame.getContentPane().add(lblNewLabel_3);

        textField_1 = new JTextField();
        textField_1.setBounds(140, 290, 114, 30);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        JButton btnNewButton = new JButton("Checkout");
        btnNewButton.setBounds(34, 340, 110, 30);
        frame.getContentPane().add(btnNewButton);
    }
}