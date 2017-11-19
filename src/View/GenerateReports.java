package View;

import SQL.SQLGenerateReports;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import static View.ViewUtils.parseUserInput;

public class GenerateReports {
    private JPanel GenerateReportsMainPanel;
    private JScrollPane ResultsPanel;
    private JPanel DateEntryPanel;
    private JPanel GenerateReportsPanel;
    private JPanel ViewReportsPanel;
    private JPanel GenerateReportsLabels;
    private JPanel GenerateReportsFields;
    private JPanel ViewReportsLabels;
    private JPanel ViewReportsFields;
    private JLabel viewStartDateLabel;
    private JLabel viewStoreIdLabel;
    private JLabel viewEndDateLabel;
    private JLabel generateStartDateLabel;
    private JLabel generateEndDateLabel;
    private JLabel generateStoreIdLabel;
    private JRadioButton wagesRadioButton;
    private JRadioButton ordersRadioButton;
    private JRadioButton salesRadioButton;
    private JPanel reportTypePanel;
    private JButton generateReportButton;
    private JButton viewReportButton;
    private JLabel generateStoreIdErrorLabel;
    private JLabel viewStoreIdErrorLabel;
    private JFormattedTextField generateEndDateField;
    private JFormattedTextField viewStartDateField;
    private JFormattedTextField viewEndDateField;
    private JFormattedTextField viewStoreIdField;
    private JFormattedTextField generateStartDateField;
    private JFormattedTextField generateStoreIdField;
    private SQLGenerateReports sqlGenerateReports;

    public GenerateReports(){
        generateStoreIdErrorLabel.setVisible(false);
        generateReportsActionListeners();
        sqlGenerateReports = new SQLGenerateReports();
    }

    public void generateReportsActionListeners(){
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int storeId = parseUserInput(generateStoreIdField, generateStoreIdErrorLabel, "Store ID");
                String start_Date = generateStartDateField.getText();
                String end_Date = generateEndDateField.getText();
                Date startDate = Date.valueOf(start_Date);
                Date endDate = Date.valueOf(end_Date);

                if(wagesRadioButton.isSelected()){
                    sqlGenerateReports.generateWagesReport(storeId, startDate, endDate);
                }
                else if(ordersRadioButton.isSelected()){
                    sqlGenerateReports.generateOrdersReport(storeId, startDate, endDate);
                }
                else if(salesRadioButton.isSelected()){
                    sqlGenerateReports.generateSalesReport(storeId, startDate, endDate);
                }
            }
        });

        viewReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int storeId = parseUserInput(viewStoreIdField, viewStoreIdErrorLabel, "Store ID");
                String start_Date = viewStartDateField.getText();
                String end_Date = viewEndDateField.getText();
                Date startDate = Date.valueOf(start_Date);
                Date endDate = Date.valueOf(end_Date);

                if(wagesRadioButton.isSelected()){
                    setTableInScrollPane(sqlGenerateReports.viewWagesReport(storeId, startDate, endDate));
                }
                else if(ordersRadioButton.isSelected()){
                        setTableInScrollPane(sqlGenerateReports.viewOrdersReport(storeId, startDate, endDate));
                }
                else if(salesRadioButton.isSelected()){
                    setTableInScrollPane(sqlGenerateReports.viewSalesReport(storeId, startDate, endDate));
                }
            }
        });
    }

    private void setTableInScrollPane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        ResultsPanel.setViewportView(table);
    }

    public JPanel getGenerateReportsMainPanel() {
        return GenerateReportsMainPanel;
    }
}
