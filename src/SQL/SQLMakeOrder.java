package SQL;

import View.ViewUtils;
import oracle.sql.TIMESTAMP;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class SQLMakeOrder {
    private final Connection con;

    public SQLMakeOrder(){
        con = DatabaseConnection.getConnection();
    }

    private int checkItemExists(int sku, int displayMessageOption){
        PreparedStatement ps;
        boolean itemExists = false;
        try{
            // check if item sku exists
            ps = con.prepareStatement("SELECT DISTINCT SKU FROM STOREITEMS WHERE SKU = ?");
            ps.setInt(1, sku);
            ResultSet item = ps.executeQuery();
            while(item.next()){
                itemExists = true;
            }
            if(!itemExists){
                if (displayMessageOption == 1) {
                    JOptionPane.showMessageDialog(null, "Item #" + sku + " does not exist \n" +
                            "press OK to continue with the rest of this order");
                }
                return 0;
            }
        } catch (SQLException e){
            System.out.println("Failed to check if SKU #" + sku + " exists");
            e.printStackTrace();
            System.exit(-1);
        }
        return 1;
    }

    private void addItemToOrder(int sku, int qty){
        if (checkItemExists(sku, 0) == 0){
            return;
        }

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
                e2.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private int findStoreId (int orderNumber){
        PreparedStatement ps;
        ResultSet resultSet;
        int storeId = 0;
        int employeeId = 0;

        try {
            ps = con.prepareStatement("SELECT E.EMPLOYEE_ID FROM EMPLOYEES E, ORDERS O " +
                                           "WHERE O.EMPLOYEE_ID = E.EMPLOYEE_ID AND O.ORDER_NUMBER = ?");

            ps.setInt(1, orderNumber);
            resultSet = ps.executeQuery();
            while(resultSet.next()){
                employeeId = resultSet.getInt(1);
            }

            ps = con.prepareStatement("SELECT DISTINCT SI.STORE_ID FROM STOREITEMS SI, EMPLOYEES E " +
                                           "WHERE SI.STORE_ID = E.STORE_ID AND E.EMPLOYEE_ID = ?");

            ps.setInt(1, employeeId);
            resultSet = ps.executeQuery();
            while (resultSet.next()){
                storeId = resultSet.getInt(1);
            }

        }  catch (SQLException e){
            e.printStackTrace();
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
            ps.executeUpdate();
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

    private int getOrderItemQuantity(int sku, int orderNumber){
        PreparedStatement ps;
        ResultSet rs;
        int qty = 0;
        try{
            ps = con.prepareStatement("SELECT QUANTITY FROM ORDERITEMS WHERE SKU = ? AND ORDER_NUMBER = ?");
            ps.setInt(1, sku);
            ps.setInt(2, orderNumber);
            rs = ps.executeQuery();
            while(rs.next()){
                qty = rs.getInt(1);
            }
            ps.close();
        } catch (SQLException e){
            System.out.println("could not obtain order item quantity");
            e.printStackTrace();
            System.exit(-1);
        }
        return qty;
    }

    private void updateItems(int storeId, int orderNumber){
        PreparedStatement ps;
        ResultSet items;
        try{
            ps = con.prepareStatement("SELECT SI.SKU, SI.STOCK_QUANTITY " +
                                           "FROM STOREITEMS SI, ORDERITEMS OI " +
                                           "WHERE SI.STORE_ID = ? AND OI.SKU = SI.SKU AND OI.ORDER_NUMBER = ?");
            ps.setInt(1, storeId);
            ps.setInt(2, orderNumber);
            items = ps.executeQuery();
            while(items.next()){
                int sku = items.getInt(1);
                int qty = items.getInt(2);
                updateStockQuantity(sku, storeId, qty + getOrderItemQuantity(sku, orderNumber));
            }
            ps.close();

        } catch (SQLException e){
            System.out.println("Failed to update stock quantity for order: " + orderNumber);
            System.exit(-1);
        }
    }

    public int makeOrder(Vector<Vector<Object>> tableContents, int numberOfItems) {
        int orderNum = 0;
        PreparedStatement ps;
        Timestamp datePlaced = new Timestamp(System.currentTimeMillis());

        if(numberOfItems != 0) {
            // if order consists only of a single item that does not exist, then return 0
            int sku = (int) tableContents.get(0).get(0);
            if(numberOfItems == 1 && checkItemExists(sku, 1) == 0){
                return 0;
            }

            String supplier = (String) tableContents.get(0).get(2);
            int employeeId = (int) tableContents.get(0).get(3);
            try {
                ps = con.prepareStatement("INSERT INTO ORDERS VALUES (SEQ_ID.NEXTVAL, ?, ?, NULL, ?)");
                ps.setString(1, supplier);
                ps.setTimestamp(2, datePlaced);
                ps.setInt(3, employeeId);
                ps.executeUpdate();
                con.commit();
                ps.close();
            } catch (SQLException e) {
                System.out.println("Failed to update orders table");
                try {
                    con.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                    System.exit(-1);
                }
            }
        }

        for (Vector<Object> order : tableContents) {
            int sku = (int) order.get(0);
            int qty = (int) order.get(1);
            addItemToOrder(sku, qty);
        }

        try {
            orderNum = ViewUtils.getSequenceNumber(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderNum;
    }

    public void markOrderAsReceived(int orderNumber, Timestamp dateReceived){
        PreparedStatement ps;
        boolean orderExists = false;
        TIMESTAMP orderInfo = null;  // ORACLE.SQL.TIMESTAMP

        try{
            // check if order exists
            ps = con.prepareStatement("SELECT ORDER_NUMBER, TIME_DATE_RECEIVED FROM ORDERS WHERE ORDER_NUMBER = ?");
            ps.setInt(1, orderNumber);
            ResultSet order = ps.executeQuery();
            while(order.next()){
                orderInfo = (TIMESTAMP) order.getObject(2);
                orderExists = true;
            }
            if(!orderExists){
                JOptionPane.showMessageDialog(null, "Order #" + orderNumber + " does not exist");
                return;
            }
            // check if order has already been received by checking the date received Timestamp
            if(orderInfo != null){
                JOptionPane.showMessageDialog(null, "Order #" + orderNumber + " has already been marked as received");
                return;
            }
        } catch (SQLException e){
            System.out.println("Failed to check if order#: " + orderNumber + " exists");
            e.printStackTrace();
            System.exit(-1);
        }

        try{
            ps = con.prepareStatement("UPDATE ORDERS SET TIME_DATE_RECEIVED = ? WHERE ORDER_NUMBER = ? ");
            ps.setTimestamp(1, dateReceived);
            ps.setInt(2, orderNumber);
            ps.executeUpdate();
            con.commit();
            ps.close();
            int storeId = findStoreId(orderNumber);
            updateItems(storeId, orderNumber);
            JOptionPane.showMessageDialog(null, "Order #" + orderNumber + " received on " + dateReceived.toString());
        } catch (SQLException e){
            try{
                con.rollback();
            } catch (SQLException e2){
                System.out.println("Failed to update order received date, aborting....");
                e2.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
