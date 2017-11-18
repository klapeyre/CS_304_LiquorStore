package SQL;

import java.sql.*;
import java.util.Vector;

public class SQLSearchStock {
    private static final String ALL_SUBTYPES = "All Types";
    private static final String ALL_STORES = "All Stores";
    private static final String ALL_REDS = "All Red Wines";
    private static final String ALL_WHITES = "All White Wines";
    private final Connection con;

    public SQLSearchStock() {
        con = DatabaseConnection.getConnection();
    }

    public ResultSet selectBySKU(int sku, boolean tax, String storeName) throws SQLException {
        PreparedStatement ps;
        if (tax) {
            ps = con.prepareStatement(
                    "SELECT i.sku, s.name as storeName, i.name, i.price + i.price * i.tax + i.deposit as price, i.description, si.stock_quantity "
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
                    "SELECT i.sku, s.name as storeName, i.name, i.price + i.price * i.tax + i.deposit as price, i.description, si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si "
                            + "WHERE i.sku = si.sku AND s.store_id = si.store_id AND UPPER(i.name) LIKE ? AND s.name LIKE ? ");
        } else {
            ps = con.prepareStatement(
                    "SELECT i.sku, s.name as storeName, i.name, i.price, i.description, si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si "
                            + "WHERE i.sku = si.sku AND s.store_id = si.store_id AND UPPER(i.name) LIKE ? AND s.name LIKE ? ");
        }

        ps.setString(1, "%" + name.toUpperCase() + "%");
        setStoreVariable(ps, storeName);
        return ps.executeQuery();
    }

    public ResultSet selectByBeer(String type, String storeName, boolean tax) throws SQLException{
        PreparedStatement ps;
        if (tax) {
            ps = con.prepareStatement(
                    "SELECT b.sku, s.name as storeName, b.company, i.name, b.type, " +
                            "ROUND(i.price + i.price * i.tax + i.deposit, 2) as price ,si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si, BEERS b "
                            + "WHERE b.sku = si.sku AND i.sku = b.sku AND s.store_id = si.store_id AND " +
                            "b.type LIKE ? AND s.name LIKE ? ");
        } else {
            ps = con.prepareStatement(
                    "SELECT b.sku, s.name as storeName, b.company, i.name, b.type, i.price, si.stock_quantity "
                            + "FROM ITEMS i, STORES s, STOREITEMS si, BEERS b "
                            + "WHERE b.sku = si.sku AND i.sku = b.sku AND s.store_id = si.store_id AND " +
                            "b.type LIKE ? AND s.name LIKE ? ");
        }

        setTypeVariable(ps, type);
        setStoreVariable(ps, storeName);
        return ps.executeQuery();
    }

    public ResultSet selectByWine(String type, String storeName, boolean tax) throws SQLException {
        PreparedStatement ps;
        if (type.equals(ALL_REDS) || type.equals(ALL_WHITES)) {
            if (tax) {
                ps = con.prepareStatement(
                        "SELECT w.sku, s.name as storeName, w.company, i.name, w.type, w.subtype, " +
                                "ROUND(i.price + i.price * i.tax + i.deposit, 2) as price, si.stock_quantity "
                                + "FROM ITEMS i, STORES s, STOREITEMS si, WINES w "
                                + "WHERE w.sku = si.sku AND i.sku = w.sku AND s.store_id = si.store_id AND " +
                                "w.type LIKE ? AND s.name LIKE ? ");
            } else {
                ps = con.prepareStatement(
                        "SELECT w.sku, s.name as storeName, w.company, i.name, w.type, w.subtype, i.price, si.stock_quantity "
                                + "FROM ITEMS i, STORES s, STOREITEMS si, WINES w "
                                + "WHERE w.sku = si.sku AND i.sku = w.sku AND s.store_id = si.store_id AND " +
                                "w.type LIKE ? AND s.name LIKE ? ");
            }
        } else {
            if (tax) {
                ps = con.prepareStatement(
                        "SELECT w.sku, s.name as storeName, w.company, i.name, w.type, w.subtype, " +
                                "ROUND(i.price + i.price * i.tax + i.deposit, 2) as price, si.stock_quantity "
                                + "FROM ITEMS i, STORES s, STOREITEMS si, WINES w "
                                + "WHERE w.sku = si.sku AND i.sku = w.sku AND s.store_id = si.store_id AND " +
                                "w.subtype LIKE ? AND s.name LIKE ? ");
            } else {
                ps = con.prepareStatement(
                        "SELECT w.sku, s.name as storeName, w.company, i.name, w.type, w.subtype, i.price, si.stock_quantity "
                                + "FROM ITEMS i, STORES s, STOREITEMS si, WINES w "
                                + "WHERE w.sku = si.sku AND i.sku = w.sku AND s.store_id = si.store_id AND " +
                                "w.subtype LIKE ? AND s.name LIKE ? ");
            }
        }

        setTypeVariable(ps, type);
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

    public Vector<String> getTypeNames(String type) throws SQLException {
        PreparedStatement ps;
        Vector<String> subTypeNames = new Vector<>();
        subTypeNames.add(ALL_SUBTYPES);

        if (type.equals("Beers")) {
            ps = con.prepareStatement("SELECT DISTINCT type " + "FROM " + type);
        } else {
            subTypeNames.add(ALL_REDS);
            subTypeNames.add(ALL_WHITES);
            ps = con.prepareStatement("SELECT DISTINCT subtype " + "FROM " + type);
        }


        ResultSet results = ps.executeQuery();
        while (results.next()) {
            subTypeNames.add(results.getString(1));
        }

        return subTypeNames;
    }

    public void setTypeVariable(PreparedStatement ps, String type) throws SQLException {
        if (type.equals(ALL_SUBTYPES)) {
            ps.setString(1, "%");
        } else if (type.equals(ALL_REDS)) {
            ps.setString(1, "Red");
        } else if (type.equals(ALL_WHITES)) {
            ps.setString(1, "White");
        } else {
            ps.setString(1,type);
        }
    }

    public void setStoreVariable(PreparedStatement ps, String storeName) throws SQLException {
        if (storeName.equals(ALL_STORES)) {
            ps.setString(2,  "%");
        } else {
            ps.setString(2, storeName);
        }
    }
}
