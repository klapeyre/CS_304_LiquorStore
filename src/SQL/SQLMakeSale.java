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

    public void makeSale(Integer[][] data, Integer storeID, Integer employeeID) throws SQLException {

        Integer saleNumber = makeNewSale();

        for (int i = 0; i < data.length; i++ ) {
            Integer sku = data[i][0];
            Integer quantity = data[i][1];
            addSaleAndItem(sku, saleNumber, quantity);
            Integer newQuantity = itemQuantityAtStore(sku, storeID) - quantity;
            updateStockQuantity(sku, newQuantity, storeID);
        }
    }

    private Integer makeNewSale(){
        return 100;//todo
    }

    private void updateStockQuantity(Integer sku, Integer newQuantity, Integer storeID) throws SQLException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("UPDATE storeitems SET stock_quantity = ? WHERE sku = ? AND store_id = ?");
            ps.setInt(1, newQuantity);
            ps.setInt(2, sku );
            ps.setInt(3, storeID);

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Update stock quantity failed. Message: "+e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
            throw e;
        }
    }

    private void addSaleAndItem(Integer sku, Integer saleNumber, Integer quantity) throws SQLException {
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("INSERT INTO saleitems VALUES (?,?,?)");
            ps.setInt(1, sku);
            ps.setInt(2, saleNumber);
            ps.setInt(3, quantity);

            ps.executeUpdate();
            con.commit();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Add sale/item to saleitems failed. Message: "+e.getMessage());
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