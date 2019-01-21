package com.doubao.merchant.server.util;

import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSONObject;


/** 
 * @ClassName: EncryptUtil 
 * @Description: 
 * @author ycs
 * @date 2019年1月16日 下午5:28:36
 *
 */
public class EncryptUtil {
    private static final String KEY = "qzcdefgabovpfgdf";  
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";  
    public static String base64Encode(byte[] bytes){  
        return Base64.encodeBase64String(bytes);  
    }  
    public static byte[] base64Decode(String base64Code) throws Exception{  
        return new BASE64Decoder().decodeBuffer(base64Code);  
    }  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));  

        return cipher.doFinal(content.getBytes("utf-8"));  
    } 
    
    public static String aesEncrypt(String content) throws Exception {      	
        return aesEncrypt(content, KEY);
    }  
    
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  

        return new String(decryptBytes);  
    }  
   
    public static String aesDecrypt(String encryptStr) throws Exception {  
        return aesDecryptByBytes(base64Decode(encryptStr), KEY);  
    }  

    public static void main(String[] args) throws Exception {
    	//使用TreeMap
		TreeMap<String, Object> treeMap	= new TreeMap<String, Object>();
		treeMap.put("amount", 			1);
		treeMap.put("cardType", 3);
		treeMap.put("mobile", "17710390085");
		treeMap.put("platformType", "12345");
		treeMap.put("queryCheckUrl", "http://117.50.60.55:8020/cardPaySynchInfo/query");
		treeMap.put("thirdTradeNo", "112392477123451547713189");
		//treeMap.put("rechargeType", "2");//充值类型
		JSONObject json =new JSONObject(treeMap);
		String content = json.toString();
        System.out.println("加密前：" + content);  

        System.out.println("加密密钥和解密密钥：" + KEY);  

        String encrypt = aesEncrypt(content, KEY);  
        System.out.println(encrypt.length()+":加密后：" + encrypt);  

        String decrypt = aesDecrypt(encrypt);  
        System.out.println("解密后：" + decrypt);  
        
        
        //{"amount":1,"cardType":3,"mobile":"17710390085","platformType":"12345","queryCheckUrl":"http://117.50.60.55:8020/cardPaySynchInfo/query","thirdTradeNo":"112392477123451547713189"}
    }
}
