package SQL;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;

public class SQLViewSales {

    private final Connection conn;

    public SQLViewSales() {
        conn = DatabaseConnection.getConnection();
    }

    public ResultSet searchByID(int saleID) throws SQLException {
        PreparedStatement ps;
        ps = conn.prepareStatement("SELECT * " +
                                        "FROM store_sales s " +
                                        "WHERE s.sale_number = ?");
        ps.setInt(1,saleID);
        ResultSet results = ps.executeQuery();

        return results;
    }

    public ResultSet searchByDate(Date sDate, Date eDate) throws SQLException {
        PreparedStatement ps;
        if (sDate.toString().equals(eDate.toString())) {
            String date = sDate.toString().substring(2,sDate.toString().length());
            date = '%' + date + '%';
            ps = conn.prepareStatement("SELECT * " +
                                            "FROM store_sales s " +
                                            "WHERE s.sale_date LIKE ? ORDER BY s.sale_date");
            ps.setString(1,date);
        } else {
            String endDate = eDate.toString().substring(2,eDate.toString().length());
            endDate = '%' + endDate + '%';
            ps = conn.prepareStatement("SELECT * " +
                    "FROM store_sales s " +
                    "WHERE s.sale_date >= ? AND s.sale_date <= ? OR s.sale_date LIKE ? ORDER BY s.sale_date");
            ps.setDate(1,sDate);
            ps.setDate(2,eDate);
            ps.setString(3,endDate);
        }
        return ps.executeQuery();
    }

    public ResultSet searchByDateAndEmployee(Date sDate, Date eDate) throws SQLException {
        PreparedStatement ps;
        ps = conn.prepareStatement("SELECT SUM(s.total_price) as Total_Sales, s.employee_id " +
                                        "FROM store_sales s " +
                                        "WHERE s.sale_date >= ? AND s.sale_date <= ? GROUP BY s.employee_id");
        ps.setDate(1,sDate);
        ps.setDate(2,eDate);
        return ps.executeQuery();
    }

    public ResultSet findBestSellers() throws SQLException {
        PreparedStatement ps;
        ps = conn.prepareStatement("SELECT SUM(s.quantity) as Total_Quantity, s.sku as SKU, i.name as Item_Name " +
                                        "FROM saleitems s, items i " +
                                        "WHERE s.sku = i.sku GROUP BY s.sku, i.name ORDER BY Total_Quantity DESC");
        return ps.executeQuery();
    }


}
