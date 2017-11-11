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
    private JScrollPane resultsPane;
    private SQLViewEmployees viewEmployees;

    public ViewEmployees() {
        viewEmployees = new SQLViewEmployees();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(employeeIDField.getText());
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

    public JPanel getViewEmployeesPanel() {
        return VEPanel;
    }
}
