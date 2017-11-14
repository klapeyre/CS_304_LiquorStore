package View;

import SQL.SQLMakeOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JTable resultsTable;
    private Vector<String> columnNames;
    private SQLMakeOrder makeOrder;


    public MakeOrder(){
        skuErrorLabel.setVisible(false);
        qtyErrorLabel.setVisible(false);
        supplierErrorLabel.setVisible(false);
        employeeIdErrorLabel.setVisible(false);
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
                int employeeId = parseUserInput(employeeIdField, employeeIdErrorLabel, "employeeId");
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
                Vector<Vector<Object>> tableContents = new Vector<Vector<Object>>();
                tableContents = model.getDataVector();

                if(rowCount != 0){

                }


                // pop up message when order is made
                // automatically attach current date
                // ensure only manager can make an order
                // update item tables???

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
