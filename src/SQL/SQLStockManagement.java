package SQL;
import java.io.IOException;
import java.sql.*;

public class SQLStockManagement {

    // Need to figure out how we're going to handle different item tables
    // assuming Beer for now

    public SQLStockManagement() {

    }

    private void insertBeer(int sku, String name, double tax, double deposit,
                            double price, String company, String region, double percentage,
                            String type, double volume, String description, int quantity) {

        Connection con = DatabaseConnection.getConnection(); // Need error handling if connection does not exist
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("INSERT INTO Beer VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            // review foreign key and not-null requirements
            ps.setInt(1, sku);
            ps.setString(2, name);
            ps.setDouble(3, tax);
            ps.setDouble(4, deposit);
            ps.setDouble(5, price);
            ps.setString(6, company);
            ps.setString(7, region);
            ps.setDouble(8, percentage);
            ps.setString(9, type);
            ps.setDouble(10, volume);
            ps.setString(11, description);
            ps.setInt(12, quantity);

            // execute statement
            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());    // change to error box in GUI
            try {
                // undo the insert
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }
}