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
    private JButton beerSearchButton;
    private JComboBox beerTypes;
    private JLabel taxLabel;
    private JComboBox wineSubTypes;
    private JButton wineSearchButton;
    private SQLSearchStock search;

    public SearchStock() {
        search = new SQLSearchStock();
        skuErrorLabel.setVisible(false);
        taxGroupButtons = setAndGroupButtons(beforeTaxRadioButton, afterTaxRadioButton); // allows user to only select one button at a time
        populateStoreDropDown();
        populateTypeDropDown("Beers");
        populateTypeDropDown("Wines");
        createSearchButtonListeners();
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

        beerSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) beerTypes.getSelectedItem();
                String store = (String) storeNames.getSelectedItem();
                try {
                    if (afterTaxRadioButton.isSelected()) {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(
                                        search.selectByBeer(type, store, true))));
                    } else {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(
                                        search.selectByBeer(type, store, false))));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                skuErrorLabel.setVisible(false);
            }
        });

        wineSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) wineSubTypes.getSelectedItem();
                String store = (String) storeNames.getSelectedItem();
                try {
                    if (afterTaxRadioButton.isSelected()) {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(
                                        search.selectByWine(type, store, true))));
                    } else {
                        setTableInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(
                                        search.selectByWine(type, store, false))));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                skuErrorLabel.setVisible(false);
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
        try {
            Vector<String> types = search.getTypeNames(type);
            if (type.equals("Beers")) {
                for (int i = 0; i < types.size(); i++) {
                    beerTypes.addItem(types.get(i));
                }
            } else {
                for (int i = 0; i < types.size(); i++) {
                    wineSubTypes.addItem(types.get(i));
                }
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
