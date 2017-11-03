package SQL;

import java.sql.*;

/**
 * Connects to an Oracle database
 */

public class Database {

    private Connection conn;
    public Statement statement;

    public Database() {
        buildConnection();
    }

    private void buildConnection(){
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "stub", "stub");
            statement = conn.createStatement();
            System.out.println("Connection works!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection fail");
        }
    }
}
