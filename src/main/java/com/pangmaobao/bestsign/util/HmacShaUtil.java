package com.pangmaobao.bestsign.util;

import org.apache.commons.net.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author he.feng
 */
public class HmacShaUtil {
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String ENCODING = "UTF-8";

    /**
     * HmacSha256秘钥加密
     **/
    public static final String HmacSha256(String message, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), HMAC_SHA256);
        sha256_HMAC.init(secret_key);
        byte[] b = sha256_HMAC.doFinal(message.getBytes());
        String result = Base64.encodeBase64String(byte2hex(b).getBytes("UTF8"));
        // Base64,据RFC 822规定，每76个字符，还需要加上一个回车换行,php没有
        return result.replaceAll("\r", "").replaceAll("\n", "");
    }

    private static String byte2hex(final byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString();
    }

}
