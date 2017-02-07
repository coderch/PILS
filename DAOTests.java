import datenmodell.Nutzer;
import db.DBConnect;
import db.NutzerDAO;
import db.VorhabenDAO;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by rrose on 07.02.2017.
 */
public class DAOTests {
    public static Properties config;

    public static void main(String[] args) {
        config = readConfigFile();
        try {
            DBConnect.verbindungAufbauen(config.getProperty("url") + config.getProperty("db"), config);
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
//        List<String> vorhabenListe = VorhabenDAO.holeVorhabenNamen();
//        for (String s : vorhabenListe) {
//            System.out.println(s);
//        }
//        Nutzer nutzer = new Nutzer(11111111, "H", "Rose", "Richard", "Soldat");
//        NutzerDAO.nutzerSpeichern(nutzer);
//        NutzerDAO.loginSpeichern(nutzer.getPersonalnummer(), "dummbeutel");
//
//        List<Nutzer> nutzerListe= NutzerDAO.nutzerHolen();
//        for (Nutzer n : nutzerListe){
//            System.out.println(n.toString());
//        }
//        NutzerDAO.loginLöschen(nutzer.getPersonalnummer());
//        NutzerDAO.loginLöschen(nutzer.getPersonalnummer());
//        NutzerDAO.nutzerLöschen(nutzer);
//        NutzerDAO.loginLöschen(nutzer.getPersonalnummer());
//
//        List<Integer> logins = NutzerDAO.holeLogins();
//        for(int i : logins){
//            System.out.println(i);
//        }
//        NutzerDAO.loginLöschen(11111112);
//        List<Integer> logneu = NutzerDAO.holeLogins();
//        for(int i : logins){
//            System.out.println(i);
//        }

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
        //...

    }


}
