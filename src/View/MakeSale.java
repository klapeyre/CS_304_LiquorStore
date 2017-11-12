package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                skuQuantityErrorLabel.setVisible(false);
                int sku;
                int quantity;

                try {
                    sku = Integer.parseInt(skuTextField.getText());
                    quantity = Integer.parseInt(quantityTextField.getText());
                } catch (NumberFormatException nfe){
                    skuQuantityErrorLabel.setVisible(true);
                    return;
                }
                //todo check if item already in...
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
                Integer[][] data = getTableData();
                System.out.println(data);
                //todo for each item check it exists in the table, ensure value is fine to subtract
                //todo make changes in saleitems and store_sales
                for (int i = 0; i < data.length; i++ ){
                    Integer sku = data[i][0];
                    Integer quantity = data[i][1];

                    sqlMakeSale.itemExists(sku);//todo

                }
            }
        });
    }

    private Integer[][] getTableData() {
        int rowCount = dataModel.getRowCount();
        Integer[][] tableData = new Integer[rowCount][2];
        for (int i = 0 ; i < rowCount ; i++)
            for (int j = 0 ; j < 2 ; j++)
                tableData[i][j] = Integer.valueOf(dataModel.getValueAt(i,j).toString());
        return tableData;
    }

    public JPanel getPanelMS() {
        return panelMS;
    }
}
