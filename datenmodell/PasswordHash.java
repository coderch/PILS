package datenmodell;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * Dient der Erzeugung von Passwort-Hashes
 *
 * @author rrose
 */
public class PasswordHash {
    /**
     * Verhindert die Instanzierung dieser Klasse.
     */
    private PasswordHash() {
    }

    /**
     * Erzeugt mit Hilfe des SHA-256 Algorithmuses einen Hashwert des übergebenen Passwortes.
     *
     * @param kennwort
     * @return SHA-256 Hashwert für den übergebenen Passwort-String
     * @throws NoSuchAlgorithmException Wird geworfen wenn der aufgerufene Algorithmus nicht verfügbar ist.
     */
    public static String createHash(String kennwort) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(kennwort.getBytes());
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: Algorithmus nicht vorhanden", JOptionPane.ERROR_MESSAGE);
        }
        return sb.toString();
    }
}