package com.flowingsun.common.utils;

import org.apache.commons.codec.binary.Base64;
//import sun.misc.BASE64Encoder;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *@Author Lyon[flowingsun007@163.com]
 *@Date 18/05/9 17:29
 *@Description 用户密码MD5加密解密校验
 */
public class MD5Utils {
    public static String encryptPassword(String saltPass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //BASE64Encoder base64Encoder = new BASE64Encoder();
        //String result = base64Encoder.encode(md5.digest(saltPass.getBytes("utf-8")));
        String result = Base64.encodeBase64String(md5.digest(saltPass.getBytes("utf-8")));
        System.out.println(result);
        return result;
    }

    public  static boolean  checkPassword(String inputPass,String userPhone, String dbSalt, String dbSaltedPwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String saltPass = inputPass + userPhone + dbSalt;
        String result = encryptPassword(saltPass);
        if(result.equals(dbSaltedPwd)){
            return true;
        }else {
            return false;
        }
    }

}