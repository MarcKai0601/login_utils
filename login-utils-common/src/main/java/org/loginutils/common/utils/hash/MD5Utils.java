package org.loginutils.common.utils.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密工具类
 */
public class MD5Utils {
    /**
     * 定义一个MD5的加密串
     */
    private static String MD5STR = "EuZMGDTOPquLovvWV4o0POBn";

    /**
     * MD5加密(加上一个加密串)
     *
     * @param value 要加密的字符串
     * @return String
     */
    public static String getMD5String(String value) {
        try {
            value = value + MD5STR;
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] e = md.digest(value.getBytes());
            return toHexString(e);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return value;
        }
    }

    /**
     * 转换
     *
     * @param bytes bytes
     * @return String
     */
    private static String toHexString(byte[] bytes) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0xff);
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            }
            else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }

}