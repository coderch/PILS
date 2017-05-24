package gui;

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
 * @author rrose
 */
public class RCHRunner {

    public static Properties config;

    public static void main(String[] args) {
        config = readConfigFile();
        try {
            DBConnect.verbindungAufbauen("jdbc:postgresql://localhost/db_pils", config);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        new LoginFrame();
//        new Frameholder("Zugführer");
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
            ;
        }
        return config;

    }

}
