package com.pangmaobao.bestsign.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;


/**
 * @author he.feng
 */
public class Base64Util {
    private static Base64 base64 = new Base64();

    /**
     * Base 64 encode string.
     * 将String 明文 进行base64加密为String类型的密文。
     *
     * @param plainText the plain text
     * @return the string
     */
    public static String base64Encode(String plainText) throws UnsupportedEncodingException {
        String cipherText = null;
        if (plainText != null) {
            cipherText = base64.encodeToString(plainText.getBytes("utf-8"));
        }
        return cipherText;
    }

    /**
     * Base 64 decode string.
     * 将base64 密文 解码为String类型的明文。
     *
     * @param cipherText the cipher text
     * @return the string
     */
    public static String base64Decode(String cipherText) throws UnsupportedEncodingException {
        String plainText = null;
        if (cipherText != null) {
            plainText = new String(base64.decode(cipherText), "utf-8");
        }
        return plainText;
    }
}
