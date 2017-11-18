package View;

import SQL.SQLViewEmployees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ViewEmployees {
    private JPanel VEPanel;
    private JButton searchButton;
    private JTextField employeeIDField;
    private JLabel employeeLabel;
    private JLabel errorLabel;
    private JScrollPane resultsPane;
    private JRadioButton managerRadioButton;
    private JRadioButton clerkRadioButton;
    private ButtonGroup employeeButtons;
    private SQLViewEmployees viewEmployees;

    public ViewEmployees() {
        employeeButtons = setAndGroupButtons(managerRadioButton, clerkRadioButton);
        viewEmployees = new SQLViewEmployees();
        errorLabel.setVisible(false);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = parseUserInput(employeeIDField, errorLabel, "ID");
                try {
                    if (managerRadioButton.isSelected()) {
                        setTableInScrollPane(new JTable(
                                ViewUtils.buildResultsTableModel(viewEmployees.searchById(id, true))));
                    } else {
                        setTableInScrollPane(new JTable(
                                ViewUtils.buildResultsTableModel(viewEmployees.searchById(id, false))));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
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

    public JPanel getViewEmployeesPanel() {
        return VEPanel;
    }
}
