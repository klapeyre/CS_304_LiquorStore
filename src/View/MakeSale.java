package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import SQL.SQLMakeSale;

public class MakeSale {
    private JTextField skuTextField;
    private JTextField quantityTextField;
    private JButton addToSaleButton;
    private JButton removeFromSaleButton;
    private JButton makeSaleButton;
    private JLabel skuQuantityErrorLabel;
    private JTable saleItemTable;
    private JPanel panelMS;
    private JTextField employeeIDTextField;
    private JTextField storeIdTextField;
    private JLabel empStoreErrorLabel;
    DefaultTableModel dataModel;
    private SQLMakeSale sqlMakeSale;

    public MakeSale() {
        sqlMakeSale = new SQLMakeSale();
        createButtonListeners();
        String[] columnNames = {"SKU","Quantity"};
        dataModel = new DefaultTableModel(columnNames, 0);
        saleItemTable.setModel(dataModel);
    }

    private void createButtonListeners(){
        addToSaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearErrors();
                int sku;
                int quantity;

                try {
                    sku = Integer.parseInt(skuTextField.getText());
                    quantity = Integer.parseInt(quantityTextField.getText());
                } catch (NumberFormatException nfe){
                    skuQuantityErrorLabel.setVisible(true);
                    return;
                }

                if (!sqlMakeSale.itemExists(sku)){
                    JOptionPane.showMessageDialog(null, "Item "+sku+" does not exist!");
                    return;
                }

                if (isInTableAlready(sku)){
                    JOptionPane.showMessageDialog(null, "Item "+sku+" is in table already, edit quantity there.");
                    return;
                }

                Integer[] rowData = {sku, quantity};
                dataModel.addRow(rowData);
            }
        });

        removeFromSaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = saleItemTable.getSelectedRows();
                for(int i=0;i<rows.length;i++){
                    dataModel.removeRow(rows[i]-i);
                }
            }
        });

        makeSaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearErrors();
                Integer employeeID;
                Integer storeID;
                try {
                    employeeID = Integer.parseInt(employeeIDTextField.getText());
                    storeID = Integer.parseInt(storeIdTextField.getText());
                } catch (NumberFormatException nfe){
                    empStoreErrorLabel.setVisible(true);
                    return;
                }

                Integer[][] data = getTableData();
                if(!dataAreOkForSale(data, storeID)){
                    return;
                } else {
                    try {
                        sqlMakeSale.makeSale(data, storeID, employeeID);
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Sale could not be made. "+e1.getMessage());
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Sale was made! :)");
                }
            }
        });
    }

    private boolean dataAreOkForSale(Integer[][] data, Integer storeID){
        boolean dataOk = true;
        for (int i = 0; i < data.length; i++ ){
            Integer sku = data[i][0];
            Integer quantity = data[i][1];
            if (!sqlMakeSale.itemExists(sku)){
                JOptionPane.showMessageDialog(null, "Item "+sku+" does not exist!");
                dataOk = false;
            }
            int quantityAtStore = sqlMakeSale.itemQuantityAtStore(sku,storeID);
            if (quantityAtStore < quantity){
                JOptionPane.showMessageDialog(null, "There's only "+quantityAtStore+" of item "+sku+" at store "+storeID+".");
                dataOk = false;
            }
        }
        return dataOk;
    }

    private boolean isInTableAlready(Integer skuToMatch){
        Integer[][] data = getTableData();
        for (int i = 0; i < data.length; i++ ) {
            Integer sku = data[i][0];
            if (sku.intValue() == skuToMatch.intValue())
                return true;
        }
        return false;
    }

    private Integer[][] getTableData() {
        int rowCount = dataModel.getRowCount();
        Integer[][] tableData = new Integer[rowCount][2];
        for (int i = 0 ; i < rowCount ; i++)
            for (int j = 0 ; j < 2 ; j++)
                tableData[i][j] = Integer.valueOf(dataModel.getValueAt(i,j).toString());
        return tableData;
    }

    private void clearErrors(){
        skuQuantityErrorLabel.setVisible(false);
        empStoreErrorLabel.setVisible(false);
    }

    public JPanel getPanelMS() {
        return panelMS;
    }
}
