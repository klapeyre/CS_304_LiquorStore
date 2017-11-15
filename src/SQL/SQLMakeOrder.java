package SQL;

import View.ViewUtils;
import java.sql.*;

public class SQLMakeOrder {
    private final Connection con;

    public SQLMakeOrder(){
        con = DatabaseConnection.getConnection();
    }

    private void addItemToOrder(int sku, int qty){
        PreparedStatement ps;
        try{
            ps = con.prepareStatement("INSERT INTO ORDERITEMS VALUES (?, ?, ?)");
            ps.setInt(1, sku);
            ps.setInt(2, ViewUtils.getSequenceNumber(con));
            ps.setInt(3, qty);
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e){
            try {
                con.rollback();
            } catch (SQLException e2) {
                // May happen due to a database error or a closed connection, so a restart may fix it
                System.out.println("Failed to roll back adding an item to the order, aborting....");
                DatabaseConnection.closeConnection();
                System.exit(-1);
            }
        }
    }

    private int findStoreId (int orderNumber){
        PreparedStatement ps;
        ResultSet resultSet;
        int storeId = 0;

        try {
            ps = con.prepareStatement("select distinct si.store_id" +
                    "from storeitems si, employees e" +
                    "where si.store_id = e.store_id AND e.employee_id in" +
                    "(select e.employee_id" +
                    "from employees e, orders o" +
                    "where o.employee_id = e.employee_id AND o.order_number = ?)");

            ps.setInt(1, orderNumber);
            resultSet = ps.executeQuery();
            storeId = resultSet.getInt(1);
        }  catch (SQLException e){
            System.out.println("Failed to retrieve store ID");
            try{
                con.rollback();
            } catch (SQLException e2){
                e2.printStackTrace();
                System.exit(-1);
            }
        }
        return storeId;
    }

    private void updateStockQuantity(int sku, int storeId, int newQuantity){
        PreparedStatement ps;
        try{
            ps = con.prepareStatement("UPDATE STOREITEMS SET STOCK_QUANTITY = ? WHERE SKU = ? AND STORE_ID = ?");
            ps.setInt(1, newQuantity);
            ps.setInt(2, sku);
            ps.setInt(3, storeId);
            con.commit();
            ps.close();
        } catch (SQLException e){
            System.out.println("Failed to update stock quantity for sku: " + sku);
            try{
                con.rollback();
            } catch (SQLException e2){
                e2.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private void itemsToUpdate(int storeId, int orderNumber){
        PreparedStatement ps;
        ResultSet storeItemsInOrder;

        try{
            ps = con.prepareStatement("SELECT SI.SKU, SI.STOCK_QUANTITY" +
                                           "FROM STOREITEMS SI, ORDERITEMS OI" +
                                           "WHERE OI.SKU = SI.SKU AND OI.ORDER_NUMBER = ? AND SI.STORE_ID = ?");
            ps.setInt(1, orderNumber);
            ps.setInt(2, storeId);

            storeItemsInOrder = ps.executeQuery();

        } catch (SQLException e){

        }

    }

    public void makeOrder(int sku, int qty, String supplier, int employeeId) {
        PreparedStatement ps;
        Timestamp datePlaced = new Timestamp(System.currentTimeMillis());

        try{
            ps = con.prepareStatement("INSERT INTO orders VALUES (seq_id.NEXTVAL, ?, ?, NULL, ?)");
            ps.setString(1, supplier);
            ps.setTimestamp(2, datePlaced);
            ps.setInt(3, employeeId);
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch(SQLException e){
            // TODO hanlde this exception
        }
        addItemToOrder(sku, qty);
    }

    public void markOrderAsReceived(int orderNumber, Timestamp receiveDate){
        PreparedStatement ps;
        try{
            ps = con.prepareStatement("UPDATE ORDERS SET TIME_DATE_RECEIVED = ? WHERE ORDER_NUMBER = ? ");
            ps.setTimestamp(1, receiveDate);
            ps.setInt(2, orderNumber);
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e){
            try{
                con.rollback();
            } catch (SQLException e2){
                System.out.println("Failed to update order received date, aborting....");
            }
        }
    }
}
