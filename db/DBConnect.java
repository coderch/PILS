package db;

import java.sql.*;
import java.util.Properties;


/**
 * Klasse zu herstellung der Datenbankverbindung
 * @author ajanzen
 * @see Connection
 */
public class DBConnect {
    private static Connection connection;

    /**
     * Methode für den Verbindungsaufbau
     * @param url die URL die den Datenbankteiber, den Port und die IP des PostgreSQL - Servers
     * @param info Login und Kennwort für die Anmeldung an PostgreSQL - Servers
     * @throws SQLException Fehler in der Verbindung
     */
    public static void verbindungAufbauen(String url, Properties info) throws SQLException {
        connection = DriverManager.getConnection(url, info);
        if (!verbindungSteht()) {
            System.exit(-1);
        }
    }

    /**
     * Methode um zu prüfen ob ein gültige Verbindung besteht
     * @return
     */
    public static boolean verbindungSteht() {
        boolean steht = false;
        if (connection != null) {
            try {
                if (connection.isValid(0)) steht = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return steht;
    }

    /**
     * Methode zum Schlissen der Datenbankverbindung
     * @throws SQLException
     */
    public static void schliessen() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            connection.close();
        }
    }

    /**
     * Methode zu Übergabe eines SQl - Statment an die Connection
     * @see PreparedStatement
     * @param sql SQL- Statment
     * @return Ausgeführter SQL - Statment
     * @throws SQLException Fehler in der Verbindung
     */
    public static PreparedStatement preparedStatement(String sql) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement(sql);
        return pstm;
    }

    /**
     * Methode zu Übergabe eines SQl - Statment an die Connection
     * @see CallableStatement
     * @param sql SQL- Statment
     * @return Ausgeführter SQL - Statment
     * @throws SQLException Fehler in der Verbindung
     */
    public static CallableStatement callableStatement(String sql) throws SQLException {
        CallableStatement cstm = connection.prepareCall(sql);
        return cstm;
    }
}
