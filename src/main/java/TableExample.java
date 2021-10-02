import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableExample {
    JFrame f;
    TableExample() {
        f = new JFrame();
        String data[][] = {{"Amit"},
                {"Jai"},
                {"Sachin"}};
        String column[] = {"NAME"};
        JTable jt = new JTable(data, column);
        jt.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(jt);
        f.add(sp);
        f.setSize(300, 400);
        f.setVisible(true);


        jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = jt.getSelectedRow();
                int col = jt.getSelectedColumn();
                System.out.println(row);
            }
        });
    }

    public static void main(String[] args) {
        new TableExample();
    }
}
