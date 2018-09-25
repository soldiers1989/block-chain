package com.xdaocloud.futurechain.util;


import com.xdaocloud.futurechain.constant.AESConstant;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/*
 * AES对称加密和解密
 */
public class EncoderUtils {
    // 加密算法
    private static final String ALG = "AES";

    // 字符编码
    private static final String ENC = "UTF-8";

    // 密钥正规化算法
    private static final String SEC_NORMALIZE_ALG = "MD5";


    /**
     * 加密
     *
     * @param KEY
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptA(String KEY, String data) throws Exception {
        MessageDigest dig = MessageDigest.getInstance(SEC_NORMALIZE_ALG);
        byte[] key = dig.digest(KEY.getBytes(ENC));
        SecretKeySpec secKey = new SecretKeySpec(key, ALG);
        Cipher aesCipher = Cipher.getInstance(ALG);
        byte[] byteText = data.getBytes(ENC);
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(byteText);
        Base64 base64 = new Base64();
        return new String(base64.encode(byteCipherText), ENC);
    }

    /**
     * 解密
     *
     * @param KEY
     * @param ciphertext
     * @return
     * @throws Exception
     */
    public static String decryptB(String KEY, String ciphertext) throws Exception {
        MessageDigest dig = MessageDigest.getInstance(SEC_NORMALIZE_ALG);
        byte[] key = dig.digest(KEY.getBytes(ENC));
        SecretKeySpec secKey = new SecretKeySpec(key, ALG);
        Cipher aesCipher = Cipher.getInstance(ALG);
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        Base64 base64 = new Base64();
        byte[] cipherbytes = base64.decode(ciphertext.getBytes());
        byte[] bytePlainText = aesCipher.doFinal(cipherbytes);
        return new String(bytePlainText, ENC);
    }

    public static void main(String[] args) throws Exception {

        String secret = "heC21g32324cw3gH201i432410m13312WP43A4lRG22413VyQ0s";
        String data = "fumin123456789";

        String passPhrase = "FNrmTxQJPlwwuTKKFYqMsA==";

        String walletPass = "x9v8TibEU//2D2igFRin6l4u2k85GV2VmQIrbmvoavMAg7odn7T2i6zRcqEibFiVBrTzf8wJTNJhHRzEzKYYeQ==";


        String activeP = "MzvR+JdKq24yEi5yPBvjb+ZypX5uI5zAXWxXwseaTlnI8oS5ClDZuEOf85pGXABRzo9YyZh6ddcFCunSwRJYpg==";


        try {
            /*System.out.println("key=" + secret + "， data=" + data);
            long start = System.currentTimeMillis();
            String encryptData = encryptA(secret, data);
            System.out.println("加密=" + encryptData + ", cost=" + (System.currentTimeMillis() - start));
            start = System.currentTimeMillis();*/


            String decryptData = decryptB(AESConstant.KEY, "5DTEGaHYEhwdJQ54tgXJGfuHL/4Whx212v16p1yUB1AVhdn8PgK7P7glBTns48GgkOAx/R6BTC1J0ERtg8P3dw==");
            System.out.println("解密=" + decryptData );



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



