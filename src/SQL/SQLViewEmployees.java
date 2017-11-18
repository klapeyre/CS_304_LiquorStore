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

    public ResultSet searchById(int id, boolean manager) throws SQLException{
        PreparedStatement ps;
        if (manager) {
            ps = con.prepareStatement("SELECT e.employee_id, e.name, s.name AS store_name, e.type, e.salary "
                    + "FROM EMPLOYEES e, STORES s "
                    + "WHERE e.store_id = s.store_id AND e.employee_id = ? ");
        }

        else {
            ps = con.prepareStatement(" SELECT * FROM clerk_view WHERE employee_id = ?");
        }

        ps.setInt(1, id);
        return ps.executeQuery();
    }
}
