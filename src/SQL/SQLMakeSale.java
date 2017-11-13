package SQL;

import java.sql.*;

public class SQLMakeSale {
    private final Connection con;

    public SQLMakeSale() { con = DatabaseConnection.getConnection();}

    public boolean itemExists(Integer sku) {
        PreparedStatement  ps;
        ResultSet  rs;
        int count = 0;

        try {
            ps = con.prepareStatement("SELECT count(sku) FROM storeitems WHERE sku = ?");
            ps.setInt(1,sku);
            rs = ps.executeQuery();
            while(rs.next()) {
                count = rs.getInt("count(sku)");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Could not determine count of item. Message: "+e.getMessage());
            System.exit(-1);
        }
        return count > 0;
    }

    public int itemQuantityAtStore(Integer sku, Integer storeID){
        PreparedStatement  ps;
        ResultSet  rs;
        int quantity = 0;
        try {
            ps = con.prepareStatement("SELECT stock_quantity FROM storeitems WHERE sku = ? AND store_id = ?");
            ps.setInt(1, sku);
            ps.setInt(2, storeID);
            rs = ps.executeQuery();
            while(rs.next()) {
                quantity = rs.getInt("stock_quantity");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Could not determine quantity of item. Message: "+e.getMessage());
            System.exit(-1); //TODO what behaviour do we want
        }
        return quantity;
    }








}
