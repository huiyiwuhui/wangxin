package com.yhfund.app.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 13-11-7
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
public class MD5Encryption {
    /**
     * 32位小写
     * @param plainText
     * @return
     */
    public static String getMd5(String plainText ) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try {
                md.update(plainText.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if(i<0) i+= 256;
                if(i<16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        String smsg="20130416channel2functionC010meridYHversionv1.020130416";
        String md5= MD5Encryption.getMd5(smsg);

        System.out.println(md5);
    }
}
