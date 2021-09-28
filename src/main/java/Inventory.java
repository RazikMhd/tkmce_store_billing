import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.awt.*;

import javax.swing.*;

import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

public class Inventory {

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
                    Inventory window = new Inventory();
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
    public Inventory() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        frmInventoryManagement = new JFrame();
        frmInventoryManagement.setTitle("Inventory Management");
        frmInventoryManagement.setBounds(100, 100, 1000, 600);
        frmInventoryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmInventoryManagement.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Inventory Management");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 28));
        lblNewLabel.setBounds(300, 20, 400, 40);
        frmInventoryManagement.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Add New Product");
        lblNewLabel_1.setBounds(100, 70, 200, 25);
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 20));
        frmInventoryManagement.getContentPane().add(lblNewLabel_1);

        JPanel panel = new JPanel();
        panel.setBorder(blackline);
        panel.setBounds(100, 115, 800, 120);
        frmInventoryManagement.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("Name");
        lblNewLabel_2.setBounds(10, 20, 100, 25);
        lblNewLabel_2.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(lblNewLabel_2);

        name = new JTextField();
        name.setBounds(100, 20, 200, 25);
        panel.add(name);
        name.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Price");
        lblNewLabel_3.setFont(new Font("Serif", Font.PLAIN, 18));
        lblNewLabel_3.setBounds(350, 20, 100, 25);
        panel.add(lblNewLabel_3);

        price = new JTextField();
        price.setBounds(420, 20, 114, 25);
        panel.add(price);
        price.setColumns(10);

        price.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c=e.getKeyChar();
                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
                    e.consume();
                }
            }
        });


        JLabel lblNewLabel_4 = new JLabel("Quantity");
        lblNewLabel_4.setFont(new Font("Serif", Font.PLAIN, 17));
        lblNewLabel_4.setBounds(10, 70, 100, 25);
        panel.add(lblNewLabel_4);

        qty = new JTextField();
        qty.setBounds(100, 70, 114, 25);
        panel.add(qty);
        qty.setColumns(10);
        qty.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c=e.getKeyChar();
                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
                    e.consume();
                }
            }
        });

        JButton addbtn = new JButton("Add New Product");
        addbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(name.getText().equals("")||price.getText().equals("")||qty.getText().equals("")) {
                    JOptionPane.showMessageDialog(addbtn,"Please Enter All Data");
                }
                else{

                    try {
                            MongoClient mongo = new MongoClient("localhost", 27017);
                            MongoDatabase database = mongo.getDatabase("tkm_store");
                            MongoCollection<Document> collection = database.getCollection("inventory");
                            Document item = new Document("_id", new ObjectId());
                            item.append("name", name.getText()).append("price", Integer.parseInt(price.getText())).append("quantity", Integer.parseInt(qty.getText()));
                            collection.insertOne(item);
                            JOptionPane.showMessageDialog(addbtn,"Product added to DB Successfully");
                            name.setText("");
                            price.setText("");
                            qty.setText("");
                            loadTableData();
                    }
                    catch (Exception exception)
                    {
                        JOptionPane.showMessageDialog(addbtn,"Could not Connect to MongoDB Database "+exception);
                    }

                }
            }
        });
        addbtn.setBounds(355, 70, 150, 35);
        panel.add(addbtn);

        JLabel lblNewLabel_5 = new JLabel("Product List");
        lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 20));
        lblNewLabel_5.setBounds(100, 250, 200, 25);
        frmInventoryManagement.getContentPane().add(lblNewLabel_5);


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
        table_1.setRowHeight(30);
        table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table_1.getColumnModel().getColumn(0).setPreferredWidth(368);
        table_1.getColumnModel().getColumn(1).setPreferredWidth(150);
        table_1.getColumnModel().getColumn(2).setPreferredWidth(120);
        table_1.getColumnModel().getColumn(3).setPreferredWidth(80);
        table_1.getColumnModel().getColumn(4).setPreferredWidth(80);
        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setBounds(100, 300, 800, 250);
        scrollPane.setBorder(blackline);
        frmInventoryManagement.add(scrollPane);
        table_1.setEnabled(false);
        loadTableData();
    }

    // populates data from DB to table
    public void loadTableData()
    {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("tkm_store");
        MongoCollection<Document> collection = database.getCollection("inventory");
        ArrayList<Document> documentList = collection.find().into(new ArrayList<>());

        DefaultTableModel tableadd=(DefaultTableModel)table_1.getModel();
        tableadd.setRowCount(0);
        for (Document product : documentList) {
            System.out.println(product.getString("name"));
            table_1.getColumn("Update").setCellRenderer((TableCellRenderer) new ButtonRenderer());
            table_1.getColumn("Delete").setCellRenderer((TableCellRenderer) new ButtonRenderer());
            Object data[]={product.getString("name"),product.getInteger("price").toString(),product.getInteger("quantity").toString(),"✎","✘"};
            tableadd.addRow(data);
        }
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