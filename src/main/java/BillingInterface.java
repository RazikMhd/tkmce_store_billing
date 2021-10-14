import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BillingInterface {
    JFrame f;
    ArrayList<Document> shoppingCart = new ArrayList<>();
    ArrayList<Document> documentList;
    JTextField cartQty;
    JTable jTable;
    JLabel grandTotal;
    JButton checkOut,manageInventory;

    public BillingInterface(){
        f=new JFrame("ComboBox Example");
        JButton b=new JButton("Add to Cart");
        b.setBounds(600,100,150,20);

        checkOut = new JButton("Check Out");
        checkOut.setBounds(30,500,150,20);

        manageInventory = new JButton("Manage Inventory");
        manageInventory.setBounds(30,450,150,20);

        grandTotal = new JLabel("Grand Total : ");
        grandTotal.setBounds(600,500,400,50);

        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("tkm_store");
        MongoCollection<Document> collection = database.getCollection("inventory");

        documentList = collection.find().into(new ArrayList<>());
        String products[]=new String[documentList.size()];

        for(int i=0;i<documentList.size();i++){products[i]=documentList.get(i).getString("name");}

        cartQty=new JTextField("1");
        cartQty.setBounds(450,100, 50,20);

        JComboBox cb=new JComboBox(products);
        cb.setBounds(50, 100,350,20);


        String heading[]=new String[] {"Name", "Price", "Quantity","Total"};

        jTable =new JTable();

        jTable.setModel((new DefaultTableModel(new Object[][] {}, heading)));

        jTable.setBounds(30,100,200,300);
        JScrollPane sp=new JScrollPane(jTable);

        sp.setBounds(30, 280, 800, 132);
        f.getContentPane().add(sp);


        f.add(cb);
        f.add(b);
        f.add(cartQty);
        f.add(grandTotal);
        f.add(checkOut);
        f.add(manageInventory);
        f.setLayout(null);
        f.setSize(1000,700);
        f.setVisible(true);


        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int itemNos=1;
                try
                {
                    itemNos = Integer.parseInt(cartQty.getText());
                }catch(Exception exception)
                {
                   exception.printStackTrace();
                }
                Document newCartProduct = documentList.get(cb.getSelectedIndex());
                newCartProduct.remove("_id");
                newCartProduct.put("quantity",itemNos);
                newCartProduct.put("itemTotal",itemNos*newCartProduct.getInteger("price"));

                if(shoppingCart.contains(newCartProduct))
                {
                    shoppingCart.set(shoppingCart.indexOf(newCartProduct), newCartProduct);
                }
                else
                {
                    shoppingCart.add(newCartProduct);
                }

                cartQty.setText("1");
                loadTableData();
            }
        });

        jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = jTable.getSelectedRow();
                shoppingCart.remove(row);
                loadTableData();
            }
        });

    }

    public static void main(String[] args)
    {
        BillingInterface billingInterface = new BillingInterface();
    }


    // populates data from DB to table
    public void loadTableData()
    {
        DefaultTableModel tableadd=(DefaultTableModel)jTable.getModel();
        tableadd.setRowCount(0);
        for (Document product : shoppingCart) {
            Object data[]={product.getString("name"),product.getInteger("price").toString(),product.getInteger("quantity").toString(),product.getInteger("itemTotal").toString()};
            tableadd.addRow(data);
        }
        calculateTotal();
    }

    public void calculateTotal()
    {
        int total = 0;
        for (Document product : shoppingCart) {
            total += product.getInteger("itemTotal");
        }
        grandTotal.setText("Grand Total : "+total);
    }



}
