package SQL;

import View.ViewUtils;

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
            System.exit(-1);
        }
        return quantity;
    }

    public Integer makeSale(Integer[][] data, Integer storeID, String paymentType, Integer employeeID) throws SQLException {

        Double totalPrice = getTotalPrice(data);
        Integer saleNumber = makeNewSale(totalPrice, paymentType, employeeID);

        for (int i = 0; i < data.length; i++ ) {
            Integer sku = data[i][0];
            Integer quantity = data[i][1];
            addSaleAndItem(sku, saleNumber, quantity);
            Integer newQuantity = itemQuantityAtStore(sku, storeID) - quantity;
            updateStockQuantity(sku, newQuantity, storeID);
        }
        return saleNumber;
    }

    private Double getTotalPrice(Integer[][] data) throws SQLException {
        Double totalPrice = 0.0;
        for (int i = 0; i < data.length; i++ ) {
            Integer sku = data[i][0];
            Integer quantity = data[i][1];
            totalPrice = totalPrice + getPrice(sku, quantity);
        }
        return totalPrice;
    }

    private Double getPrice(Integer sku, Integer quantity) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        Double tax = 0.0;
        Double deposit = 0.0;
        Double price = 0.0;

        try {
            ps = con.prepareStatement("SELECT tax, deposit, price FROM items WHERE sku = ?");
            ps.setInt(1, sku);
            rs = ps.executeQuery();

            while(rs.next()) {
                tax = rs.getDouble("tax");
                deposit = rs.getDouble("deposit");
                price = rs.getDouble("price");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Could not get price of item. Message: " + e.getMessage());
            throw e;
        }
        return ((price*tax)+price+deposit)*quantity;
    }

    private Integer makeNewSale(Double totalPrice, String paymentType,Integer employeeID) throws SQLException {
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("INSERT INTO store_sales VALUES (seq_id.nextval,?,?,?,?)");
            ps.setDouble(1, totalPrice);
            ps.setString(2, paymentType);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setInt(4, employeeID);

            ps.executeUpdate();
            con.commit();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Making new sale failed. Message: "+e.getMessage());
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
