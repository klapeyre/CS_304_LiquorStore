package SQL;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class SQLSearchStock {
    private static final String ALL_SUBTYPES = "All Types";
    private static final String ALL_STORES = "All Stores";
    private final Connection con;

    public SQLSearchStock() {
        con = DatabaseConnection.getConnection();
    }

    public ResultSet selectBySKU(int sku, boolean tax, String storeName) throws SQLException {
        PreparedStatement ps;
        if (tax) {
            ps = con.prepareStatement(
                    "SELECT i.sku, s.name as storeName, i.name, i.price + i.tax as price, i.description, si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si "
                            + "WHERE i.sku = si.sku AND s.store_id = si.store_id AND i.sku = ? AND s.name LIKE ? ");
        } else {
            ps = con.prepareStatement(
                    "SELECT i.sku, s.name as storeName, i.name, i.price, i.description, si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si "
                            + "WHERE i.sku = si.sku AND s.store_id = si.store_id AND i.sku = ? AND s.name LIKE ? ");
        }
        ps.setInt(1, sku);
        setStoreVariable(ps, storeName);
        return ps.executeQuery();
    }

    public ResultSet selectByName(String name, boolean tax, String storeName) throws SQLException {
        PreparedStatement ps;
        if (tax) {
            ps = con.prepareStatement(
                    "SELECT i.sku, s.name as storeName, i.name, i.price + i.tax as price, i.description, si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si "
                            + "WHERE i.sku = si.sku AND s.store_id = si.store_id AND i.name LIKE ? AND s.name LIKE ? ");
        } else {
            ps = con.prepareStatement(
                    "SELECT i.sku, s.name as storeName, i.name, i.price, i.description, si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si "
                            + "WHERE i.sku = si.sku AND s.store_id = si.store_id AND i.name LIKE ? AND s.name LIKE ? ");
        }

        ps.setString(1, "%" + name + "%");
        setStoreVariable(ps, storeName);
        return ps.executeQuery();
    }

    public ResultSet selectBySubType(String subType, String type, String storeName) throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                    "SELECT t.sku, t.company, i.name, t.type, s.name as storeName, si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si, " + type + " t "
                            + "WHERE t.sku = si.sku AND i.sku = t.sku AND s.store_id = si.store_id AND " +
                            "t.type LIKE ? AND s.name LIKE ? ");
        setSubTypeVariable(ps, subType);
        setStoreVariable(ps, storeName);
        return ps.executeQuery();
    }

    public Vector<String> getStoreNames() throws SQLException {
        Vector<String> storeNames = new Vector<String>();
        storeNames.add(ALL_STORES);
        PreparedStatement ps = con.prepareStatement("SELECT s.name " + "FROM STORES s ");

        ResultSet results = ps.executeQuery();
        while (results.next()) {
            storeNames.add(results.getString(1));
        }

        return storeNames;
    }

    public Vector<String> getSubTypeNames(String type) throws SQLException {
        Vector<String> subTypeNames = new Vector<>();
        subTypeNames.add(ALL_SUBTYPES);
        PreparedStatement ps = con.prepareStatement("SELECT DISTINCT type " + "FROM " + type);

        ResultSet results = ps.executeQuery();
        while (results.next()) {
            subTypeNames.add(results.getString(1));
        }

        return subTypeNames;
    }

    public void setSubTypeVariable(PreparedStatement ps, String subType) throws SQLException {
        if (subType.equals(ALL_SUBTYPES)) {
            ps.setString(1, "%");
        } else {
            ps.setString(1, subType);
        }
    }

    public void setStoreVariable(PreparedStatement ps, String storeName) throws SQLException {
        if (storeName.equals(ALL_STORES)) {
            ps.setString(2,  "%");
        } else {
            ps.setString(2, storeName);
        }
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
