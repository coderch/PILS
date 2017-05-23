package gui;

import db.DBConnect;
import db.DBPrueferTask;

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
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
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
            e.printStackTrace();
        }
        return config;

    }

}
