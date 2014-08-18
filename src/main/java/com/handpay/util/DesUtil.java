package com.handpay.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.handpay.core.common.util.ObjectUtil;
import com.handpay.mina.server.ConsoleTextArea;

public class DesUtil {
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this.getClass());

	public static String des3Encrypt(String message, String key)
			throws Exception {
		if (ObjectUtil.isNull(message)){
			message = "";
		}
		StringBuffer buf = new StringBuffer();
		buf.append(message);
		int len = (message.length() + 8) / 8 * 8 - message.length();
		for (int i = 0; i < len; i++) {
			buf.append("\0");
		}
		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");

		DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		String enc = new String(Base64.encodeBase64(cipher.doFinal(buf.toString()
				.getBytes())));
		return enc;
	}

	public static String des3DeEncrypt(String message, String key)
			throws Exception {

		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");

		DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		String dec = new String(cipher.doFinal(Base64.decodeBase64(message
				.getBytes())));
		
		return dec;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(DesUtil.des3DeEncrypt(
				"ZUY2KJ5MwMMiorYQTf6BPvkg12HdV5fWrzlHMnD+PEGY3NJ03GZDa7JnFD7i6w2HkSTEjveDOBFnreUjH3RcCPoqE+1Ltcef",
				"abcdefgh12345678ccbttf21"));
	}

}
