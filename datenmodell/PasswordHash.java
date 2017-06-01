package datenmodell;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * Dient der Erzeugung von Passwort-Hashes mit Hilfe des in Java implementierten SHA-256 Algorithmuses.
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
     * @param passwort Das Passwort für welches der SHA-Hash erzeugt werden soll.
     * @return Gibt einen String zurück mit dem SHA-256 Hashwert für das übergebene Passwort (64 Symbole von 0-f).
     */
    public static String createHash(String passwort) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256"); // Festlegen des Hash-Algorithmuses.
            byte[] hashes = mDigest.digest(passwort.getBytes()); // Zerlegen des Strings in ein Byte-Array und Berechnung der Hashes der einzelnen Bytes.
            for (byte hash : hashes) {
                sb.append(String.format("%02x",hash)); //Zusammenführung der errechneten Hashwerte zu einem zusammenhängenden String. Alternative Möglichkeit (Integer.toString((hash & 0xff) + 0x100, 16).substring(1)).
            }
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: " + e.getCause(), JOptionPane.ERROR_MESSAGE);
        }
        return sb.toString();
    }
}