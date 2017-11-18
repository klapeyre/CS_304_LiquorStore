package SQL;

import javax.swing.*;
import java.sql.*;

public class SQLGenerateReports {
    private final Connection con;

    public SQLGenerateReports(){
        con = DatabaseConnection.getConnection();
    }

    private int addOrdersToReport(ResultSet rs){

    }

    public void generateWagesReport(int storeId, Date startDate, Date endDate){


    }

    public void generateOrdersReport(int storeId, Date startDate, Date endDate){
        PreparedStatement ps;
        ResultSet rs;
        try{
            ps = con.prepareStatement("SELECT O.TIME_DATE_PLACED as \"Time placed\", " +
                                            "O.TIME_DATE_RECEIVED as \"Time received\", " +
                                            "SUM(OI.QUANTITY * I.PRICE) AS \"ORDER TOTAL\" " +
                                            "FROM ORDERITEMS OI, ORDERS O, EMPLOYEES E, ITEMS I " +
                                            "WHERE E.STORE_ID = 1 AND " +
                                            "O.EMPLOYEE_ID = E.EMPLOYEE_ID AND " +
                                            "O.ORDER_NUMBER = OI.ORDER_NUMBER AND " +
                                            "I.SKU = OI.SKU AND " +
                                            "O.TIME_DATE_RECEIVED IS NOT NULL " +
                                            "GROUP BY O.TIME_DATE_PLACED , O.TIME_DATE_RECEIVED");
            rs = ps.executeQuery();
            ps.close();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Failed to obtain list of orders to include in report");
            e.printStackTrace();
            return;
        }

        addOrdersToReport(ResultSet rs);
    }

    public void generareSalesReport(int storeId, Date startDate, Date endDate){

    }

    public ResultSet viewWagesReport(int storeId, Date startDate, Date endDate){
        ResultSet result;

        return result;
    }

    public ResultSet viewOrdersReport(int storeId, Date startDate, Date endDate){
        ResultSet result;

        return result;
    }

    public ResultSet viewSalesReport(int storeId, Date startDate, Date endDate){
        ResultSet result;

        return result;
    }

}
