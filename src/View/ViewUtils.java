package View;

import oracle.sql.TIMESTAMP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Static methods class to eliminate repeated methods/code throughout View classes
 * If used in more than one view, create a method and place in here to be reused
 */

public final class ViewUtils {

    /**
     * Given a set of results from executing a PreparedStatement, create a table model that can be used in a JTable
     */

    public static DefaultTableModel buildResultsTableModel(ResultSet results) throws SQLException {
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
                Object temp = results.getObject(columnIndex);
                Object cl = temp.getClass();
                if (temp.getClass() == TIMESTAMP.class) {
                    vector.add(results.getTimestamp(columnIndex));
                } else {
                    vector.add(temp);
                }
            }
            data.add(vector);
        }

        results.close();
        return new DefaultTableModel(data, columnNames);
    }

    /**
     * Iterate through a group of buttons and return the text of the selected button
     */

    public static String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public static int getSequenceNumber(Connection con) throws SQLException {
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


}
