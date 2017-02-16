package datenmodell;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * Dient der Erzeugung von Passwort-Hashes
 * @author rrose
 */
public class PasswordHash {
    /**
     * Verhindert die Instanzierung dieser Klasse.
     */
    private PasswordHash(){}

    /**
     * Erzeugt mit Hilfe des SHA-256 Algorithmuses einen Hashwert des übergebenen Passwortes.
     * @param kennwort
     * @return SHA-256 Hashwert für den übergebenen Passwort-String
     * @throws NoSuchAlgorithmException Wird geworfen wenn der aufgerufene Algorithmus nicht verfügbar ist.
     */
   public static String createHash(String kennwort) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(kennwort.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
