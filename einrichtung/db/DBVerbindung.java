package einrichtung.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by rrose on 21.11.2016.
 */
public class DBVerbindung {

    public static Connection CON = null;

    private DBVerbindung() {
    }

    public static void verbinden() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("properties.prop"));
            CON = DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void schliessen() {
        try {
            if (CON != null) {
                CON.close();
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

    public static void tabellenAnlegen() {

    }

}
