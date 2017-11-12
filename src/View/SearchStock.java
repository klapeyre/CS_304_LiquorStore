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
    private JLabel skuErrorLabel;
    private JButton nameSearchButton;
    private JScrollPane resultsPane;
    private JComboBox storeNames;
    private JRadioButton beforeTaxRadioButton;
    private JRadioButton afterTaxRadioButton;
    private ButtonGroup taxGroupButtons;
    private JRadioButton beerRadioButton;
    private JRadioButton wineRadioButton;
    private ButtonGroup typeGroupButtons;
    private JButton typeSearchButton;
    private JComboBox subTypes;
    private JLabel taxLabel;
    private SQLSearchStock search;

    public SearchStock() {
        search = new SQLSearchStock();
        skuErrorLabel.setVisible(false);
        taxGroupButtons = setAndGroupButtons(beforeTaxRadioButton, afterTaxRadioButton); // allows user to only select one button at a time
        typeGroupButtons = setAndGroupButtons(beerRadioButton, wineRadioButton);
        populateStoreDropDown();
        populateTypeDropDown("Beers");
        createSearchButtonListeners();
        createRadioButtonListeners();
    }

    private void createSearchButtonListeners() {
        skuSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sku = parseUserInput(skuField, skuErrorLabel, "sku");
                String store = (String) storeNames.getSelectedItem();
                try {
                    if (afterTaxRadioButton.isSelected()) {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(search.selectBySKU(sku, true, store))));
                    } else {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(search.selectBySKU(sku, false, store))));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        nameSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String store = (String) storeNames.getSelectedItem();
                try {
                    if (afterTaxRadioButton.isSelected()) {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(search.selectByName(name, true, store))));
                    } else {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(search.selectByName(name, false, store))));

                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                skuErrorLabel.setVisible(false);
            }
        });

        typeSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subType = (String) subTypes.getSelectedItem();
                String store = (String) storeNames.getSelectedItem();
                String type = ViewUtils.getSelectedButtonText(typeGroupButtons);
                try {
                    if (afterTaxRadioButton.isSelected()) {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(
                                        search.selectByType(subType, type, store, true))));
                    } else {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(
                                        search.selectByType(subType, type, store, false))));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                skuErrorLabel.setVisible(false);
            }
        });
    }


    private void createRadioButtonListeners() {
        beerRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (beerRadioButton.isSelected()) {
                    populateTypeDropDown(beerRadioButton.getText().trim());
                }
            }
        });

        wineRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wineRadioButton.isSelected()) {
                    populateTypeDropDown(wineRadioButton.getText().trim());
                }
            }
        });
    }


    private ButtonGroup setAndGroupButtons(JRadioButton brother, JRadioButton sister) {
        ButtonGroup group = new ButtonGroup();
        group.add(brother);
        group.add(sister);
        brother.setSelected(true);
        return group;
    }

    private void populateStoreDropDown() {
        try {
            Vector<String> stores = search.getStoreNames();
            for (int i = 0; i < stores.size(); i++) {
                storeNames.addItem(stores.get(i));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTypeDropDown(String type) {
        subTypes.removeAllItems();
        try {
            Vector<String> types = search.getSubTypeNames(type);
            for (int i = 0; i < types.size(); i++) {
                subTypes.addItem(types.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setTableInScrollPane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        resultsPane.setViewportView(table);
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


    public JPanel getSearchStockPanel() {
        return searchStockPanel;
    }

}
