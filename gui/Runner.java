package gui;

import datenmodell.PasswordHash;
import db.DBConnect;
import db.DBPrueferTask;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Timer;

/**
 * @author mwaldau
 */
public class Runner {

    public static Properties config;

    public static void main(String[] args) {
        config = readConfigFile();
        try {
            DBConnect.verbindungAufbauen(config.getProperty("url") + config.getProperty("db"), config);
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
            System.exit(0);
        }
        System.out.println(PasswordHash.createHash("P@ssw0rd"));
        new LoginFrame();
        if (DBConnect.verbindungSteht()) {
            Timer timer = new Timer(true);
            timer.schedule(new DBPrueferTask(), 5000, 5000);
        }
    }

    private static Properties readConfigFile() {
        Properties config = new Properties();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("info.config"))) {
            config.load(bis);
            bis.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return config;

    }

}
