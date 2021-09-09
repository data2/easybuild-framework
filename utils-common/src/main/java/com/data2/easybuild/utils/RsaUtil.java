package com.data2.easybuild.utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {
    //取值 SHA256WithRSA  或者 SHA1WithRSA
    //RSA 对应 SHA1WithRSA
    //RSA2 对应 SHA256WithRSA  至少2048 位以上
    public static final String SHA_WITH_RSA_ALGORITHM = "SHA256WithRSA";

    public static final String MD5_WITH_RSA = "MD5WithRSA";

    public static final int KEY_SIZE_2048 = 2048;

    public static final int KEY_SIZE_1024 = 1024;

    public static final String ALGORITHM = "RSA";

    public static final String CHARSET = "UTF-8";

    /**
     * RSA公钥加密
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(CHARSET)));
        return outStr;
    }

    /**
     * RSA私钥解密
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = java.util.Base64.getDecoder().decode(str.getBytes(CHARSET));
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 随机生成密钥对
     * 也可以用阿里的生成工具
     */
    public static void genKeyPair(Integer keySize) throws NoSuchAlgorithmException {
        //推荐 2048位以上，保证安全性
        if (keySize == null) {
            keySize = KEY_SIZE_2048;
        }
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(keySize, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.getEncoder().encode(privateKey.getEncoded()));
        // 将公钥和私钥保存到Map
        System.out.println("公钥:" + publicKeyString);
        System.out.println("私钥:" + privateKeyString);
    }

    public static void main(String[] args) throws Exception {
//        genKeyPair(2048);
//MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnXGLzkLAH0qsOa26JFdR5foJ/aBTE6nq9rSID+AEh+jyx7iMLX2QmvTcCT1XevFkOeWW/nJSjKtu1ypYyjf/aqintd//hSClTaLgqOpDemGJon1JrlFmNuG2GPztk4hVzcG9XWBc2+PHg3Y5F6NxKZ8eK+Xi6VChaWrafNEbmAFX9Nqe3meQe6Ec/zePIMcg1yiteyyAPjuFLjENwkHqw8h2V1GQ3awRUo/15vdI6edAqQjx7L3/5KDnJfQ40Qk4BqMt2wsjCDJmIkwU7S89apQF30r8isIDtzXOkqEBJ753rcI/Ny2iZ50L+UoNHMGJuoxb2TubEn+Z9LUgrHEAMQIDAQAB
//MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCdcYvOQsAfSqw5rbokV1Hl+gn9oFMTqer2tIgP4ASH6PLHuIwtfZCa9NwJPVd68WQ55Zb+clKMq27XKljKN/9qqKe13/+FIKVNouCo6kN6YYmifUmuUWY24bYY/O2TiFXNwb1dYFzb48eDdjkXo3Epnx4r5eLpUKFpatp80RuYAVf02p7eZ5B7oRz/N48gxyDXKK17LIA+O4UuMQ3CQerDyHZXUZDdrBFSj/Xm90jp50CpCPHsvf/koOcl9DjRCTgGoy3bCyMIMmYiTBTtLz1qlAXfSvyKwgO3Nc6SoQEnvnetwj83LaJnnQv5Sg0cwYm6jFvZO5sSf5n0tSCscQAxAgMBAAECggEAKUPuabQzGMCAnWl8RTQhwg4j+jbAL4I0lE7C7q4/YcPbhh9HllC3yvVsintqZ03HgCk9xxj9q+Lu3TQ7K10785+avV+lYCYrTUroaT1lTDkuUslVu3uYN8A28xuvxSBRpOknkcbE79kmnL0qv5hV+5u507WgIJjChZe9BkPkYJVnj9g516R5UzfNA8Hxeq2fGRiHQ1q+T6ueL7t+eWpkyk9Q95jjIEkfHdfo41KdUqi/KKCLKxMoIqE/ubUzWwxP+MH6IvF+NivwzOYAk8pgD5DwRva6V4QsBxJ9yUDvp71r/RQA4ekYlLngaTGcQGj8SDVNDUN2Uf+aPn7I+nPhxQKBgQDifLG5lfPT0d6dvGyYkv73l9QeVpehvDXmZqcl6j6nL+1TstuGUrgZVzFNnvyJETsYCyv0S6ruKe/Ky59dBk7JJU/+M9XiXOndb+4b5B2lToZnAAXo4sNJO4ZfaPT4k6gJCAcMZkVkyoCHyhdvNeNhxZq9eiAiilPTaq0a/T7ujwKBgQCx9abSPPToSmtSe2XDh4VWJDJCsFzdwa0vIYgdNxH4hvZ26LEyqSyFpEHtV1pB3JskMxkSBehhz0xAoQVFqepVSiDQOezRjW3WletAl0eyV0PyXSVfRhIFRBoSa9GMH3clY3RvwiNTiiWqRuyMULHxCLKHEZX0ic/BvR7gmbyFPwKBgHUbAFHzJNWbKD3q1UO2jbq7eGQPZeusgzoUTcye57JOZ6nlSviTALmpicaWtACzNhjifeGc1k66xE/ZJRdWUZpTfp7zcl3OqOVkIHuvYVy902SicGvQBCVzb7EZdcCpqDKzQeG5Z5wL0JVleuR3upAtOitKYB874sfn2oWf99AlAoGAZtUaVrYelvQZ3AmuHjR7WfGEkYCzyPiXiHqJ2sftvHzQgpmneyzCq17lMa6eWxyNEXftbrq3M33EBDyHfV/YJvy0xV4TeGuPIsZysFo6ddVzT+N/KvSkiSJ3JlnjNmMgSByWNdYq76UOarydB9/BUxTBhMcmra3MvZj49LQheaUCgYAOqU78aIW7hsyDmozN3qcID9GkmSwc3HaoGdK6YR6JAblcCX88pxZSNGDCZ4cgGmrONGNfdNJnXnqsWPTIXZ/CEAydhXLbvzDQ9gLX7psQg6ZoL4+ubdN95syXcVqAW1PwHDL3slPcuKHUVsCx7zg7DjjlyiOMLKBEdnx1RMkmfA==
        String enstr = encrypt("{\"orderId\":\"1231\"}",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnXGLzkLAH0qsOa26JFdR5foJ/aBTE6nq9rSID+AEh+jyx7iMLX2QmvTcCT1XevFkOeWW/nJSjKtu1ypYyjf/aqintd//hSClTaLgqOpDemGJon1JrlFmNuG2GPztk4hVzcG9XWBc2+PHg3Y5F6NxKZ8eK+Xi6VChaWrafNEbmAFX9Nqe3meQe6Ec/zePIMcg1yiteyyAPjuFLjENwkHqw8h2V1GQ3awRUo/15vdI6edAqQjx7L3/5KDnJfQ40Qk4BqMt2wsjCDJmIkwU7S89apQF30r8isIDtzXOkqEBJ753rcI/Ny2iZ50L+UoNHMGJuoxb2TubEn+Z9LUgrHEAMQIDAQAB");
        System.out.println(enstr);

        String s = decrypt(enstr,"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCdcYvOQsAfSqw5rbokV1Hl+gn9oFMTqer2tIgP4ASH6PLHuIwtfZCa9NwJPVd68WQ55Zb+clKMq27XKljKN/9qqKe13/+FIKVNouCo6kN6YYmifUmuUWY24bYY/O2TiFXNwb1dYFzb48eDdjkXo3Epnx4r5eLpUKFpatp80RuYAVf02p7eZ5B7oRz/N48gxyDXKK17LIA+O4UuMQ3CQerDyHZXUZDdrBFSj/Xm90jp50CpCPHsvf/koOcl9DjRCTgGoy3bCyMIMmYiTBTtLz1qlAXfSvyKwgO3Nc6SoQEnvnetwj83LaJnnQv5Sg0cwYm6jFvZO5sSf5n0tSCscQAxAgMBAAECggEAKUPuabQzGMCAnWl8RTQhwg4j+jbAL4I0lE7C7q4/YcPbhh9HllC3yvVsintqZ03HgCk9xxj9q+Lu3TQ7K10785+avV+lYCYrTUroaT1lTDkuUslVu3uYN8A28xuvxSBRpOknkcbE79kmnL0qv5hV+5u507WgIJjChZe9BkPkYJVnj9g516R5UzfNA8Hxeq2fGRiHQ1q+T6ueL7t+eWpkyk9Q95jjIEkfHdfo41KdUqi/KKCLKxMoIqE/ubUzWwxP+MH6IvF+NivwzOYAk8pgD5DwRva6V4QsBxJ9yUDvp71r/RQA4ekYlLngaTGcQGj8SDVNDUN2Uf+aPn7I+nPhxQKBgQDifLG5lfPT0d6dvGyYkv73l9QeVpehvDXmZqcl6j6nL+1TstuGUrgZVzFNnvyJETsYCyv0S6ruKe/Ky59dBk7JJU/+M9XiXOndb+4b5B2lToZnAAXo4sNJO4ZfaPT4k6gJCAcMZkVkyoCHyhdvNeNhxZq9eiAiilPTaq0a/T7ujwKBgQCx9abSPPToSmtSe2XDh4VWJDJCsFzdwa0vIYgdNxH4hvZ26LEyqSyFpEHtV1pB3JskMxkSBehhz0xAoQVFqepVSiDQOezRjW3WletAl0eyV0PyXSVfRhIFRBoSa9GMH3clY3RvwiNTiiWqRuyMULHxCLKHEZX0ic/BvR7gmbyFPwKBgHUbAFHzJNWbKD3q1UO2jbq7eGQPZeusgzoUTcye57JOZ6nlSviTALmpicaWtACzNhjifeGc1k66xE/ZJRdWUZpTfp7zcl3OqOVkIHuvYVy902SicGvQBCVzb7EZdcCpqDKzQeG5Z5wL0JVleuR3upAtOitKYB874sfn2oWf99AlAoGAZtUaVrYelvQZ3AmuHjR7WfGEkYCzyPiXiHqJ2sftvHzQgpmneyzCq17lMa6eWxyNEXftbrq3M33EBDyHfV/YJvy0xV4TeGuPIsZysFo6ddVzT+N/KvSkiSJ3JlnjNmMgSByWNdYq76UOarydB9/BUxTBhMcmra3MvZj49LQheaUCgYAOqU78aIW7hsyDmozN3qcID9GkmSwc3HaoGdK6YR6JAblcCX88pxZSNGDCZ4cgGmrONGNfdNJnXnqsWPTIXZ/CEAydhXLbvzDQ9gLX7psQg6ZoL4+ubdN95syXcVqAW1PwHDL3slPcuKHUVsCx7zg7DjjlyiOMLKBEdnx1RMkmfA==");
        System.out.println(s);
    }
}
