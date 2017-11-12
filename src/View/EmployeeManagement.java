package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import SQL.SQLEmployeeManagement;

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
    private JTextField employeeRemoveTextField;
    private JButton removeEmployeeButton;
    private JTextField employeeChangeSalaryTextField;
    private JTextField newSalaryTextField;
    private JButton changeEmployeeSalaryButton;
    private JLabel storeIDerrorLabel;
    private SQLEmployeeManagement search;


    public EmployeeManagement() {
        search = new SQLEmployeeManagement();

        addNewEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String username = usernameTextField.getText();
                char[] password = passwordField.getPassword();
                Double salary = null;
                int storeID;
                String type = null;

                if (!salaryTextField.getText().equals("")){
                    salary = Double.parseDouble(salaryTextField.getText());
                }

                if (storeIDTextField.getText().equals("")) {
                    storeIDerrorLabel.setVisible(true);
                    errorTimer();
                    return;
                } else {
                    storeID = Integer.parseInt(storeIDTextField.getText());
                }

                if (clerkRadioButton.isSelected()){
                    type = "Clerk";
                } else if (managerRadioButton.isSelected()){
                    type = "Manager";
                }
                System.out.println("Adding "+name+" "+username+" "+password+" "+salary+" "+storeID+" "+type);

                int employeeID = search.insertNewEmployee(name, username, password, salary, storeID, type);
                //TODO check if everything was fine then
                JOptionPane.showMessageDialog(null, "New employee with ID "+employeeID+" was added!");
            }
        });

        removeEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int employeeID = Integer.parseInt(employeeRemoveTextField.getText());
                search.removeEmployee(employeeID);
                //TODO check if everything was fine then
                JOptionPane.showMessageDialog(null, "Employee with ID "+employeeID+" was removed!");
            }
        });

        changeEmployeeSalaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int employeeID = Integer.parseInt(employeeChangeSalaryTextField.getText());
                double newSalary = Double.parseDouble(newSalaryTextField.getText());
                search.changeSalary(employeeID,newSalary);
                JOptionPane.showMessageDialog(null, "Employee's salary was changed!");

            }
        });
    }

    private void errorTimer(){
        Timer t = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeIDerrorLabel.setVisible(false);
            }
        });
        t.setRepeats(false);
        t.start();
    }

    public JPanel getPanelEM() {
        return panelEM;
    }
}
