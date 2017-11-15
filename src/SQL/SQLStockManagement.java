package SQL;
import java.io.IOException;
import java.sql.*;

public class SQLStockManagement {
    private final Connection con;

    public SQLStockManagement() {
        con = DatabaseConnection.getConnection();
    }

    private void insertBeer(int sku, String name, double tax, double deposit,
                            double price, String company, String region, double percentage,
                            String type, double volume, String description, int quantity) {

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

    public void removeItem(Integer sku) throws SQLException {
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("DELETE FROM items WHERE sku = ?");
            ps.setInt(1, sku);

            int rowCount = ps.executeUpdate();

            if (rowCount == 0) {
                throw new UnsupportedOperationException();
            }
            con.commit();
            ps.close();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e2) {
                System.out.println("Message: " + e2.getMessage());
                System.exit(-1);
            }
            throw e;
        }
    }

    public void updateDescription(Integer sku, String description) throws SQLException {
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("UPDATE items SET description = ? WHERE sku = ?");
            ps.setString(1, description);
            ps.setInt(2, sku);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                throw new UnsupportedOperationException();
            }
            con.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Updating description failed. Message: "+e.getMessage());
            try
            {
                // undo the insert
                con.rollback();
            } catch (SQLException e2) {
                System.out.println("Message: " + e2.getMessage());
                System.exit(-1);
            }
            throw e;
        }
    }

    public void updateQuantity(Integer sku, Integer quantity, Integer storeID) throws SQLException {
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("UPDATE storeitems SET stock_quantity = ? WHERE sku = ? AND store_id = ?");
            ps.setInt(1, quantity);
            ps.setInt(2, sku);
            ps.setInt(3, storeID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                throw new UnsupportedOperationException();
            }
            con.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Updating quantity failed. Message: "+e.getMessage());
            try
            {
                // undo the insert
                con.rollback();
            } catch (SQLException e2) {
                System.out.println("Message: " + e2.getMessage());
                System.exit(-1);
            }
            throw e;
        }
    }


}