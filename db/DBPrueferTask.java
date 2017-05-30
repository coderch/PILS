package db;

import javax.swing.*;
import java.util.TimerTask;

/**
 * Timertask, welcher die Verbindung zur Datenbank prüft und bei Unterbrechung der Verbindung das Programm beendet.
 *
 * @author rrose
 */
public class DBPrueferTask extends TimerTask {
    /**
     * Die run()-Methode greift auf die statische Methode DBConnect.verbindungSteht() zu und überprüft die Datenbankverbindung ob diese noch valide ist.
     * Im Falle eines Verbindungsabbruches beendet dieser Task das Programm.
     */
    public void run() {
        if (!DBConnect.verbindungSteht()) {
            JOptionPane.showMessageDialog(null, "Verbindung zu Datenbank unterbrochen. Bitte starten Sie das Programm erneut", "FEHLER: Datenbank Verbindung", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
