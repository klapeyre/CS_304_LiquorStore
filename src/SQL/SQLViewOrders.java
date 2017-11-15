package SQL;

import java.sql.*;

public class SQLViewOrders {
    private final Connection con;

    public SQLViewOrders() {
        con = DatabaseConnection.getConnection();
    }

    public ResultSet searchBySKUAndDate(int sku, Date fromDate, Date toDate) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT oi.order_number, i.sku, i.name, oi.quantity, i.price as individual_price, i.price * oi.quantity as total_price "
                        + "FROM ORDERS o, ORDERITEMS oi, ITEMS i "
                        + "WHERE oi.sku = ? AND oi.sku = i.sku AND oi.order_number = o.order_number AND o.time_date_placed BETWEEN ? AND ?"
        );
        ps.setInt(1, sku);
        ps.setDate(2, fromDate);
        ps.setDate(3, toDate);
        return ps.executeQuery();
    }

    public ResultSet searchByOrderNumber(int orderNumber) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT oi.order_number, i.sku, i.name, oi.quantity, i.price as individual_price, i.price * oi.quantity as total_price "
                        + "FROM ORDERS o, ORDERITEMS oi, ITEMS i "
                        + "WHERE o.order_number = ? AND oi.order_number = o.order_number AND oi.sku = i.sku"
        );
        ps.setInt(1, orderNumber);
        return ps.executeQuery();
    }
}
