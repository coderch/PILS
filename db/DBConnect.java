package db;

import javax.swing.*;
import java.sql.*;
import java.util.Properties;


/**
 * Klasse zur Herstellung und Verwaltung der Datenbankverbindung.
 *
 * @author ajanzen
 * @see Connection
 */
public class DBConnect {
    /**
     * Attribut in dem das Verbindungsobjekt gesichert wird.
     */
    private static Connection connection;

    /**
     * Methode für den Verbindungsaufbau.
     *
     * @param url  Die URL, der Port und die IP-Adresse des PostgreSQL-Servers
     * @param info Login und Kennwort für die Anmeldung am PostgreSQL-Server
     * @throws SQLException Fehler in der Verbindung.
     */
    public static void verbindungAufbauen(String url, Properties info) throws SQLException {
        connection = DriverManager.getConnection(url, info);
        if (!verbindungSteht()) {
            System.exit(-1);
        }
    }

    /**
     * Methode um zu prüfen, ob ein gültige Verbindung besteht.
     *
     * @return Gibt einen Boolean zurück. Wenn die Datenbankverbindung aufgebaut ist - true, andernfalls - false.
     */
    public static boolean verbindungSteht() {
        boolean steht = false;
        if (connection != null) {
            try {
                if (connection.isValid(0)) steht = true;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: " + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
            }
        }
        return steht;
    }

    /**
     * Methode zum Schließen der Datenbankverbindung.
     *
     * @throws SQLException Diese Exception wird geworfen wenn der Zugriff auf die Datenbankverbindung fehlschlägt.
     */
    public static void schliessen() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            connection.close();
        }
    }

    /**
     * Methode zu Übergabe eines SQL-Statements an die Connection.
     *
     * @param sql SQL- Statment
     * @return Gibt ein PreparedStatement zurück.
     * @throws SQLException Fehler in der Verbindung
     * @see PreparedStatement
     */
    public static PreparedStatement preparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    /**
     * Methode zu Übergabe eines SQL-Statements an die Connection.
     *
     * @param sql SQL- Statment
     * @return Gibt ein CallableStatement zurück.
     * @throws SQLException Fehler in der Verbindung
     * @see CallableStatement
     */
    public static CallableStatement callableStatement(String sql) throws SQLException {
        return connection.prepareCall(sql);
    }

    /**
     * Methode zur Abfrage von MetaDaten der Datenbank.
     *
     * @return Die Metadaten der angefragten Datenbank.
     */
    public static DatabaseMetaData getMetaData() {
        DatabaseMetaData dbmd = null;
        try {
            dbmd = connection.getMetaData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: " + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
        }
        return dbmd;
    }

    /**
     * Erstellt ein JOptionPane mit einer Fehlermeldung.
     *
     * @param e Die geworfene Exception.
     */
    public static void SQLFehlermeldung(SQLException e) {
        JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: " + e.getSQLState(), JOptionPane.ERROR_MESSAGE);
    }
}
