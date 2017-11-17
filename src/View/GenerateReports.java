package View;

import javax.swing.*;

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
    private JTextField GenerateStartDateField;
    private JTextField GenerateEndDateField;
    private JTextField GenerateStoreIdField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField storeIdField;
    private JLabel startDateLabel;
    private JLabel storeIdLabel;
    private JLabel endDateLabel;
    private JLabel GenerateStartDateLabel;
    private JLabel GenerateEndDateLabel;
    private JLabel GenerateStoreIdLabel;
    private JRadioButton wagesRadioButton;
    private JRadioButton ordersRadioButton;
    private JRadioButton salesRadioButton;
    private JPanel reportTypePanel;


    public JPanel getGenerateReportsMainPanel() {
        return GenerateReportsMainPanel;
    }
}
