package com.damon.iot.bridging.utils;


import java.security.MessageDigest;

/**
 * @description: md5加密工具类
 * @author: ICE-kolin.liu
 * @datetime: 2019-08-28 11:59
 */
public class Md5Utils {
    private static final String[] HEX_DIG_ITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    /**
     * MD5加密
     *
     * @param origin      字符
     * @param charsetName 编码
     * @return
     */
    public static String MD5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null == charsetName || "".equals(charsetName)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception e) {
        }
        return resultString;
    }


    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIG_ITS[d1] + HEX_DIG_ITS[d2];
    }

}
