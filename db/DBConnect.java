package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Created by ajanzen on 16.12.2016.
 */
public class DBConnect {
    private static Connection connection;

    public static boolean verbindungAufbauen(String url, Properties info) throws SQLException {
        connection = DriverManager.getConnection(url, info);
        if (verbindungSteht()) {

            return true;
        } else {
            System.exit(-1);
            return false;
        }
    }

    public static boolean verbindungSteht() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            return true;
        } else {
            return false;
        }
    }

    public static void schliessen() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            connection.close();
        }
    }

    public static PreparedStatement preparedStatement(String sql) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement(sql);
        return pstm;
    }
}
