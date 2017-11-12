package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLViewEmployees {

    private Connection con;

    public SQLViewEmployees() {
        con = DatabaseConnection.getConnection();
    }

    public ResultSet searchById(int id) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT e.employee_id, e.name, s.name, e.type "
                + "FROM EMPLOYEES e, STORES s "
                + "WHERE e.store_id = s.store_id AND e.employee_id = ? ");
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    // TODO: Add a separate query for Manager view once it's implemented in SQL scripts
}
