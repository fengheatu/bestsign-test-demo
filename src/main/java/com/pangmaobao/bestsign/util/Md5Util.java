package com.pangmaobao.bestsign.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author he.feng
 */
public class Md5Util {

    private final static String[] strDigits =
            { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * Md 5 hex string.
     * 进行MD5 16进制的签名 返回为32位字符。
     *
     * @param plaintext the plaintext
     * @return the string
     */
    public static String md5Hex(String plaintext) {
        String ciphertext = null;
        if (plaintext != null) {
            ciphertext = DigestUtils.md5Hex(plaintext);
        }
        return ciphertext;
    }

    /**
     * byte to array string
     * @param bByte bByte
     * @return the string
     */
    private static String byteToArrayString(byte bByte)
    {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0)
        {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * byte to string
     * @param bByte bByte
     * @return the string
     */
    private static String byteToString(byte[] bByte)
    {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++)
        {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * get md5 code
     *
     * @param strObj str obj
     * @return the string
     */
    public static String GetMD5Code(String strObj)
    {
        String resultString = null;
        try
        {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");

            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        return resultString;
    }
}
