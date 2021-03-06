import db.DBConnect;
import db.DBPrueferTask;
import gui.LoginFrame;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Timer;

/**
 * Startklasse des Programms. Beinhaltet die main Methode in der zunächst die configfile eingelesen wird
 * um danach die Verbindung zur Datenbank aufzubauen. Mittels eines Timers wird im laufenden Betrieb ständig die Verbindung zur Datenbank
 * geprüft.
 *
 * @author ajanzen, rrose, mwaldau
 */
class Runner {

    public static void main(String[] args) {
        Properties config = readConfigFile();
        try {
            DBConnect.verbindungAufbauen(config.getProperty("url") + config.getProperty("db"), config);
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
            System.exit(0);
        }

        new LoginFrame();
        if (DBConnect.verbindungSteht()) {
            Timer timer = new Timer(true);
            timer.schedule(new DBPrueferTask(), 5000, 5000);
        }
    }

    /**
     * List die Konfigdatei ein
     *
     * @return config Properties Objekt
     */
    private static Properties readConfigFile() {
        Properties config = new Properties();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./info.config")))) {
            config.load(br);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return config;

    }

}
