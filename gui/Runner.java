package gui;

import datenmodell.Nutzer;
import db.DBConnect;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mwaldau on 26.01.2017.
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

        new Frameholder("Zugführer");


    }

    private static Properties readConfigFile() {
        Properties config = new Properties();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("info.config"))) {
            config.load(bis);
            bis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;

    }

}
