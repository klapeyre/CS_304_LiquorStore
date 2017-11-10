package View;

import SQL.SQLSearchStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class SearchStock extends JFrame {
    private JPanel searchStockPanel;
    private JTextField nameField;
    private JTextField skuField;
    private JButton skuSearchButton;
    private JLabel searchBySKULabel;
    private JLabel searchByNameLabel;
    private JButton nameButton;
    private JScrollPane resultsPane;
    private JComboBox storeNames;
    private JLabel taxLabel;
    private JRadioButton beforeTaxRadioButton;
    private JRadioButton afterTaxRadioButton;
    private JRadioButton beerRadionButton;
    private JRadioButton wineRadioButton;
    private JButton typeButton;
    private JComboBox subTypes;
    private SQLSearchStock search;

    public SearchStock() {
        search = new SQLSearchStock();
        setAndGroupButtons(beforeTaxRadioButton, afterTaxRadioButton); // allows user to only select one button at a time
        setAndGroupButtons(beerRadionButton, wineRadioButton);
        populateStoreDropDown();
        populateSubTypeDropDown("Beers");

        skuSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sku = Integer.parseInt(skuField.getText());
                String store = (String) storeNames.getSelectedItem();
                try {
                    if (beforeTaxRadioButton.isSelected()) {
                        setTableInScrollPane(new JTable
                                (search.buildResultsTableModel(search.selectBySKU(sku, false, store))));
                    } else {
                        setTableInScrollPane(new JTable
                                (search.buildResultsTableModel(search.selectBySKU(sku, true, store))));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String store = (String) storeNames.getSelectedItem();
                try {
                    if (beforeTaxRadioButton.isSelected()) {
                        setTableInScrollPane(new JTable
                                (search.buildResultsTableModel(search.selectByName(name, false, store))));
                    } else {
                        setTableInScrollPane(new JTable
                                (search.buildResultsTableModel(search.selectByName(name, true, store))));

                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        typeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subType = (String) subTypes.getSelectedItem();
                String store = (String) storeNames.getSelectedItem();
                try {
                    if (beerRadionButton.isSelected()) {
                        setTableInScrollPane(new JTable
                                (search.buildResultsTableModel(search.selectBySubType(subType, true, store))));
                    } else {
                        setTableInScrollPane(new JTable
                                (search.buildResultsTableModel(search.selectBySubType(subType, false, store))));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        beerRadionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (beerRadionButton.isSelected()) {
                    populateSubTypeDropDown(beerRadionButton.getText().trim());
                }
            }
        });

        wineRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wineRadioButton.isSelected()) {
                    populateSubTypeDropDown(wineRadioButton.getText().trim());
                }
            }
        });
    }

    private void setAndGroupButtons(JRadioButton brother, JRadioButton sister) {
        ButtonGroup group = new ButtonGroup();
        group.add(brother);
        group.add(sister);
        brother.setSelected(true);
    }

    private void populateStoreDropDown() {
        try {
            Vector<String> stores = search.selectByStore();
            for (int i = 0; i < stores.size(); i++) {
                storeNames.addItem(stores.get(i));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateSubTypeDropDown(String type) {
        subTypes.removeAllItems();
        try {
            Vector<String> types = search.selectByType(type);
            for (int i = 0; i < types.size(); i++) {
                subTypes.addItem(types.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel getSearchStockPanel() {
        return searchStockPanel;
    }

    private void setTableInScrollPane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        resultsPane.setViewportView(table);
    }

}
