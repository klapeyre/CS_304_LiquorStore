package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockManagement {
    private JTextField nameTextField;
    private JPanel panelSM;
    private JTextField taxTextField;
    private JTextField depositTextField;
    private JTextField priceTextFieldTextField;
    private JTextField descriptionTextField;
    private JButton addNewItemButton;
    private JTextField storeIDTextField;
    private JTextField alcoholPercentageTextField;
    private JTextField packQuantityTextField;
    private JTextField typeTextField;
    private JTextField regionTextField;
    private JTextField companyTextField;
    private JTextField subtypeTextField;
    private JRadioButton beerRadioButton;
    private JRadioButton wineRadioButton;
    private JTextField removeItemTextField;
    private JButton removeItemButton;
    private JTextField quantityTextField;

    public StockManagement() {
        addNewItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                double tax = Double.parseDouble(taxTextField.getText());
                double deposit = Double.parseDouble(depositTextField.getText());
                double price = Double.parseDouble(priceTextFieldTextField.getText());
                String description = descriptionTextField.getText();
                String storeID = storeIDTextField.getText();
                double percentage = Double.parseDouble(alcoholPercentageTextField.getText());
                String type = typeTextField.getText();
                String region = regionTextField.getText();
                String company = companyTextField.getText();
                int quantity = Integer.parseInt(quantityTextField.getText());

                System.out.println(name+" "+tax+" "+deposit+" "+price+" "+description+" "+storeID+" "+percentage+" "+type+" "+region+" "+company+" "+quantity);
                // TODO instantiate a ManageStock object here, using input data obtained from GUI

                if (beerRadioButton.isSelected()){
                    int packQuantity = Integer.parseInt(packQuantityTextField.getText());
                    //TODO insert beer
                    //insertBeer(name, tax, deposit, price, description, storeID, percentage, type, region, company, packQuantity);
                    //TODO check if everything was fine then
                    JOptionPane.showMessageDialog(null, "New beer was added!");
                } else if (wineRadioButton.isSelected()){
                    String subtype = subtypeTextField.getText();
                    //TODO insert wine
                    //insertWine(name, tax, deposit, price, description, storeID, percentage, type, region, company, subtype); //Don't forget to insert into Item table too
                    //TODO check if everything was fine then
                    JOptionPane.showMessageDialog(null, "New wine was added!");
                } else {
                    //TODO insert nonalcoholic
                    //insertNonAlcoholicItem(name, tax, deposit, price, description, storeID);
                    //TODO check if everything was fine then
                    JOptionPane.showMessageDialog(null, "New item was added!");
                }
            }
        });
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemToRemoveID = removeItemTextField.getText();
                //TODO remove
                //removeItem(itemToRemoveID);
                //TODO check if everything was fine then
                JOptionPane.showMessageDialog(null, "Item was removed!");
            }
        });
    }

    public JPanel getPanelSM() {
        return panelSM;
    }
}
