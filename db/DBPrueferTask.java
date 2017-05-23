package db;

import javax.swing.*;
import java.util.TimerTask;

/**
 * @author rrose
 */
public class DBPrueferTask extends TimerTask {
    @Override
    public void run() {
        if (!DBConnect.verbindungSteht()) {
            JOptionPane.showMessageDialog(null, "Verbindung zu Datenbank unterbrochen. Bitte starten Sie das Programm erneut", "FEHLER: Datenbank Verbindung", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
