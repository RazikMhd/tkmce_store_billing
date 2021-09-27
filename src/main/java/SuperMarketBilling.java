import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

public class SuperMarketBilling {

    private JFrame frmInventoryManagement;
    private JTextField name;
    private JTextField price;
    private JTextField qty;
    private JTable table_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SuperMarketBilling window = new SuperMarketBilling();
                    window.frmInventoryManagement.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public SuperMarketBilling() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmInventoryManagement = new JFrame();
        frmInventoryManagement.setTitle("Inventory Management");
        frmInventoryManagement.setBounds(100, 100, 440, 440);
        frmInventoryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmInventoryManagement.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Inventory Management");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        lblNewLabel.setBounds(100, 20, 240, 15);
        frmInventoryManagement.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Add New Product");
        lblNewLabel_1.setBounds(34, 50, 125, 15);
        frmInventoryManagement.getContentPane().add(lblNewLabel_1);

        JButton btnNewButton = new JButton("Update Stock");
        btnNewButton.setBounds(273, 50, 129, 25);
        frmInventoryManagement.getContentPane().add(btnNewButton);

        JPanel panel = new JPanel();
        panel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), null, null, null)));
        panel.setBounds(34, 85, 369, 88);
        frmInventoryManagement.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("Name");
        lblNewLabel_2.setBounds(7, 10, 65, 25);
        panel.add(lblNewLabel_2);

        name = new JTextField();
        name.setBounds(80, 10, 114, 25);
        panel.add(name);
        name.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Price");
        lblNewLabel_3.setBounds(205, 10, 35, 25);
        panel.add(lblNewLabel_3);

        price = new JTextField();
        price.setBounds(250, 10, 114, 25);
        panel.add(price);
        price.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Quantity");
        lblNewLabel_4.setBounds(7, 50, 65, 25);
        panel.add(lblNewLabel_4);

        qty = new JTextField();
        qty.setBounds(80, 50, 114, 25);
        panel.add(qty);
        qty.setColumns(10);


        JButton addbtn = new JButton("Submit");
        addbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(name.getText().equals("")||price.getText().equals("")||qty.getText().equals("")) {
                    JOptionPane.showMessageDialog(addbtn,"Please Enter All Data");
                }
                else{
                    table_1.getColumn("Update").setCellRenderer((TableCellRenderer) new ButtonRenderer());
                    table_1.getColumn("Delete").setCellRenderer((TableCellRenderer) new ButtonRenderer());
                    Object data[]={name.getText(),price.getText(),qty.getText(),"Edit","-"};
                    DefaultTableModel tableadd=(DefaultTableModel)table_1.getModel();
                    tableadd.addRow(data);
                    JOptionPane.showMessageDialog(addbtn,"Product Added Succesfully");
                    name.setText("");
                    price.setText("");
                    qty.setText("");

                }
            }
        });
        addbtn.setBounds(260, 50, 85, 25);
        panel.add(addbtn);

        JLabel lblNewLabel_5 = new JLabel("Product List");
        lblNewLabel_5.setBounds(34, 190, 90, 15);
        frmInventoryManagement.getContentPane().add(lblNewLabel_5);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(34, 208, 369, 160);
        frmInventoryManagement.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        table_1 = new JTable();
        table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        table_1.setColumnSelectionAllowed(true);
        table_1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Name", "Price", "Qty","Update","Delete"
                }
        ));
        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setBounds(0, 16, 369, 160);
        panel_1.add(scrollPane);
    }
}
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        //setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}