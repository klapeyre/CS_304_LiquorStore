package SQL;

import java.sql.*;

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
        ps = conn.prepareStatement("SELECT * " +
                                        "FROM store_sales s " +
                                        "WHERE s.sale_date >= ? AND s.sale_date <= ?");
        ps.setDate(1,sDate);
        ps.setDate(2,eDate);
        return ps.executeQuery();
    }

    public ResultSet searchByDateAndEmployee(Date sDate, Date eDate, int employeeID) throws SQLException {
        PreparedStatement ps;
        ps = conn.prepareStatement("SELECT * " +
                                        "FROM store_sales s " +
                                        "WHERE s.sale_date >= ? AND s.sale_date <= ? + s.employee_id = ?");
        ps.setDate(1,sDate);
        ps.setDate(2,eDate);
        ps.setInt(3,employeeID);
        return ps.executeQuery();
    }


}
