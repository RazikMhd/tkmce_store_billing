import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Billing {

    private final TableRowSorter<TableModel> sorter;
    private JTextField field;
    public JTable table;

    public Billing(){

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

        DefaultTableModel model = new DefaultTableModel(new String[][]{{"Sarah","100"}, {"Abc","50"}, {"def","30"}}, new String[]{"Name","Price","Selection"}){
            boolean[] columnEditables = new boolean[] {
                    false, false, true
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };

        table = new JTable(model);
        sorter = new TableRowSorter<TableModel>(model);  // <--
        table.setRowSorter(sorter);
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRender());

        table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JTextField()));
        table.getColumnModel().getColumn(0).setPreferredWidth(550);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(98);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// <--
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(100, 100, 800, 130);
        sp.setBorder(blackline);

        JLabel lblNewLabel_2 = new JLabel("Cart");
        lblNewLabel_2.setFont(new Font("Serif", Font.PLAIN, 18));
        lblNewLabel_2.setBounds(100, 250, 70, 15);
        frame.getContentPane().add(lblNewLabel_2);


        JTable table_1 = new JTable();
        table_1.setModel(new DefaultTableModel(
                new Object[][] {
                        {"nkfd", "jknm", "jbbm", "jhndfm"},
                        {"vhnbhj", "jhbjh", "bjbj", "jbjb"},
                        {"bjjbj", "jbjb", "bhjbb", null},
                        {null, null, null, null},
                },
                new String[] {
                        "Item", "Nos", "Price", "Total","Remove"
                }
        ){
            boolean[] columnEditables = new boolean[] {
                    false, true, false, false,true
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table_1.setRowHeight(25);
        table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table_1.getColumnModel().getColumn(4).setCellRenderer(new RemoveButtonRender());;

        table_1.getColumnModel().getColumn(4).setCellEditor(new RemoveButtonEditor(new JTextField()));
        table_1.getColumnModel().getColumn(0).setPreferredWidth(400);
        table_1.getColumnModel().getColumn(1).setPreferredWidth(100);
        table_1.getColumnModel().getColumn(2).setPreferredWidth(120);
        table_1.getColumnModel().getColumn(3).setPreferredWidth(110);
        table_1.getColumnModel().getColumn(4).setPreferredWidth(68);

        JScrollPane sp1=new JScrollPane(table_1);
        sp1.setBounds(100, 280, 800, 132);
        sp1.setBorder(blackline);
        frame.getContentPane().add(sp1);

        JLabel lblNewLabel_3 = new JLabel("Grand Total");
        lblNewLabel_3.setFont(new Font("Serif", Font.PLAIN, 18));
        lblNewLabel_3.setBounds(100, 425, 120, 30);
        frame.getContentPane().add(lblNewLabel_3);


        JButton btnNewButton = new JButton("Checkout");
        btnNewButton.setBounds(100, 470, 150, 50);
        frame.getContentPane().add(btnNewButton);

        JTextField textField_1 = new JTextField();
        textField_1.setBounds(225, 425, 114, 30);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);
        textField_1.setEnabled(false);
        textField_1.setBorder(blackline);

        frame.getContentPane().add(field);
        frame.getContentPane().add(field);
        frame.getContentPane().add(sp);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setSize(1000,600);

        loadItems();
    }

    public void loadItems()
    {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("tkm_store");
        MongoCollection<Document> collection = database.getCollection("inventory");
        ArrayList<Document> documentList = collection.find().into(new ArrayList<>());

        DefaultTableModel tableadd=(DefaultTableModel)table.getModel();
        tableadd.setRowCount(0);
        for (Document product : documentList) {
            System.out.println(product.getString("name"));
            Object data[]={product.getString("name"),product.getInteger("price").toString()};
            tableadd.addRow(data);
        }
    }

    protected void filter() {
        sorter.setRowFilter(RowFilter.regexFilter("^(?i)"+field.getText(),0));  // <--
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Billing();
            }
        });
    }
}

//ADD BUTTON TO THE CART ACTION

class ButtonRender extends JButton implements TableCellRenderer
{

    //CONSTRUCTOR
    public ButtonRender() {
        //SET BUTTON PROPERTIES
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj,
                                                   boolean selected, boolean focused, int row, int col) {

        //SET PASSED OBJECT AS BUTTON TEXT
        setText((obj==null) ? "":obj.toString());

        return this;
    }

}

//BUTTON EDITOR CLASS
class ButtonEditor extends DefaultCellEditor
{
    protected JButton btn;
    private String lbl;
    private Boolean clicked;

    public ButtonEditor(JTextField txt) {
        super(txt);

        btn=new JButton();
        btn.setOpaque(true);

        //WHEN BUTTON IS CLICKED
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                fireEditingStopped();
            }
        });
    }

    //OVERRIDE A COUPLE OF METHODS
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {

        //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
        lbl=(obj==null) ? "":obj.toString();
        btn.setText(lbl);
        clicked=true;
        return btn;
    }

    @Override
    public Object getCellEditorValue() {

        if(clicked)
        {
            //SHOW US SOME MESSAGE
            JOptionPane.showMessageDialog(btn, lbl+" selection Clicked");
        }
        //SET IT TO FALSE NOW THAT ITS CLICKED
        clicked=false;
        return new String(lbl);
    }

    @Override
    public boolean stopCellEditing() {

        //SET CLICKED TO FALSE FIRST
        clicked=false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}


//REMOVE ITEM FROM THE CART ACTION
class RemoveButtonRender extends JButton implements TableCellRenderer
{

    //CONSTRUCTOR
    public RemoveButtonRender() {
        //SET BUTTON PROPERTIES
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj,
                                                   boolean selected, boolean focused, int row, int col) {

        //SET PASSED OBJECT AS BUTTON TEXT
        setText((obj==null) ? "":obj.toString());

        return this;
    }

}

//BUTTON EDITOR CLASS
class RemoveButtonEditor extends DefaultCellEditor
{
    protected JButton btn;
    private String lbl;
    private Boolean clicked;

    public RemoveButtonEditor(JTextField txt) {
        super(txt);

        btn=new JButton();
        btn.setOpaque(true);

        //WHEN BUTTON IS CLICKED
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                fireEditingStopped();
            }
        });
    }

    //OVERRIDE A COUPLE OF METHODS
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {

        //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
        lbl=(obj==null) ? "":obj.toString();
        btn.setText(lbl);
        clicked=true;
        return btn;
    }

    @Override
    public Object getCellEditorValue() {

        if(clicked)
        {
            //SHOW US SOME MESSAGE
            JOptionPane.showMessageDialog(btn, lbl+" Remove Clicked");
        }
        //SET IT TO FALSE NOW THAT ITS CLICKED
        clicked=false;
        return new String(lbl);
    }

    @Override
    public boolean stopCellEditing() {

        //SET CLICKED TO FALSE FIRST
        clicked=false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}