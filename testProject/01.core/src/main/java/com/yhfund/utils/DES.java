package com.yhfund.utils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class DES {
	public static final String DESKEY = "wxtlyhjj";
    private static byte[] iv = {1,2,3,4,5,6,7,8};
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
//      IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

        return Base64.encode(encryptedData);
    }
    public static String decryptDES(String decryptString, String decryptKey) {
    	try {
    		byte[] byteMi = Base64.decode(decryptString);
    		IvParameterSpec zeroIv = new IvParameterSpec(iv);
//      IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
    		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
    		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
    		byte decryptedData[] = cipher.doFinal(byteMi);     
    		return new String(decryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return null;
    }
    
    public static void main(String[] args) {
    	try {
    		//System.out.println(DES.decryptDES("AF7ZHYa9e+4=", DESKEY));
			System.out.println(DES.encryptDES("666666", DESKEY));
    		//System.out.println(DateUtil.getFormatDay(new Date(1404172047051l)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
