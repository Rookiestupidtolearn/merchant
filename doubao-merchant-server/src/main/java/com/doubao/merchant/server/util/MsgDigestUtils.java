package com.doubao.merchant.server.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MsgDigestUtils {

	private final static Logger logger = Logger.getLogger(MsgDigestUtils.class);

	//public static PrivateKey privateKey = null;
	/**
	 * 公钥，
	 */
	//public static PublicKey publicKey = null;

	static {
		try {
			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 *初始化私钥  by 私钥Str
	 * @param privateBase64edKey
	 */
	public  static PrivateKey initPrivateKeyByKeyString(String privateBase64edKey) {

		try {

			return getPrivateKeyStr(privateBase64edKey);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 初始化公钥  by 公钥Str
	 * @param publicBase64edKey
	 */
	public  static PublicKey initPublicKeyByKeyString(String  publicBase64edKey) {


		try {
			return getPublicKeyByStr(publicBase64edKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
             return null;
	}
	
	

	/**
	 * 对传入字符串进行签名
	 * 
	 * @param inputStr
	 * @return @
	 */
	public  static String sign(String inputStr,String privateBase64edKey ) {
		String result = null;
		try {

				// 初始化
				PrivateKey privateKey=initPrivateKeyByKeyString(privateBase64edKey);

			byte[] tByte;
			Signature signature = Signature.getInstance("SHA1withRSA", "BC");
			signature.initSign(privateKey);
			signature.update(inputStr.getBytes("UTF-8"));
			tByte = signature.sign();
			result = Base64.encode(tByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 对返回的数据进行验签
	 * 
	 * @param src
	 *            返回数据明文
	 * @param signValue
	 *            返回数据签名
	 * @return
	 */
	public static boolean  verifySign(String src, String signValue,String publicBase64edKey ) {
		boolean bool = false;
		try {
			if (StringUtils.isBlank(src) || StringUtils.isBlank(signValue)) {
				return false;
			}

			PublicKey publicKey=initPublicKeyByKeyString(publicBase64edKey);

			Signature signature = Signature.getInstance("SHA1withRSA", "BC");
			signature.initVerify(publicKey);
			signature.update(src.getBytes("UTF-8"));
			bool = signature.verify(Base64.decode(signValue));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	
	private  static PublicKey getPublicKeyByStr(String publicBase64edKey) {
		
		KeyFactory kf;
		PublicKey publickey = null;
		try {
			kf = KeyFactory.getInstance("RSA", "BC");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicBase64edKey));
			publickey = kf.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publickey;
	}
	
	private  static PrivateKey getPrivateKeyStr(String privateBase64edKey) {
		
		KeyFactory kf;
		
		PrivateKey privateKey = null;
		try {
			kf = KeyFactory.getInstance("RSA", "BC");
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateBase64edKey));
			privateKey = kf.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return privateKey;
	}
	
	

	public  String readFile(String fileName) {
		try {

			logger.info(MsgDigestUtils.class.getResource("/").getPath() + "prkey.key");
			fileName = MsgDigestUtils.class.getResource("/").getPath() + fileName;

			File f = new File(fileName);
			FileInputStream in = new FileInputStream(f);
			int len = (int) f.length();

			byte[] data = new byte[len];
			int read = 0;
			while (read < len) {
				read += in.read(data, read, len - read);
			}
			in.close();
			return new String(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
