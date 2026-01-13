package org.loginutils.common.mgr.utils;


import java.security.MessageDigest;

public class SignUtils {

    private static final String MD5_STRING = "D41D8CD98F00B204E9800998ECF8427E";

    public static String makeSignByPasswordMD5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5加密失敗", e);
        }
    }
}
