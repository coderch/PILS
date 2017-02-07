package db;

import java.sql.*;
import java.util.Properties;


/**
 * Created by ajanzen on 16.12.2016.
 */
public class DBConnect {
    private static Connection connection;

    public static void verbindungAufbauen(String url, Properties info) throws SQLException {
        connection = DriverManager.getConnection(url, info);
        if (!verbindungSteht()) {
            System.exit(-1);
        }
    }

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

    public static void schliessen() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            connection.close();
        }
    }

    public static PreparedStatement preparedStatement(String sql) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement(sql);
        return pstm;
    }
    public static CallableStatement callableStatement(String sql) throws SQLException {
        CallableStatement cstm = connection.prepareCall(sql);
        return cstm;
    }
}
