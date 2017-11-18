package SQL;
import View.ViewUtils;

import java.io.IOException;
import java.sql.*;

public class SQLStockManagement {
    private final Connection con;

    public SQLStockManagement() {
        con = DatabaseConnection.getConnection();
    }

    public Integer insertItem(String name, Double tax, Double deposit,
                            Double price, String description) throws SQLException {

        PreparedStatement ps;

        try {
            ps = con.prepareStatement("INSERT INTO items VALUES (seq_id.nextval,?,?,?,?,?)");
            ps.setString(1, name);
            if (tax == null){
                ps.setNull(2, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(2, tax);
            }
            if (deposit == null){
                ps.setNull(3, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(3, deposit);
            }
            if (price == null){
                ps.setNull(4, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(4, price);
            }
            ps.setString(5, description);

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Inserting item failed. Message: "+e.getMessage());
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
        return ViewUtils.getSequenceNumber(con);
    }

    public int insertBeer(String name, Double tax, Double deposit, Double price, String description, Double percentage,
                          String type, String region, String company, Integer volume, Integer packQuantity) throws SQLException {

        Integer sku = insertItem(name, tax, deposit, price, description);

        PreparedStatement ps;

        try {
            ps = con.prepareStatement("INSERT INTO beers VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, sku);
            ps.setString(2, company);
            ps.setString(3, region);
            if (percentage == null){
                ps.setNull(4, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(4, percentage);
            }
            ps.setString(5, type);
            if (volume == null){
                ps.setNull(6, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(6, volume);
            }
            if (packQuantity == null){
                ps.setNull(7, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(7, packQuantity);
            }

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Inserting beer failed. Message: "+e.getMessage());
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
        return ViewUtils.getSequenceNumber(con);
    }


    public int insertWine(String name, Double tax, Double deposit, Double price, String description, Double percentage,
                          String type, String region, String company, Integer volume, String subtype) throws SQLException {

        Integer sku = insertItem(name, tax, deposit, price, description);

        PreparedStatement ps;

        try {
            ps = con.prepareStatement("INSERT INTO wines VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, sku);
            ps.setString(2, company);
            ps.setString(3, region);
            if (percentage == null){
                ps.setNull(4, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(4, percentage);
            }
            ps.setString(5, type);
            if (volume == null){
                ps.setNull(6, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(6, volume);
            }
            ps.setString(7, subtype);

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Inserting wine failed. Message: "+e.getMessage());
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
        return ViewUtils.getSequenceNumber(con);
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