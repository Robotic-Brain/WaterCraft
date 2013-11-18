package dgrxf.watercraft.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {
    
    public static String generateHash(String toHash) {
        BigInteger i = null;
        try {
            String returnHash = "";
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] data = toHash.getBytes();
            m.update(data, 0, data.length);
            i = new BigInteger(1, m.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return String.format("%1$032X", i);
    }
}
