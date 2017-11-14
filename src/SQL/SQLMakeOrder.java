package SQL;

import java.sql.*;

public class SQLMakeOrder {
    private final Connection con;
    private int orderNumber;

    public SQLMakeOrder(){
        con = DatabaseConnection.getConnection();

    }

    public void makeOrder(String supplier, int employeeId) {
        //TODO assuming date received is the same as date placed
        Timestamp datePlaced = new Timestamp(System.currentTimeMillis());
        Timestamp dateReceived = new Timestamp(System.currentTimeMillis());

        PreparedStatement PS;





    }

    private void addItemToOrder(int sku, int qty){
        PreparedStatement ps;
        try{
            ps = con.prepareStatement("INSERT INTO orderitems VALUES (?,seq_orderNum.CURRVAL,?)");
            ps.setInt(1, sku);
            ps.setInt(2, qty);
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e){
            try {
                con.rollback();
            } catch (SQLException e2) {
                //TODO handle this exception
            }
        }
    }






}
