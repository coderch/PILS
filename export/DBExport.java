package export;

import db.DBConnect;

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
 * Die Klasse DBExport dient dazu, für jegliche Tabellen in der vorliegenden Datenbank einen Dump bzw. ein INSERT INTO-Skript zu erzeugen, welches als zusätzliches Backup dienen soll.
 *
 * @author rrose
 */
public class DBExport {
    /**
     * Dieser Konstruktor erstellt ein neues DBExport-Objekt und erstellt gleichzeitig die dump-Dateien mit Hilfe der Metadaten der Datenbank.
     */
    public DBExport() {
        List<String> tableNames = getTableNames(); // Alle Tabellen aus der Datenbank holen. Siehe getTableNames().
        for (String s : tableNames) {
            try (PreparedStatement pstm = DBConnect.preparedStatement("SELECT * FROM " + s);
                 ResultSet rs = pstm.executeQuery(); // ResultSet für jede Tabelle mit allen Inhalten.
                 BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(s) + ".sql"))) { // Erstellen der Datei für diese Tabelle.
                ResultSetMetaData rsmd = rs.getMetaData();  // Metadadaten des ResultSets holen, um später die Anzahl und die Datentypen der Spalten zu erkennen.
                String command = "INSERT INTO " + s + " ";
                StringJoiner sjo = new StringJoiner(",", "(", ")");
                int columncount = rsmd.getColumnCount();
                for (int i = 1; i <= columncount; i++) {
                    sjo.add(rsmd.getColumnName(i)); // Für jede Spalte den Namen in das SQL-Statement einfügen.
                }
                command += sjo.toString() + " VALUES ";

                while (rs.next()) { // Für jeden Eintrag im ResultSet werden nun die Values dem SQL-Statement hinzugefügt.
                    StringJoiner sj = new StringJoiner(",", "(", ")");
                    for (int i = 1; i <= columncount; i++) {
                        if (rs.getObject(i) == null) {
                            sj.add("''");
                        } else if (rsmd.getColumnTypeName(i).equals("int2") || rsmd.getColumnTypeName(i).equals("int4") || rsmd.getColumnTypeName(i).equals("int8") || rsmd.getColumnTypeName(i).equals("bool")
                                || rsmd.getColumnTypeName(i).equals("float4") || rsmd.getColumnTypeName(i).equals("float8") || rsmd.getColumnTypeName(i).equals("numeric")) {
                            sj.add(rs.getObject(i).toString());
                        } else {
                            sj.add("'" + rs.getObject(i).toString() + "'");
                        }
                    }
                    String zeile = command + sj.toString() + ";"; // Zusammenführung der einzelnen Teilbefehle.
                    bwr.write(zeile); // Schreiben eines Befehls.
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
            ResultSet rs = dbmd.getTables(null, null, "%", types);  // Filtern, dass nur die Name der Datentabellen dieser Datenbank hinzugefügt werden.
            while ((rs.next())) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
        }
        return tableNames;
    }
}

/*
Diese Klasse wurde geschrieben bevor das Thema Threads im Unterricht behandelt wurde. Man kann diesen Vorgang recht simpel in ein Runnable verwandeln.
Wir haben uns dennoch dafür entschieden, diese Lösung so zu belassen.
 */