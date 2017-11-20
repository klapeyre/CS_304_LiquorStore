import javax.swing.*;
import View.MainScreen;
import SQL.DatabaseConnection;

public class App {

    public static void main(String[] args) {
        DatabaseConnection database = new DatabaseConnection();
        database.establishConnection("ora_n1j8", "a47906102");
        JFrame frame = new JFrame("Liquor Store App");
        frame.setContentPane(new MainScreen().getPanelMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
