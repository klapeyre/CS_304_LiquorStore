package SQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class SQLSearchStock {
    private final String ALL_STORES = "All Stores";
    private final String BEERS = "Beers";
    Connection con;

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

    public ResultSet selectBySubType(String subType, boolean beer, String storeName) throws SQLException{
        PreparedStatement ps;
        if (beer) {
            ps = con.prepareStatement(
                    "SELECT b.sku, s.name as storeName, b.company, si.stock_quantity "
                        + "FROM BEERS b, STORES s, STOREITEMS si "
                        + "WHERE b.sku = si.sku AND s.store_id = si.store_id AND b.type = ? AND s.name LIKE ? ");
        } else {
            ps = con.prepareStatement(
                    "SELECT w.sku, s.name as storeName, w.company, si.stock_quantity "
                            + "FROM WINES w, STORES s, STOREITEMS si "
                            + "WHERE w.sku = si.sku AND s.store_id = si.store_id AND w.type = ? AND s.name LIKE ? ");
        }
        ps.setString(1, subType);
        setStoreVariable(ps, storeName);
        return ps.executeQuery();
    }

    public Vector<String> selectByStore() throws SQLException {
        Vector<String> storeNames = new Vector<String>();
        storeNames.add(ALL_STORES);
        PreparedStatement ps = con.prepareStatement("SELECT s.name " + "FROM STORES s ");

        ResultSet results = ps.executeQuery();
        while (results.next()) {
            storeNames.add(results.getString(1));
        }

        return storeNames;
    }

    public Vector<String> selectByType(String type) throws SQLException {
        PreparedStatement ps;
        Vector<String> typeNames = new Vector<>();
        if (type.equals(BEERS)) {
            ps = con.prepareStatement("SELECT DISTINCT b.type " + "FROM BEERS b ");
        } else {
            ps = con.prepareStatement("SELECT DISTINCT w.type " + "FROM WINES w ");
        }
        ResultSet results = ps.executeQuery();
        while (results.next()) {
            typeNames.add(results.getString(1));
        }

        return typeNames;
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
