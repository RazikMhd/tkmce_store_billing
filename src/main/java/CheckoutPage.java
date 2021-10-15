import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CheckoutPage {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckoutPage window = new CheckoutPage();
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
	public CheckoutPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		JLabel lblNewLabel = new JLabel("Invoice");
		lblNewLabel.setBounds(246, 35, 72, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Date :");
		lblNewLabel_1.setBounds(54, 82, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JTable table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Product Name", "Price", "Qty", "Total" }) {
					Class[] columnTypes = new Class[] { String.class, Float.class, Float.class, Float.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
		table.getColumnModel().getColumn(0).setPreferredWidth(143);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(54, 138, 434, 170);
		frame.getContentPane().add(sp);

		JLabel lblNewLabel_2 = new JLabel("Total Amount:");
		lblNewLabel_2.setBounds(337, 392, 83, 24);
		frame.getContentPane().add(lblNewLabel_2);

		frame.setBounds(100, 100, 599, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
