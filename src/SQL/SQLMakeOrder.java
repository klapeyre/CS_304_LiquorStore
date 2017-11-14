package SQL;

import java.sql.*;

public class SQLMakeOrder {
    private final Connection con;
    private int orderNumber;

    public SQLMakeOrder(){
        con = DatabaseConnection.getConnection();

    }

    public void makeOrder(int sku, int qty, String supplier, int employeeId) {
        //TODO assuming date received is the same as date placed
        PreparedStatement ps;
        Timestamp datePlaced = new Timestamp(System.currentTimeMillis());
        Timestamp dateReceived = new Timestamp(System.currentTimeMillis());

        try{
            ps = con.prepareStatement("INSERT INTO orders VALUES (seq_orderNum.NEXTVAL, ?, ?, ?, ?)");
            ps.setString(1, supplier);
            ps.setTimestamp(2, datePlaced);
            ps.setTimestamp(3, dateReceived);
            ps.setInt(4, employeeId);
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch(SQLException e){
            // TODO hanlde this exception
        }
        addItemToOrder(sku, qty);
        //TODO update stock
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
