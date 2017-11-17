package SQL;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SQLViewOrders {
    private final Connection con;

    public SQLViewOrders() {
        con = DatabaseConnection.getConnection();
    }

    public ResultSet searchBySKUAndDate(int sku, Date startDate, Date endDate) throws SQLException {
        Date inclusiveEndDate = advanceEndDateOneDay(endDate);
        PreparedStatement ps = con.prepareStatement(
                "SELECT oi.order_number, i.sku, i.name, oi.quantity, i.price as individual_price, i.price * oi.quantity as total_price "
                        + "FROM ORDERS o, ORDERITEMS oi, ITEMS i "
                        + "WHERE oi.sku = ? AND oi.sku = i.sku AND oi.order_number = o.order_number AND o.time_date_placed BETWEEN ? AND ? "
        );
        ps.setInt(1, sku);
        ps.setDate(2, startDate);
        ps.setDate(3, inclusiveEndDate);

        return ps.executeQuery();
    }

    private Date advanceEndDateOneDay(Date endDate) {
        String dt = endDate.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        return Date.valueOf(sdf.format(c.getTime()));
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
