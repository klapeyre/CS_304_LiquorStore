package View;

import SQL.SQLSearchStock;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Comparator;
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
    private JButton beerSearchButton;
    private JComboBox beerTypes;
    private JLabel taxLabel;
    private JComboBox wineSubTypes;
    private JButton wineSearchButton;
    private SQLSearchStock search;

    public SearchStock() {
        search = new SQLSearchStock();
        skuErrorLabel.setVisible(false);
        setAndGroupButtons(beforeTaxRadioButton, afterTaxRadioButton); // allows user to only select one button at a time
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
                        setTableSorterInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(search.selectBySKU(sku, true, store))));
                    } else {
                        setTableSorterInScrollPane(new JTable
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
                        setTableSorterInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(search.selectByName(name, true, store))));
                    } else {
                        setTableSorterInScrollPane(new JTable
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
                        setTableSorterInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(
                                        search.selectByBeer(type, store, true))));
                    } else {
                        setTableSorterInScrollPane(new JTable
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
                        setTableSorterInScrollPane(new JTable
                                (ViewUtils.buildResultsTableModel(
                                        search.selectByWine(type, store, true))));
                    } else {
                        setTableSorterInScrollPane(new JTable
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

    private void setAndGroupButtons(JRadioButton brother, JRadioButton sister) {
        ButtonGroup group = new ButtonGroup();
        group.add(brother);
        group.add(sister);
        brother.setSelected(true);
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

    private void setTableSorterInScrollPane(JTable table) {
        setTableSorter(table);
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        resultsPane.setViewportView(table);
    }

    private void setTableSorter(JTable table) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        // sort first and last two columns - they will always be BigDecimals
        sorter.setComparator(table.getColumnCount() - 1, new IntComparator());
        sorter.setComparator(table.getColumnCount() - 2, new IntComparator());
        sorter.setComparator(0, new IntComparator());
        table.setRowSorter(sorter);
    }


    class IntComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            BigDecimal num1 = (BigDecimal) o1;
            BigDecimal num2 = (BigDecimal) o2;
            num1 = num1.setScale(0, RoundingMode.HALF_UP);
            num2 = num2.setScale(0, RoundingMode.HALF_UP);
            Integer int1 = num1.intValueExact();
            Integer int2 = num2.intValueExact();
            return int1.compareTo(int2);
        }

        public boolean equals(Object o2) {
            return this.equals(o2);
        }
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
