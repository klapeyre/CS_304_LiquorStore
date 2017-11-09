package View;

import SQL.SQLSearchStock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.BorderLayout;

public class SearchStock extends JFrame {
    private JPanel searchStockPanel;
    private JTextField nameField;
    private JTextField skuField;
    private JLabel skuLabel;
    private JButton skuSearchButton;
    private JButton nameButton;
    private SQLSearchStock search;

    public SearchStock() {
        search = new SQLSearchStock();
        skuSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sku = Integer.parseInt(skuField.getText());
                try {
                    JTable table = new JTable(search.buildResultsTableModel(search.selectBySKU(sku)));
                    getContentPane().add(new JScrollPane(table), BorderLayout.CENTER); // omly POC
                    // TODO: embed results in current screen, not pop-up table like this
                    pack();
                    setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                try {
                    JTable table = new JTable(search.buildResultsTableModel(search.selectByName(name)));
                    getContentPane().add(new JScrollPane(table), BorderLayout.CENTER); // omly POC
                    // TODO: embed results in current screen, not pop-up table like this
                    pack();
                    setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public JPanel getSearchStockPanel() {
        return searchStockPanel;
    }
}
