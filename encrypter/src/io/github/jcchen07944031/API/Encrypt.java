package io.github.jcchen07944031.API;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.math.BigInteger;

public class Encrypt {

	public static String encode(Object nullObj, String data) {
		data = "McD" + data + "APP";

		String dataMD5 = MD5(data);
		if(dataMD5 == "") {
			return "";
		}

		byte[] dataBytes = DESEncrypt(dataMD5, "", "");
		if(dataBytes == null) {
			return "";
		}

		String dataBase64 = Base64Encode(dataBytes);
		if(dataBase64 == "") {
			return "";
		}
		return dataBase64.substring(0, 4) + dataMD5 + dataBase64.substring(dataBase64.length() - 4);
	}

	private static String MD5(String data) {
		try {
			MessageDigest mMessageDigest = MessageDigest.getInstance("MD5");
			mMessageDigest.update(data.getBytes());
			return new BigInteger(1, mMessageDigest.digest()).toString(16);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
		
	}

	private static byte[] DESEncrypt(String data, String key, String iv) {
		try {
			AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv.getBytes());
			SecretKeySpec mSecretKeySpec = new SecretKeySpec(key.getBytes(), "DES");
			Cipher mCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			mCipher.init(1, mSecretKeySpec, mAlgorithmParameterSpec);
			return mCipher.doFinal(data.getBytes());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private static String Base64Encode(byte[] dataBytes) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(dataBytes);
	}
}
