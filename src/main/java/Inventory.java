import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
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
        Color myCustomColor1 = new Color(145, 107, 191);
        frmInventoryManagement = new JFrame();
        frmInventoryManagement.setTitle("Inventory Management");
        frmInventoryManagement.setBounds(100, 100, 1000, 600);
        frmInventoryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{
            frmInventoryManagement.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/main/img/bg.png")))));

        }catch(IOException e)
        {
            e.printStackTrace();

        }



        JLabel lblNewLabel = new JLabel("INVENTORY MANAGEMENT");
        Color myCustomColor2 = new Color(23, 0, 85);
        lblNewLabel.setForeground(myCustomColor2);
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 28));
        lblNewLabel.setBounds(290, 20, 420, 40);
        frmInventoryManagement.getContentPane().add(lblNewLabel);



        JPanel panel = new JPanel();
        panel.setBorder(blackline);
        panel.setBounds(650, 80, 320, 460);
        frmInventoryManagement.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Add New Product");
        lblNewLabel_1.setForeground(myCustomColor1);

        lblNewLabel_1.setBounds(62, 20, 196, 30);
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 20));
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Name");
        lblNewLabel_2.setBounds(20, 60, 280, 40);
        lblNewLabel_2.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(lblNewLabel_2);

        name = new JTextField();
        name.setBounds(20, 110, 280, 40);
        panel.add(name);
        name.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Price");
        lblNewLabel_3.setFont(new Font("Serif", Font.PLAIN, 18));
        lblNewLabel_3.setBounds(20, 170, 280, 40);
        panel.add(lblNewLabel_3);

        price = new JTextField();
        price.setBounds(20, 220, 280, 40);
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
        lblNewLabel_4.setBounds(20, 280, 280, 40);
        panel.add(lblNewLabel_4);

        qty = new JTextField();
        qty.setBounds(20, 330, 280, 40);
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




        JButton addbtn = new JButton("Add");
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
        addbtn.setBackground(Color.BLUE);
        addbtn.setForeground(Color.WHITE);
        addbtn.setBounds(150, 390, 150, 35);
        panel.add(addbtn);


        //panel for list all products
        JPanel panel2=new JPanel();
        panel2.setBounds(30,80,600,460);
        panel2.setLayout(null);
        panel2.setBorder(blackline);
        frmInventoryManagement.add(panel2);

        JLabel lblNewLabel_5 = new JLabel("Product List");
        lblNewLabel_5.setForeground(myCustomColor1);
        lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 20));
        lblNewLabel_5.setBounds(226, 15, 138, 25);
        panel2.add(lblNewLabel_5);

        table_1 = new JTable();
        table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        table_1.setColumnSelectionAllowed(true);
        String heading[]=new String[] {"Name", "Price", "Qty","Update","Delete"
        };

        table_1.setModel(new DefaultTableModel(
                new Object[][] {
                },
heading
        ){
            boolean[] columnEditables = new boolean[] {
                    false, false, false, true,true
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table_1.setRowHeight(30);
        table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table_1.getColumnModel().getColumn(3).setCellRenderer(new UpdateButtonRender());;
        table_1.getColumnModel().getColumn(3).setCellEditor(new UpdateButtonEditor(new JTextField()));

        table_1.getColumnModel().getColumn(4).setCellRenderer(new DeleteButtonRender());;
        table_1.getColumnModel().getColumn(4).setCellEditor(new DeleteButtonEditor(new JTextField()));

        table_1.getColumnModel().getColumn(0).setPreferredWidth(250);
        table_1.getColumnModel().getColumn(1).setPreferredWidth(110);
        table_1.getColumnModel().getColumn(2).setPreferredWidth(100);
        table_1.getColumnModel().getColumn(3).setPreferredWidth(60);
        table_1.getColumnModel().getColumn(4).setPreferredWidth(54);
        JTableHeader th=table_1.getTableHeader();
        Color myCustomColor3 = new Color(32, 136, 203);
        th.setBackground(myCustomColor3);
        th.setForeground(Color.white);
        Font headerFont = new Font("Verdana", Font.BOLD, 14);
        th.setFont(headerFont);


        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setBounds(10, 50, 580, 380);
        panel2.add(scrollPane);
        table_1.setEnabled(true);
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


            Object data[]={product.getString("name"),product.getInteger("price").toString(),product.getInteger("quantity").toString(),"✎","✘"};
            tableadd.addRow(data);
        }
    }

}



//DELETE BUTTON FROM THE CART ACTION
class DeleteButtonRender extends JButton implements TableCellRenderer
{

    //CONSTRUCTOR
    public DeleteButtonRender() {
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
class DeleteButtonEditor extends DefaultCellEditor
{
    protected JButton btn;
    private String lbl;
    private Boolean clicked;

    public DeleteButtonEditor(JTextField txt) {
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
            JOptionPane.showMessageDialog(btn," Remove Clicked");
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
//DELETE BUTTON FROM THE CART ACTION
class UpdateButtonRender extends JButton implements TableCellRenderer
{

    //CONSTRUCTOR
    public UpdateButtonRender() {
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
class UpdateButtonEditor extends DefaultCellEditor
{
    protected JButton btn;
    private String lbl;
    private Boolean clicked;

    public UpdateButtonEditor(JTextField txt) {
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
            JOptionPane.showMessageDialog(btn," Update Clicked");
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