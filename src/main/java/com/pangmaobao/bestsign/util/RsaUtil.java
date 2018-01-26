package com.pangmaobao.bestsign.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * @author he.feng
 */
public class RsaUtil {
    private static Logger logger = LoggerFactory.getLogger(RsaUtil.class);
    private static final int MAX_DECRYPT_BLOCK = 128;
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKey privateK = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
//        return Base64.decode(base64.getBytes());
        return new Base64().decode(base64.getBytes());
    }

    public static byte[] encode(String base64) {
        return new Base64().encode(base64.getBytes());
    }

    /**
     * RSA私钥签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String signWithPriKey(String content, String privateKey, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new Base64().decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return new String(new Base64().encode(signed), encode);
        } catch (Exception e) {
            logger.error("进行rsa签名发生异常", e);
            throw new RuntimeException("签名发生异常");
        }

    }

    /**
     * RSA私钥签名
     *
     * @param content
     * @param privateKey
     * @return
     */
    public static String signWithPriKey(String content, String privateKey) {
        return signWithPriKey(content, privateKey, DEFAULT_ENCODING);
    }

    /**
     * RSA公钥签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean verifySignWithPubKey(String content, String sign, String publicKey, String encode) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = new Base64().decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));
            return signature.verify(new Base64().decode(sign));
        } catch (Exception e) {
            logger.error("进行rsa签名校验发生异常", e);
        }
        return false;
    }

    /**
     * RSA公钥签名检查
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean verifySignWithPubKey(String content, String sign, String publicKey) {
        logger.info("开始使用rsa公钥进行签名验证,原始签名sign=[" + sign + "]");
        return verifySignWithPubKey(content, sign, publicKey, DEFAULT_ENCODING);
    }
}
