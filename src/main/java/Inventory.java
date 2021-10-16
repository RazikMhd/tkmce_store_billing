import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Inventory {

	public JFrame frmInventoryManagement;
	private JTextField name;
	private JTextField price;
	private JTextField qty;
	private JTable table_1;
	public ArrayList<Document> documentList = new ArrayList<>();

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
	public void initialize() {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		Color myCustomColor1 = new Color(145, 107, 191);
		frmInventoryManagement = new JFrame();
		frmInventoryManagement.setTitle("Inventory Management");
		frmInventoryManagement.setBounds(100, 100, 1000, 600);
		frmInventoryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			frmInventoryManagement
					.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/main/img/bg.png")))));

		} catch (IOException e) {
			e.printStackTrace();

		}
		JButton Billing_Section = new JButton("Billing Section");
		Color myCustomColr5 = new Color(75, 56, 105);
		Billing_Section.setBackground(myCustomColr5);
		Billing_Section.setForeground(Color.white);
		Billing_Section.setBounds(120, 25, 120, 40);
		frmInventoryManagement.getContentPane().add(Billing_Section);

		JLabel lblNewLabel = new JLabel("INVENTORY MANAGEMENT");
		Color myCustomColor2 = new Color(251, 116, 62);
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
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
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
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
		});

		JButton addbtn = new JButton("Add");
		addbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (name.getText().equals("") || price.getText().equals("") || qty.getText().equals("")) {
					JOptionPane.showMessageDialog(addbtn, "Please Enter All Data");
				} else {

					try {
						MongoClient mongo = new MongoClient("localhost", 27017);
						MongoDatabase database = mongo.getDatabase("tkm_store");
						MongoCollection<Document> collection = database.getCollection("inventory");
						Document item = new Document("_id", new ObjectId());
						item.append("name", name.getText()).append("price", Integer.parseInt(price.getText()))
								.append("quantity", Integer.parseInt(qty.getText()));
						collection.insertOne(item);
						JOptionPane.showMessageDialog(addbtn, "Product added to DB Successfully");
						name.setText("");
						price.setText("");
						qty.setText("");
						loadTableData();
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(addbtn, "Could not Connect to MongoDB Database " + exception);
					}

				}
			}
		});
		addbtn.setBackground(Color.BLUE);
		addbtn.setForeground(Color.WHITE);
		addbtn.setBounds(150, 390, 150, 35);
		panel.add(addbtn);

		// panel for list all products
		JPanel panel2 = new JPanel();
		panel2.setBounds(30, 80, 600, 460);
		panel2.setLayout(null);
		panel2.setBorder(blackline);
		frmInventoryManagement.add(panel2);

		JLabel lblNewLabel_5 = new JLabel("PRODUCT DETAILS");
		lblNewLabel_5.setForeground(myCustomColor1);
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel_5.setBounds(188, 15, 224, 25);
		panel2.add(lblNewLabel_5);

		table_1 = new JTable();
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setColumnSelectionAllowed(true);
		String heading[] = new String[] { "Name", "Price", "Qty", "Delete" };

		table_1.setModel(new DefaultTableModel(new Object[][] {}, heading) {
			boolean[] columnEditables = new boolean[] { false, false, false, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.setRowHeight(30);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table_1.getColumnModel().setColumnMargin(5);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(300);

		table_1.getColumnModel().getColumn(1).setPreferredWidth(110);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(70);

		table_1.setFont(new Font("Serif", 1, 15));

		JTableHeader th = table_1.getTableHeader();
		Color myCustomColor3 = new Color(32, 136, 203);
		th.setBackground(myCustomColor3);
		th.setForeground(Color.white);
		Font headerFont = new Font("Verdana", Font.BOLD, 14);
		th.setFont(headerFont);

		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(10, 50, 580, 380);
		panel2.add(scrollPane);
		table_1.setEnabled(true);
		frmInventoryManagement.setVisible(true);
		loadTableData();

		// Redirecting from Inventory page to Billing on button Click
		Billing_Section.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BillingInterface third = new BillingInterface();
				frmInventoryManagement.setVisible(false);
				third.f.setVisible(true);
			}
		});

		table_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table_1.getSelectedRow();

				try {
					MongoClient mongo = new MongoClient("localhost", 27017);
					MongoDatabase database = mongo.getDatabase("tkm_store");
					MongoCollection<Document> collection = database.getCollection("inventory");
					collection.deleteOne(documentList.get(row));

				}catch (Exception exception)
				{
					exception.printStackTrace();
				}

				loadTableData();
			}
		});

	}

	// populates data from DB to table
	public void loadTableData() {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("tkm_store");
		MongoCollection<Document> collection = database.getCollection("inventory");
		documentList = collection.find().into(new ArrayList<>());

		DefaultTableModel tableadd = (DefaultTableModel) table_1.getModel();
		tableadd.setRowCount(0);
		for (Document product : documentList) {
			Object data[] = { product.getString("name"), product.getInteger("price").toString(),
					product.getInteger("quantity").toString(), "✎", "✘" };
			tableadd.addRow(data);
		}
	}

}
