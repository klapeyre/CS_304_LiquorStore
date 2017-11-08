import javax.swing.*;
import View.MainScreen;
import SQL.Database;

public class App {

    public static void main(String[] args) {
        Database database = new Database();
        JFrame frame = new JFrame("Cool App");
        frame.setContentPane(new MainScreen().getPanelMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        try {// TODO remove eventually
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
