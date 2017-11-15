package SQL;

import java.sql.*;

/**
 * Connects to an Oracle database
 */

public class DatabaseConnection {

    private static Connection conn;
    private static Statement statement;

    public static void establishConnection(String username, String password){
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1522:ug", username, password);
            statement = conn.createStatement();
            System.out.println("Connection works!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection fail");
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}