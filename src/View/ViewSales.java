package View;

import SQL.SQLViewSales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class ViewSales extends JFrame {
    private JPanel panelVS;
    private JTextField saleIDtextField;
    private JButton saleIDsearchButton;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JButton searchByDateButton;
    private JScrollPane ResultsTable;
    private JButton searchSaleByEmployeeButton;
    private JButton sortBestSellers;
    private SQLViewSales search;


    public ViewSales() {
        search = new SQLViewSales();
        createSearchButtonListeners();

    }

    private void createSearchButtonListeners() {
        saleIDsearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saleID = saleIDtextField.getText();
                int ID = Integer.parseInt(saleID);

                try {
                    setTableInScrollPane(new JTable
                            (ViewUtils.buildResultsTableModel(search.searchByID(ID))));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateTextField.getText();
                String endDate = endDateTextField.getText();
                Date sDate = Date.valueOf(startDate);
                Date eDate = Date.valueOf(endDate);

                try {
                    setTableInScrollPane(new JTable
                            (ViewUtils.buildResultsTableModel(search.searchByDate(sDate, eDate))));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        searchSaleByEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateTextField.getText();
                String endDate = endDateTextField.getText();
                Date sDate = Date.valueOf(startDate);
                Date eDate = Date.valueOf(endDate);

                try {
                    setTableInScrollPane(new JTable
                            (ViewUtils.buildResultsTableModel(search.searchByDateAndEmployee(sDate, eDate))));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        sortBestSellers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setTableInScrollPane(new JTable
                            (ViewUtils.buildResultsTableModel(search.findBestSellers())));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void setTableInScrollPane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        ResultsTable.setViewportView(table);
    }


    public JPanel getPanelVS() {
        return panelVS;
    }
}
