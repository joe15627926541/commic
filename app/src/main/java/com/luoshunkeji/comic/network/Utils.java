package com.luoshunkeji.comic.network;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    /**
     * 计算字符串的MD5值
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] MD5(String str) throws NoSuchAlgorithmException {
        return MD5(str.getBytes());
    }

    /**
     * 计算字节数据的MD5值
     *
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] MD5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(bytes, 0, bytes.length);
        return m.digest();
    }

    /**
     * 转换16进制表达的字符串
     *
     * @param bytes
     * @return
     */
    public static String hexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int b = (0xFF & bytes[i]);
            if (b <= 0xF)
                sb.append("0");
            sb.append(Integer.toHexString(b));
        }
        return sb.toString();
    }

    /**
     * 是否中国大陆手机号码
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneChina(String phone) {
        if (phone == null || phone.length() == 0) {
            return false;
        }
        String regex = "^1[3458]\\d{9}$";
        return phone.matches(regex);
    }
}
