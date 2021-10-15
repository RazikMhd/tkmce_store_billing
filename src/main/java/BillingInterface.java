import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class BillingInterface {
	JFrame f;
	ArrayList<Document> shoppingCart = new ArrayList<>();
	ArrayList<Document> documentList;
	JTextField cartQty;
	JTable jTable;
	JLabel grandTotal, labelQty;
	JButton checkOut, manageInventory;
	Border blackline = BorderFactory.createLineBorder(Color.black);

	public BillingInterface() {
		f = new JFrame("ComboBox Example");
		try {
			f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/main/img/billingbg.jpg")))));

		} catch (IOException e) {
			e.printStackTrace();

		}
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("BILLING");
		Color myCustomColor2 = new Color(82, 5, 123);
		lblNewLabel.setForeground(myCustomColor2);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 28));
		lblNewLabel.setBounds(470, 20, 120, 40);
		f.getContentPane().add(lblNewLabel);

		JButton b = new JButton("Add to Cart");
		b.setBounds(750, 100, 180, 30);
		Color myCustomColor6 = new Color(97, 0, 148);
		b.setBackground(myCustomColor6);
		b.setForeground(Color.white);

		checkOut = new JButton("Check Out");
		checkOut.setBounds(60, 480, 150, 30);
		Color myCustomColor5 = new Color(54, 139, 133);
		checkOut.setBackground(myCustomColor5);
		checkOut.setForeground(Color.white);

		manageInventory = new JButton("Manage Inventory");
		manageInventory.setBounds(60, 420, 150, 30);
		manageInventory.setBackground(Color.yellow);
		Color myCustomColor3 = new Color(70, 70, 96);
		manageInventory.setBackground(myCustomColor3);
		manageInventory.setForeground(Color.white);

		grandTotal = new JLabel("Grand Total : ");
		grandTotal.setBounds(700, 480, 400, 50);
		grandTotal.setFont(new Font("Dialog", Font.BOLD, 20));

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("tkm_store");
		MongoCollection<Document> collection = database.getCollection("inventory");

		documentList = collection.find().into(new ArrayList<>());
		String products[] = new String[documentList.size()];

		for (int i = 0; i < documentList.size(); i++) {
			products[i] = documentList.get(i).getString("name");
		}

		cartQty = new JTextField("1");
		cartQty.setBounds(520, 100, 50, 30);

		labelQty = new JLabel("Qty");

		labelQty.setBounds(470, 100, 50, 30);
		Color myCustomColor8 = new Color(2, 44, 67);
		labelQty.setForeground(myCustomColor8);
		labelQty.setFont(new Font("Dialog", Font.BOLD, 18));

		JComboBox cb = new JComboBox(products);
		Color myCustomColor7 = new Color(127, 200, 169);
		cb.setBackground(myCustomColor7);
		cb.setForeground(Color.BLUE);
		cb.setBounds(60, 100, 350, 30);

		String heading[] = new String[] { "Name", "Price", "Quantity", "Total" };

		jTable = new JTable();

		jTable.setModel((new DefaultTableModel(new Object[][] {}, heading)));
		jTable.setFont(new Font("Serif", 1, 15));
		jTable.getColumnModel().setColumnMargin(5);
		jTable.setRowHeight(30);

		jTable.getColumnModel().getColumn(0).setPreferredWidth(410);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(140);
		jTable.getColumnModel().getColumn(2).setPreferredWidth(140);
		jTable.getColumnModel().getColumn(3).setPreferredWidth(180);

		JTableHeader th = jTable.getTableHeader();
		Color myCustomColor4 = new Color(156, 61, 84);
		th.setBackground(myCustomColor4);
		th.setForeground(Color.white);
		Font headerFont = new Font("Verdana", Font.BOLD, 14);
		th.setFont(headerFont);

		JScrollPane sp = new JScrollPane(jTable);

		sp.setBounds(60, 210, 870, 150);
		sp.setBorder(blackline);

		f.getContentPane().add(sp);

		f.add(cb);
		f.add(b);
		f.add(cartQty);
		f.add(labelQty);
		f.add(grandTotal);
		f.add(checkOut);
		f.add(manageInventory);
		f.setLayout(null);
		f.setSize(1000, 600);
		f.setVisible(true);

//Redirecting from Billing  page to Inventory on button Click
		manageInventory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Inventory secod = new Inventory();
				f.setVisible(false);
				secod.frmInventoryManagement.setVisible(true);
			}
		});

		// Redirecting from Billing page to Inventory on button Click
		checkOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckoutPage ft = new CheckoutPage();
				f.setVisible(false);
				ft.frame.setVisible(true);
			}
		});

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int itemNos = 1;
				try {
					itemNos = Integer.parseInt(cartQty.getText());
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				Document newCartProduct = documentList.get(cb.getSelectedIndex());
				newCartProduct.remove("_id");
				newCartProduct.put("quantity", itemNos);
				newCartProduct.put("itemTotal", itemNos * newCartProduct.getInteger("price"));

				if (shoppingCart.contains(newCartProduct)) {
					shoppingCart.set(shoppingCart.indexOf(newCartProduct), newCartProduct);
				} else {
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

	public static void main(String[] args) {
		BillingInterface billingInterface = new BillingInterface();
	}

	// populates data from DB to table
	public void loadTableData() {
		DefaultTableModel tableadd = (DefaultTableModel) jTable.getModel();
		tableadd.setRowCount(0);
		for (Document product : shoppingCart) {
			Object data[] = { product.getString("name"), product.getInteger("price").toString(),
					product.getInteger("quantity").toString(), product.getInteger("itemTotal").toString() };
			tableadd.addRow(data);
		}
		calculateTotal();
	}

	public void calculateTotal() {
		int total = 0;
		for (Document product : shoppingCart) {
			total += product.getInteger("itemTotal");
		}
		grandTotal.setText("Grand Total : " + total);
	}

}
