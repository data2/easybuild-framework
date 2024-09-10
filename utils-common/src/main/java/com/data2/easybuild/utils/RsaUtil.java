package com.data2.easybuild.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class RsaUtil {
    /**
     * generate Rsa Pair
     *
     * @throws Exception
     */
    public static KeyPair generateRsaPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    private static String publicKeyToString(PublicKey publicKey) throws Exception {
        byte[] encoded = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }

    private static String privateKeyToString(PrivateKey privateKey) throws Exception {
        byte[] encoded = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }


    /**
     * generate Rsa Pair，turn to String
     * return list
     *
     * @throws Exception
     */
    public static List<String> generateRsaPairString() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        PublicKey publicKey = pair.getPublic();
        PrivateKey privateKey = pair.getPrivate();

        try {
            String publicKeyString = publicKeyToString(publicKey);
            String privateKeyString = privateKeyToString(privateKey);

            log.info("publicKey:{} ", publicKeyString);
            log.info("privateKey:{}", privateKeyString);

            ArrayList<String> result = new ArrayList<>();
            result.add(publicKeyString);
            result.add(privateKeyString);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static PublicKey stringToPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private static PrivateKey stringToPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static byte[] encryptInner(PublicKey publicKey, String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    private static String decryptInner(PrivateKey privateKey, byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes, "UTF-8");
    }

    /**
     * loading public key string and encrypt
     *
     * @param
     */
    public static String encryt(String text, String publicKeyStr) {
        try {
            PublicKey publicKey = stringToPublicKey(publicKeyStr);
            byte[] cipherText = encryptInner(publicKey, text);
            String cipherTextStr = Base64.getEncoder().encodeToString(cipherText);
            log.info("origin text: {} ", text);
            log.info("encry text: {}", cipherTextStr);
            return cipherTextStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * loading private key string and decrypt
     *
     * @param
     */
    public static String decrypt(String cipherText, String privateKeyStr) {
        try {
            PrivateKey privateKey = stringToPrivateKey(privateKeyStr);
            String decryptedText = decryptInner(privateKey, Base64.getDecoder().decode(cipherText));
            log.info("cipher text: {}", cipherText);
            log.info("decrypt text: {}", decryptedText);
            return decryptedText;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * demo for using.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        encryt("111", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu3nYjLKetKekMGLAxju8CJUOyp4G5MaKXM5tDdHXyzTchBF4/HDl3TAG1SyqD2Xwzy5YLfnMeR0wX3fLN/jIdr3OsDgSUyJuQ9XTjbNQHzFa4U6LRvdaq5Orr1r2wclw4dvtGmUHYnAKdvyOZbukKYRDizLGfvH4U6wmKY/yFVZupuiQYnwyxDBDZT4JK3PjzlNjV3WgcAuna2/V6YBHDFDsey79u4hORJKET7MEzhbdoatR5zF/QmGXoWibYv9TARgSxxWYwqa/Pq1ZacEJrUu3ls61EDy14dQvJDC03A9M8fW/VkHVjQu/SfK54/1P/YQzPrURmGVITwiwvA4p+QIDAQAB");
        decrypt("I1OBOrejMfRNMZOeNXt/r9kstQB2IHTz+zvp+UbOsgL5HlSUcbEeIbGRy+/vkQQtSk6gccBAr+tohZ9XfeKwgdrUqlZ9J9j6DQSrxZNkWbry+BVAY6b0PGQYZEJDOl1UUd6a7yYs95p73ilIzwXUo4cby5/cYDdw2pTzismWjyNEu3nI3lrxlwZWxL094gSVe89zsaCAkWU/rZxBR+5HApTK+w+DgruqHQaUvH7ZA2rEhLs/2aUxQ8ngIAcwhaeDPo4bVvxu4b/1g6WdJ1EJaGgDr8VabAwSYqYdzXrXn8kGuxjRgaxMP7qs2e9M356hWRmHDMCoIep+CDycgzxmlQ==",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC7ediMsp60p6QwYsDGO7wIlQ7Kngbkxopczm0N0dfLNNyEEXj8cOXdMAbVLKoPZfDPLlgt+cx5HTBfd8s3+Mh2vc6wOBJTIm5D1dONs1AfMVrhTotG91qrk6uvWvbByXDh2+0aZQdicAp2/I5lu6QphEOLMsZ+8fhTrCYpj/IVVm6m6JBifDLEMENlPgkrc+POU2NXdaBwC6drb9XpgEcMUOx7Lv27iE5EkoRPswTOFt2hq1HnMX9CYZehaJti/1MBGBLHFZjCpr8+rVlpwQmtS7eWzrUQPLXh1C8kMLTcD0zx9b9WQdWNC79J8rnj/U/9hDM+tRGYZUhPCLC8Din5AgMBAAECggEAYTlAPz8GpQNnV5xvAp7J1d2PTS6EvrHj4VtAHPGdKongbjEtgCUAYCPe90BxdQjVyGvtWPJ5wrmLc4cJgZ5oP4QRozuA7VImwtEqJAtA2txamAuGmSgAAojTeV4AJ+FSxB6sqIqKONeH5cr2AfY8nTc1XAPaAs1zjdjx9Y3r6noEKvNq1nb94YjUiRMAVr8M8vA6IDsFEaH+dTsf68A9Tvwd9c7B3ANfQNLoaygVTLjA6oThP885uY4GBCarSIUq4563rAsWAfvgR+tWM+wkkKJAAVDEaVBvQO97u9AVrlB1JKCI3zqXAGrdLu0cUO5V2EN3o7WaO02iLNumKadF3QKBgQDeM4Um5ErwraZVMUNUNOvpiR6h9P6E7r9kNEUKJg9kZVObNNUid/DuTFEBQLTRbyFUsFx0bSOjEGzPSmeN9ct9SiJ1MzWCrW5vgoTsEmNgXPtKa5hSKvWgp4mVr3bGS2xlFfVDWL7vgi+H8DYUboPhNyNYfygpylZcx1nFEjSgXwKBgQDX/iD0zbZMZVI6iTfdsSfj9bOPoWGjXh01KJZ5+KH+1iGEoWslRy62ML8zk8xubt/cFusYGlpRAALuc7JtV2K0h20VQKNHFmNqsdGneTp7XlFuUxM2arSyMqfYG3pAC9snRep2tFL4CXmsm3gQH5QmxOwY342k44TP1m1b/YH0pwKBgHUQ77Xt0Q28b0WzDqXuu9vuQhVR3kZrAqroXvdZ1gIu4D2FOEVQq77gtkzSqnEs+4KC6DLuzTe8D5emu6gvN2ZrCHx23lpaxTQ6r40wourCEiLDTRz9RSu/GN2j9A/E7PbU+sLgnrfQ/vm/tL5O+2xMgHK/i4l48McwwDrzvIZBAoGAcerJbPPWMwK5VMAsY9Ncq6FZ2+YMvgIUO4Y1nwzgO6yqLu6HuTiKzXJ2VXidOJ1nmgfIZIT5n4D3Ctagg4S0PWyzvqLBuut28b3pSn/otUpwflR4bJnoxZdr6NHNL1xRcKwyVfsTS1iMANpaf354P95ae1/kr9ODp657LSpOUT8CgYEA0GCcmzy3eN8lUUazyCAXoLKj1OsEkd6RGJro3CTHGL8hHUzVG5HQMR9GwweHXQde5nejqgVXJHmHcCuLFdY7nwiPbweNPwHvAeS643sskXZtelGRSrOumNSu+WaRjmFV0Cresqw1bUEyItIkFONYj46zE6w3HO2t4uAKD3yueZ8=");
    }
}