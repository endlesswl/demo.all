package com.lingcaibao.pay;

import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	public static String encrypt(String encryptString,
									String encryptKey,String charsetName)
		throws Exception {
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes(charsetName));
		String encode = Base64Des.encode(encryptedData);
		return encode.replace(" ", "-");
		// return Base64.encode(encryptedData);
	}

	public static String decrypt(String decryptString,
									String decryptKey,String charsetName)
		throws Exception {
		decryptString = decryptString.replace("-", " ");
		byte[] byteMi = new Base64Des().decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(charsetName), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);
		return new String(decryptedData);
	}

	public static String getKey() {
		UUID randomUUID = UUID.randomUUID();
		String uuid = randomUUID.toString();
		return uuid.substring(0, 8);
	}

	public static void main(String[] args) {
		UUID randomUUID = UUID.randomUUID();
		String uuid = randomUUID.toString();
		System.out.println(uuid);
		uuid = uuid.substring(0, 8);
		System.out.println(uuid);
		String key = uuid;
		String text = "dfjskldfsdjfkljsldfkjskldfjskldfsldjklsjlkdfjskldjfldfjskldfsdjfkljsldfkjskldfjskldfsldjklsjlkdfjskldjfldfjskldfsdjfkljsldfkjskldfjskldfsldjklsjlkdfjskldjfl";
		System.out.println(text.length());
		try {
			String result1 = DESUtil.encrypt(text, key,"utf-8");
			System.out.println(result1.length());
			String result2 = DESUtil.decrypt(result1, key,"utf-8");
			System.out.println(result1);
			System.out.println(result2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}