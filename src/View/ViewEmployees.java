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
    private SQLViewEmployees viewEmployees;

    public ViewEmployees() {
        viewEmployees = new SQLViewEmployees();
        errorLabel.setVisible(false);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = parseUserInput(employeeIDField, errorLabel, "ID");
                try {
                    setTableInScrollPane(new JTable(
                            ViewUtils.buildResultsTableModel(viewEmployees.searchById(id))
                    ));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
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
