package SQL;

import View.ViewUtils;

import java.sql.*;

public class SQLMakeOrder {
    private final Connection con;
    private int orderNumber;

    public SQLMakeOrder(){
        con = DatabaseConnection.getConnection();
        try{
            orderNumber = ViewUtils.getSequenceNumber(con);
        } catch (SQLException e){
            // Order number is a primary key, so abort if we fail to automatically acquire it
            System.out.println("Failed to acquire sequence number for order, aborting....");
            DatabaseConnection.closeConnection();
            System.exit(-1);
        }
    }

    private void addItemToOrder(int sku, int qty){
        PreparedStatement ps;
        try{
            ps = con.prepareStatement("INSERT INTO orderitems VALUES (?, ?, ?)");
            ps.setInt(1, sku);
            ps.setInt(2, orderNumber);
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

    private void updateStock(int sku, int qty, int orderNumber){
        PreparedStatement ps;
        ResultSet resultSet;
        int storeId = 0;
        try{
            // find out store id
            ps = con.prepareStatement("select distinct si.store_id" +
                                           "from storeitems si, employees e" +
                                           "where si.store_id = e.store_id AND e.employee_id in" +
                                           "( select e.employee_id" +
                                              "from employees e, orders o" +
                                              "where o.employee_id = e.employee_id AND o.order_number = ? )");
            ps.setInt(1, orderNumber);
            resultSet = ps.executeQuery();

            while(resultSet.next()){
                storeId = resultSet.getInt(1);
            }
            con.commit();
            ps.close();
        } catch (SQLException e){

        }

    }

    public void makeOrder(int sku, int qty, String supplier, int employeeId) {
        //TODO assuming date received is the same as date placed
        PreparedStatement ps;
        Timestamp datePlaced = new Timestamp(System.currentTimeMillis());

        try{
            ps = con.prepareStatement("INSERT INTO orders VALUES (?, ?, ?, NULL, ?)");
            ps.setInt(1, ViewUtils.getSequenceNumber(con));
            ps.setString(2, supplier);
            ps.setTimestamp(3, datePlaced);
            ps.setInt(4, employeeId);
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



    }
}
