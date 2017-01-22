import datenmodell.Nutzer;
import datenmodell.PasswordHash;
import gui.Frameholder;

import java.security.NoSuchAlgorithmException;

/**
 * Created by rrose on 21.11.2016.
 */
public class Runner {
    public static void main(String[] args) {
        Nutzer nutzer = new Nutzer(11191306, "password", "Rose", "Richard", "OFR", "Zugführer");

        System.out.println(nutzer.getKennwort());
        try {
            System.out.println(PasswordHash.createHash("Password"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
