//import datenmodell.Nutzer;
//import datenmodell.PasswordHash;
//import datenmodell.Vorhaben;
//import db.DBConnect;
//import db.NutzerDAO;
//import db.VorhabenDAO;
//
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Properties;
//
///**
// * Created by rrose on 21.11.2016.
// */
//public class Runner {
//    public static Properties config;
//
//    public static void main(String[] args) {
//        config = readConfigFile();
//        Nutzer nutzer = new Nutzer(131313131, "UmP","password", "Rose", "Richard", "OFR", "Zugführer");
//
//
//        System.out.println(nutzer.getKennwort());
//        try {
//            System.out.println(PasswordHash.createHash("Password"));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        try {
//            DBConnect.verbindungAufbauen(config.getProperty("url") + config.getProperty("db"), config);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        List<Nutzer> nutzers = NutzerDAO.nutzerHolen();
//        List<String> vorhabenNamen = VorhabenDAO.holeVorhabenNamen();
//        for (String s : vorhabenNamen) {
//            System.out.println(s.toString());
//        }
//        for (Nutzer n : nutzers) {
//            System.out.println(n.toString());
//        }
//        Vorhaben vorhaben = new Vorhaben("Test", "Tut das gut", LocalDate.now(), LocalDate.now());
//        ///VorhabenDAO.vorhabenSpeichern(vorhaben, eingeteilteSoldaten);
//
//        NutzerDAO.loginSpeichern(nutzer);
//        NutzerDAO.nutzerLöschen(nutzer);
//        NutzerDAO.loginLöschen(nutzer.getPersonalnummer());
//    }
//
//
//    private static Properties readConfigFile() {
//        Properties config = new Properties();
//
//        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("info.config"))) {
//            config.load(bis);
//            bis.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return config;
//
//    }
//}
