package SQL;

import View.ViewUtils;

import javax.swing.*;
import java.sql.*;

public class SQLGenerateReports {
    private final Connection con;

    public SQLGenerateReports(){
        con = DatabaseConnection.getConnection();
    }

    private void addReport(ResultSet wages, ResultSet orders, ResultSet sales, int storeId, Date startDate, Date endDate){
        PreparedStatement ps;
        boolean emptyReport = true;
        double totalOrders = 0;
        double totalSales = 0;
        double totalWages = 0;

       try{
           while(wages.next()){
               emptyReport = false;
               totalWages = wages.getDouble(2);
           }
           while(orders.next()){
               emptyReport = false;
               totalOrders = orders.getDouble(2);
           }
           while(sales.next()){
               emptyReport = false;
               totalSales = sales.getDouble(2);
           }
       }catch(SQLException e){
           System.out.println("one or more report fields are empty, moving in with report generation");
       }

        try {
            ps = con.prepareStatement("INSERT INTO REPORTS VALUES (SEQ_ID.NEXTVAL, ?, ?, ?, ?, ?, ?)");
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ps.setInt(3, storeId);
            ps.setDouble(4, totalSales);
            ps.setDouble(5, totalOrders);
            ps.setDouble(6, totalWages);
            ps.executeUpdate();

            if (emptyReport){
                ps = con.prepareStatement("INSERT INTO REPORTS VALUES (SEQ_ID.NEXTVAL, ?, ?, ?, ?, ?, ?)");
                ps.setDate(1, startDate);
                ps.setDate(2, endDate);
                ps.setInt(3, storeId);
                ps.setDouble(4, 0);
                ps.setDouble(5, 0);
                ps.setDouble(6, 0);
                ps.executeUpdate();
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Failed to update reports table");
            try {
                con.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void generateReport(int storeId, Date startDate, Date endDate) {
        long diff = Math.abs(endDate.getTime() - startDate.getTime());
        // include the date a report was generated on in wages
        long numberOfDaysWorked = diff / (24 * 60 * 60 * 1000) + 1;
        PreparedStatement ps;
        ResultSet wages, orders, sales;
        String date = null;
        if (startDate.toString().equals(endDate.toString())) {
            date = startDate.toString().substring(2, startDate.toString().length());
            date = '%' + date + '%';
        }

        try {
            // obtain wages information
            ps = con.prepareStatement("SELECT E.STORE_ID, SUM(E.SALARY * ?) " +
                                            "FROM EMPLOYEES E " +
                                            "WHERE E.STORE_ID = ? " +
                                            "GROUP BY E.STORE_ID");
            ps.setLong(1, numberOfDaysWorked);
            ps.setInt(2, storeId);
            wages = ps.executeQuery();

            // obtain orders information
            ps = con.prepareStatement("SELECT E.STORE_ID, " +
                    "SUM(OI.QUANTITY * I.PRICE) AS \"ORDER TOTAL\" " +
                    "FROM ORDERITEMS OI, ORDERS O, EMPLOYEES E, ITEMS I " +
                    "WHERE E.STORE_ID = ? AND " +
                    "O.EMPLOYEE_ID = E.EMPLOYEE_ID AND " +
                    "O.ORDER_NUMBER = OI.ORDER_NUMBER AND " +
                    "I.SKU = OI.SKU AND " +
                    "O.TIME_DATE_PLACED >= ? AND " +
                    "O.TIME_DATE_RECEIVED IS NOT NULL " +
                    "GROUP BY E.STORE_ID");
            ps.setInt(1, storeId);
            ps.setDate(2, startDate);
            orders = ps.executeQuery();

            // obtain sales information
            if (startDate.toString().equals(endDate.toString())) {
                ps = con.prepareStatement("SELECT E.STORE_ID, " +
                        "SUM(S.TOTAL_PRICE) AS \"SALES TOTAL\" " +
                        "FROM STORE_SALES S, EMPLOYEES E " +
                        "WHERE E.STORE_ID = ? AND " +
                        "S.EMPLOYEE_ID = E.EMPLOYEE_ID AND " +
                        "S.SALE_DATE LIKE ? " +
                        "GROUP BY E.STORE_ID");
                ps.setInt(1, storeId);
                ps.setString(2, date);
                sales = ps.executeQuery();
            } else{
                ps = con.prepareStatement("SELECT E.STORE_ID, " +
                        "SUM(S.TOTAL_PRICE) AS \"SALES TOTAL\" " +
                        "FROM STORE_SALES S, EMPLOYEES E " +
                        "WHERE E.STORE_ID = ? AND " +
                        "S.EMPLOYEE_ID = E.EMPLOYEE_ID AND " +
                        "S.SALE_DATE >= ? AND " +
                        "S.SALE_DATE <= ? " +
                        "GROUP BY E.STORE_ID");
                ps.setInt(1, storeId);
                ps.setDate(2, startDate);
                ps.setDate(3, endDate);
                sales = ps.executeQuery();
            }
            addReport(wages, orders, sales, storeId, startDate, endDate);
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to obtain wages, orders or sales data for the report");
            e.printStackTrace();
            return;
        }
    }

    public JTable viewWagesReport(int storeId, Date startDate, Date endDate){
        PreparedStatement ps;
        ResultSet rs;
        JTable resultsTable = null;
        try{
            ps = con.prepareStatement("SELECT REPORT_ID AS \"REPORT ID\", " +
                                            "STORE_ID AS \"STORE ID\", "+
                                            "ST_DATE AS \"START DATE\", " +
                                            "END_DATE AS \"END DATE\", " +
                                            "TOTAL_WAGES AS \"TOTAL WAGES\" " +
                                            "FROM REPORTS " +
                                            "WHERE STORE_ID = ? AND ST_DATE >= ? AND END_DATE <= ?");
            ps.setInt(1, storeId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            rs = ps.executeQuery();
            resultsTable = new JTable(ViewUtils.buildResultsTableModel(rs));
            ps.close();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Failed obtain wage reports data\n " +
                                                                         "please check store id, start and end dates");
        }
        return resultsTable;
    }

    public JTable viewOrdersReport(int storeId, Date startDate, Date endDate){
        PreparedStatement ps;
        ResultSet rs;
        JTable resultsTable = null;
        try{
            ps = con.prepareStatement("SELECT REPORT_ID AS \"REPORT ID\", " +
                                            "STORE_ID AS \"STORE ID\", "+
                                            "ST_DATE AS \"START DATE\", " +
                                            "END_DATE AS \"END DATE\", " +
                                            "TOTAL_ORDERS AS \"TOTAL ORDERS\" " +
                                            "FROM REPORTS " +
                                            "WHERE STORE_ID = ? AND ST_DATE >= ? AND END_DATE <= ?");
            ps.setInt(1, storeId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            rs = ps.executeQuery();
            resultsTable = new JTable(ViewUtils.buildResultsTableModel(rs));
            ps.close();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Failed obtain order reports data\n" +
                                                                         "please check store id, start and end dates");
        }
        return resultsTable;
    }

    public JTable viewSalesReport(int storeId, Date startDate, Date endDate) {
        PreparedStatement ps;
        ResultSet rs;
        JTable resultsTable = null;
        try {
            ps = con.prepareStatement("SELECT REPORT_ID AS \"REPORT ID\", " +
                                            "STORE_ID AS \"STORE ID\", " +
                                            "ST_DATE AS \"START DATE\", " +
                                            "END_DATE AS \"END DATE\", " +
                                            "TOTAL_SALES AS \"TOTAL SALES\" " +
                                            "FROM REPORTS " +
                                            "WHERE STORE_ID = ? AND ST_DATE >= ? AND END_DATE <= ?");
            ps.setInt(1, storeId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            rs = ps.executeQuery();
            resultsTable = new JTable(ViewUtils.buildResultsTableModel(rs));
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed obtain sale reports data\n" +
                                                                         "please check store id, start and end dates");
        }
        return resultsTable;
    }
}
