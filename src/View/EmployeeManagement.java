package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeManagement {
    private JButton addNewEmployeeButton;
    private JPanel panelEM;
    private JTextField nameTextField;
    private JTextField usernameTextField;
    private JTextField salaryTextField;
    private JTextField storeIDTextField;
    private JPasswordField passwordField;
    private JRadioButton clerkRadioButton;
    private JRadioButton managerRadioButton;
    private JTextField employeeToRemove;
    private JButton removeEmployeeButton;


    public EmployeeManagement() {
        addNewEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String username = usernameTextField.getText();
                char[] password = passwordField.getPassword();
                String salary = salaryTextField.getText();
                String storeID = storeIDTextField.getText();
                Integer type = null; //TODO change to string Manager/Clerk?
                if (clerkRadioButton.isSelected()){
                    type = 0;
                } else if (managerRadioButton.isSelected()){
                    type = 1;
                }
                System.out.println(name+" "+username+" "+password+" "+salary+" "+storeID+" "+type);
                //TODO check they are not null-ones that need to be filled
                //insertNewEmployee(name, username, password, salary, storeID, type);
                //TODO check if everything was fine then
                JOptionPane.showMessageDialog(null, "New employee was added!");
            }
        });
        removeEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeID = employeeToRemove.getText();
                //TODO
                //deleteEmployee(employeeID);
                //TODO check if everything was fine then
                JOptionPane.showMessageDialog(null, "Employee was removed!");
            }
        });
    }

    public JPanel getPanelEM() {
        return panelEM;
    }
}
