package View;

import SQL.SQLMakeOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Vector;

public class MakeOrder extends JFrame{
    private JPanel MakeOrderMainPanel;
    private JPanel InputPanel;
    private JPanel labelPanel;
    private JPanel buttonPanel;
    private JLabel skuLabel;
    private JLabel qtyLabel;
    private JTextField skuField;
    private JTextField qtyField;
    private JButton addToOrderButton;
    private JButton placeOrderButton;
    private JPanel textInputPanel;
    private JLabel skuErrorLabel;
    private JLabel qtyErrorLabel;
    private JButton removeFromOrderButton;
    private JScrollPane resultsPane;
    private JTextField employeeIdField;
    private JLabel EmployeeIdLabel;
    private JLabel employeeIdErrorLabel;
    private JTextField supplierField;
    private JLabel supplierErrorLabel;
    private JLabel suuplierLabel;
    private JPanel orderReceivedPanel;
    private JPanel orderReceivedButtonPanel;
    private JPanel orderReceivedInputPanel;
    private JButton orderReceivedButton;
    private JTextField orderNumberField;
    private JLabel orderNumberLabel;
    private JLabel orderNumberErrorLabel;
    private JTable resultsTable;
    private Vector<String> columnNames;
    private SQLMakeOrder makeOrder;


    public MakeOrder(){
        skuErrorLabel.setVisible(false);
        qtyErrorLabel.setVisible(false);
        supplierErrorLabel.setVisible(false);
        employeeIdErrorLabel.setVisible(false);
        orderNumberErrorLabel.setVisible(false);
        columnNames = new Vector<String>();
        columnNames.add("SKU");
        columnNames.add("Quantity");
        columnNames.add("Supplier");
        columnNames.add("Employee ID");
        resultsTable = new JTable(null, columnNames);
        makeOrder = new SQLMakeOrder();
        MakeOrderActionListeners();
    }

    private void MakeOrderActionListeners(){
        addToOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sku = parseUserInput(skuField, skuErrorLabel, "sku");
                int qty = parseUserInput(qtyField, qtyErrorLabel, "qty");
                int employeeId = parseUserInput(employeeIdField, employeeIdErrorLabel, "employee Id");
                String supplier = supplierField.getText();
                if((sku != -1) && (qty != -1) && (employeeId != -1)){
                    DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
                    model.addRow(new Object[]{sku, qty, supplier, employeeId});
                    setTableInScrollPane(resultsTable);
                }
            }
        });

        removeFromOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
                if (resultsTable.getSelectedRow() != -1){
                    model.removeRow(resultsTable.getSelectedRow());
                }
            }
        });

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
                int rowCount = resultsTable.getRowCount();
                Vector<Vector<Object>> tableContents = model.getDataVector();

                if(rowCount != 0){
                    for (Vector<Object> order : tableContents){
                        int sku = (int)order.get(0);
                        int qty = (int)order.get(1);
                        String supplier = (String)order.get(2);
                        int employeeId = (int)order.get(3);
                        makeOrder.makeOrder(sku, qty, supplier, employeeId);
                    }
                }
            }
        });

        orderReceivedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderNumber = parseUserInput(orderNumberField, orderNumberErrorLabel, "order #");
                Timestamp dateReceived = new Timestamp(System.currentTimeMillis());
                makeOrder.markOrderAsReceived(orderNumber, dateReceived);
            }
        });
    }

    private int parseUserInput(JTextField inputField, JLabel errorLabel, String id) {
        int value = 0;
        try {
            value = Integer.parseInt(inputField.getText());
            errorLabel.setVisible(false);
        } catch (NumberFormatException ne) {
            errorLabel.setVisible(true);
            errorLabel.setForeground(Color.RED);
            errorLabel.setText("Invalid input value for: " + id);
            value = -1;
        }
        return value;
    }

    private void setTableInScrollPane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        resultsPane.setViewportView(table);
    }

    public JPanel getMakeOrderMainPanel() {
        return MakeOrderMainPanel;
    }
}
