import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TableExample {

    private final TableRowSorter<TableModel> sorter;
    private JTextField field;
    public JTable table;

    public TableExample(){

        Border blackline = BorderFactory.createLineBorder(Color.black);
        JFrame frame = new JFrame();
        JLabel lblNewLabel = new JLabel("Billing");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 28));
        lblNewLabel.setBounds(400, 15, 200, 35);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Item Name");
        lblNewLabel_1.setFont(new Font("Serif", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(100, 55, 120, 25);
        frame.getContentPane().add(lblNewLabel_1);

        field = new JTextField();
        field.setBounds(225, 55, 200, 25);
        frame.getContentPane().add(field);
        field.setColumns(10);

        field.getDocument().addDocumentListener(new DocumentListener() {  // <---
            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        DefaultTableModel model = new DefaultTableModel(new String[][]{{"Sarah"},{"sahsdbn"},{"sajjhjh"}, {"Abc"}, {"def"}}, new String[]{"Name"}){
            boolean[] columnEditables = new boolean[] {
                    false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };

        table = new JTable(model);
        sorter = new TableRowSorter<TableModel>(model);  // <--
        table.setRowSorter(sorter);

        table.getColumnModel().getColumn(0).setPreferredWidth(800);
        table.setRowHeight(25);
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(100, 100, 798, 130);
        sp.setBorder(blackline);

        frame.getContentPane().add(field);
        frame.getContentPane().add(field);
        frame.getContentPane().add(sp);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setSize(1000,600);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                System.out.println(row);
            }
        });

    }

    protected void filter() {
        sorter.setRowFilter(RowFilter.regexFilter("^(?i)"+field.getText(),0));  // <--
    }

    public static void main(String[] args) {
        new TableExample();
    }
}

