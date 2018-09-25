package com.xdaocloud.futurechain.util;

import com.xdaocloud.futurechain.constant.Constant;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易IM 工具
 *
 * @author LuoFuMin
 * @data 2018/9/10
 */
public class YunXinUtils {

    // 计算并获取CheckSum
    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    // 计算并获取md5值
    public static String getMD5(String requestBody) {
        return encode("md5", requestBody);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest
                    = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 云信 headers
     *
     * @return
     */
    public static Map<String, String> getheaders() {
        Map<String, String> headers = new HashMap<>(4);
        String nonce = RandomUtils.getNumberId();
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = YunXinUtils.getCheckSum(Constant.NETEASE_APP_SECRET, nonce, curTime);
        headers.put("CheckSum", checkSum);
        headers.put("Nonce", nonce);
        headers.put("CurTime", curTime);
        headers.put("AppKey", Constant.NETEASE_APP_KEY);
        return headers;
    }
}
