package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen {
    private JButton manageStockButton;
    private JPanel panelMain;
    private JButton searchStockButton;
    private JButton manageEmployeesButton;
    private JButton viewEmployeesButton;
    private JButton makeSaleButton;
    private JButton reportsButton;
    private JButton viewSalesButton;
    private JButton makeOrderButton;
    private JButton viewOrdersButton;

    public MainScreen() {
        manageStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello World");
            }
        });
        searchStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getPanelMain() {
        return panelMain;
    }
}
