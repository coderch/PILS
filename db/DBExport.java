package db;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rrose on 01.12.2016.
 */
public class DBExport {

    public DBExport() {
        List<String> tableNames = getTableNames();
        for (String s : tableNames) {
            System.out.println(s);
            try (PreparedStatement pstm = DBConnect.preparedStatement("SELECT * FROM " + s);
                 ResultSet rs = pstm.executeQuery()) {
                while (rs.next()){
                    System.out.println("Hier kommen nun eigentlich Daten");

                }
            } catch (SQLException e) {
                System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
            }
        }
    }

    public List<String> getTableNames() {
        List<String> tableNames = new LinkedList<>();
        DatabaseMetaData dbmd = DBConnect.getMetaData();
        String[] types = {"TABLE"};
        try {
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            while ((rs.next())) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return tableNames;
    }
}
