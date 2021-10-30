package itransitioncourse.game;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MoveHash {

    public byte[] getSecureKey(int numBytes) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public byte[] getHMAC(String compMove, byte[] secureKey) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String HMAC_ALGO = "HmacSHA256";
        Mac hmac = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(secureKey, HMAC_ALGO);
        hmac.init(keySpec);
        byte[] hmacMove = hmac.doFinal(compMove.getBytes("utf-8"));
        return hmacMove;
    }
}