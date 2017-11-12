package SQL;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class SQLMakeSale {
    private final Connection con;

    public SQLMakeSale() { con = DatabaseConnection.getConnection();}

    public boolean itemExists(Integer sku){
        return true; //Todo
    }


}
