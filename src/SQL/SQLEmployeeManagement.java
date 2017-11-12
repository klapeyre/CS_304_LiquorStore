package SQL;

import java.sql.*;

public class SQLEmployeeManagement {
    private final Connection con;

    public SQLEmployeeManagement() {
        con = Database.getConnection();
    }

    public int insertNewEmployee(String name, String username, char[] password, Double salary, int storeID, String type) throws SQLException {
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("INSERT INTO employees VALUES (seq_id.nextval,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, String.valueOf(password));
            if (salary == null){
                ps.setNull(4, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(4, salary);
            }
            ps.setInt(5, storeID);
            ps.setString(6, type);

            ps.executeUpdate();
            con.commit();
            ps.close();

        } catch (SQLException e) {
            try
            {
                // undo the insert
                con.rollback();
            } catch (SQLException e2) {
                System.out.println("Message: " + e2.getMessage());
                System.exit(-1);
            }
            throw e;
        }

        return getSequenceNumber();
    }

    private int getSequenceNumber() throws SQLException {
        Statement stmt;
        ResultSet rs;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT seq_id.currval FROM DUAL");
            rs.next();
            int id = rs.getInt("CURRVAL");
            stmt.close();
            return id;
        } catch (SQLException e){
            System.out.println("Could not get sequence number. Message: " + e.getMessage());
            throw e;
        }
    }

    public void removeEmployee(int employeeID){
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("DELETE FROM employees WHERE employee_id = ?");
            ps.setInt(1, employeeID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                //TODO throw error
                System.out.print("DOES NOT EXIST");
            }
            con.commit();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            try
            {
                con.rollback();
            } catch (SQLException e2)
            {
                System.out.println("Message: " + e2.getMessage());
                System.exit(-1);
            }
        }

    }


    public void changeSalary(int employeeID, double newSalary){
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("UPDATE employees SET salary = ? WHERE employee_id = ?");
            ps.setDouble(1, newSalary);
            ps.setInt(2, employeeID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                //TODO throw error
                System.out.print("DOES NOT EXIST");
            }
            con.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
