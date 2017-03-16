package db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by rrose on 01.12.2016.
 */
public class DBExport {

    public DBExport() {
        List<String> tableNames = getTableNames();
        for (String s : tableNames) {
            try (PreparedStatement pstm = DBConnect.preparedStatement("SELECT * FROM " + s);
                 ResultSet rs = pstm.executeQuery();
                 BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(s) + ".csv"))) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columncount = rsmd.getColumnCount();

                while (rs.next()) {
                    StringJoiner sj = new StringJoiner(",");
                    for (int i = 1; i <= columncount; i++) {
                        if (rs.getObject(i) == null) {
                            sj.add("");
                        } else {
                            sj.add(rs.getObject(i).toString());
                        }
                    }
                    bwr.write(sj.toString());
                    bwr.newLine();
                }
            } catch (SQLException e) {
                System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
            } catch (IOException e) {
                e.printStackTrace();
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