package SQL;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class SQLSearchStock {
    Connection con;

    public SQLSearchStock() {
        con = DatabaseConnection.getConnection();
    }

    public ResultSet selectBySKU(int sku) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT i.sku, i.name, i.price, i.description " + "FROM ITEMS i " + "WHERE i.sku = ?");
        ps.setInt(1, sku);
        return ps.executeQuery();
    }

    public ResultSet selectByName(String name) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT i.sku, i.name, i.price, i.description " + "FROM ITEMS i " + "WHERE i.name LIKE ?");
        ps.setString(1, "%" + name + "%");
        return ps.executeQuery();
    }

    public DefaultTableModel buildResultsTableModel(ResultSet results) throws SQLException {
        ResultSetMetaData metaData = results.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (results.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(results.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
