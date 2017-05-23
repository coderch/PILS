package db;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Die Klasse DBExport dient dazu, für jegliche Tabellen in der vorliegenden Datenbank einen dump zu erzeugen, welche als zusätzliches Back Up dienen.
 *
 * @author rrose
 */
public class DBExport {

    public DBExport() {
        List<String> tableNames = getTableNames();
        for (String s : tableNames) {
            try (PreparedStatement pstm = DBConnect.preparedStatement("SELECT * FROM " + s);
                 ResultSet rs = pstm.executeQuery();
                 BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(s) + ".sql"))) {
                ResultSetMetaData rsmd = rs.getMetaData();
                String command = "INSERT INTO " + s + " ";
                StringJoiner sjo = new StringJoiner(",", "(", ")");
                int columncount = rsmd.getColumnCount();
                for (int i = 1; i <= columncount; i++) {
                    sjo.add(rsmd.getColumnName(i));
                }
                command += sjo.toString() + " VALUES ";

                while (rs.next()) {
                    StringJoiner sj = new StringJoiner(",", "(", ")");
                    for (int i = 1; i <= columncount; i++) {
                        if (rs.getObject(i) == null) {
                            sj.add("''");
                        } else if (rsmd.getColumnTypeName(i) == "int2" || rsmd.getColumnTypeName(i) == "int4" || rsmd.getColumnTypeName(i) == "int8" || rsmd.getColumnTypeName(i) == "bool"
                                || rsmd.getColumnTypeName(i) == "float4" || rsmd.getColumnTypeName(i) == "float8" || rsmd.getColumnTypeName(i) == "numeric") {
                            sj.add(rs.getObject(i).toString());
                        } else {
                            sj.add("'" + rs.getObject(i).toString() + "'");
                        }
                    }
                    String zeile = command + sj.toString() + ";";
                    bwr.write(zeile);
                    bwr.newLine();
                }
            } catch (SQLException e) {
                DBConnect.SQLFehlermeldung(e);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Diese private Methode wird lediglich von der Klasse DBExport genutzt. Beim erstellen einer Instanz dieser Klasse wird diese Methode aufgerufen,
     * um eine Liste mit allen Namen der in der Datenbank vorhandenen Tabellen zu erhalten.
     *
     * @return Liste mit allen in der Datenbank vorliegenden Tabellennamen
     */
    private List<String> getTableNames() {
        List<String> tableNames = new LinkedList<>();
        DatabaseMetaData dbmd = DBConnect.getMetaData();
        String[] types = {"TABLE"};
        try {
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            while ((rs.next())) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
        }
        return tableNames;
    }
}