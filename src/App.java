import javax.swing.*;
import View.MainScreen;
import SQL.DatabaseConnection;

public class App {

    public static void main(String[] args) {
        DatabaseConnection database = new DatabaseConnection();
        database.establishConnection("ora_i4n0b", "a17677089");
        JFrame frame = new JFrame("Cool App");
        frame.setContentPane(new MainScreen().getPanelMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        try {// TODO remove eventually
            Thread.sleep(6000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
