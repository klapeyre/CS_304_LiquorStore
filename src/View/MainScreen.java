package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.EmployeeManagement;
import View.StockManagement;

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
                JFrame frame = new JFrame("Stock Management");
                frame.setContentPane(new StockManagement().getPanelSM());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        searchStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Search Stock");
                frame.setContentPane(new SearchStock().getSearchStockPanel());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        manageEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Employee Management");
                frame.setContentPane(new EmployeeManagement().getPanelEM());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("View Sales");
                frame.setContentPane(new ViewSales().getPanelVS());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public JPanel getPanelMain() {
        return panelMain;
    }
}
