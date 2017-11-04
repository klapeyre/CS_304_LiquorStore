import javax.swing.*;
import View.MainScreen;

public class App {

    public static void main(String[] args) {
        System.out.print("Hello World ____________");
     //   Database database = new Database();
        JFrame frame = new JFrame("Cool App");
        frame.setContentPane(new MainScreen().getPanelMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        System.out.print("Hello World Kat\n");
//        String tag = "blah blah\n";
//        JFrame f = new JFrame();
//        JPanel p = new JPanel();
//        JLabel l = new JLabel(tag);
//        p.add(l);
//        f.add(p);
//        f.setVisible(true);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
