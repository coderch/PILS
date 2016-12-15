package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by rrose on 21.11.2016.
 */
public class DBConnect {

    private static Connection CON;

    public DBConnect(String sql) {

    }

    public void verbindungAufbauen(String url, Properties properties) {

    }

    public void schliessen() {

    }

    public static Properties datenEinlesen(String dateipfad) {
        Properties properties = null;
        return properties;
    }

    public PreparedStatement preparedStatement(String sql) {
        PreparedStatement pstm = null;
        try {
            pstm = CON.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstm;
    }

    public Statement getStatement(String sql) {
        Statement st = null;
        try {
            st = CON.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return st;
    }


}
