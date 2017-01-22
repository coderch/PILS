package datenmodell;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * Created by rrose on 21.01.2017.
 */
public class PasswordHash {

    private PasswordHash(){}

   public static String createHash(String password) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
