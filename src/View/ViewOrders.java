package View;

import SQL.SQLViewOrders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class ViewOrders {
    private JPanel viewOrdersPanel;
    private JTextField orderField;
    private JTextField skuField;
    private JTextField fromField;
    private JTextField toField;
    private JButton orderButton;
    private JButton skuSearchButton;
    private JScrollPane resultsPane;
    private SQLViewOrders viewOrders;

    public ViewOrders() {
        viewOrders = new SQLViewOrders();
        createButtonListeners();
    }

    private void createButtonListeners() {
        skuSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sku = Integer.parseInt(skuField.getText());
                Date fromDate = Date.valueOf(fromField.getText());
                Date toDate = Date.valueOf(toField.getText());
                try {
                    setTableInScrollPane(new JTable
                            (ViewUtils.buildResultsTableModel(viewOrders.searchBySKUAndDate(sku, fromDate, toDate))));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderNumber = Integer.parseInt(orderField.getText());
                try {
                    setTableInScrollPane(new JTable(
                            ViewUtils.buildResultsTableModel(viewOrders.searchByOrderNumber(orderNumber))));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void setTableInScrollPane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        resultsPane.setViewportView(table);
    }

    public JPanel getViewOrdersPanel() {
        return viewOrdersPanel;
    }
}
