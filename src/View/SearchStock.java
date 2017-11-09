package View;

import SQL.SQLSearchStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SearchStock extends JFrame {
    private JPanel searchStockPanel;
    private JTextField nameField;
    private JTextField skuField;
    private JButton skuSearchButton;
    private JLabel searchBySKULabel;
    private JLabel searchByNameLabel;
    private JButton nameButton;
    private JScrollPane resultsPane;
    private SQLSearchStock search;

    public SearchStock() {
        search = new SQLSearchStock();
        skuSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sku = Integer.parseInt(skuField.getText());
                try {
                    setTableInScrollPane(new JTable(search.buildResultsTableModel(search.selectBySKU(sku))));
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
                    setTableInScrollPane(new JTable(search.buildResultsTableModel(search.selectByName(name))));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public JPanel getSearchStockPanel() {
        return searchStockPanel;
    }

    private void setTableInScrollPane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        resultsPane.setViewportView(table);
    }

}
