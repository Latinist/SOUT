package artintech;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Анатолий on 19.01.2015.
 */
public class Digester {
    public Digester(){}
    public static String getDigest(String password) throws NoSuchAlgorithmException {
        if (password == null) password = "";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
