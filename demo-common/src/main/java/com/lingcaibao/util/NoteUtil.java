package com.lingcaibao.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class NoteUtil {
	
	public static String encode64(byte[] bbs){
		BASE64Encoder encoder=new BASE64Encoder();
		String str=encoder.encode(bbs);
		return str;
	}
	
	public static String decode64(String msg){
		BASE64Decoder decoder=new BASE64Decoder();
		try {
			byte[] bbs=decoder.decodeBuffer(msg);
			return new String(bbs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static String md5(String msg){
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte[] bbs=md.digest(msg.getBytes());
			String str=encode64(bbs);
			return str;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String createuuid(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
	public static String currentTime(){
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static void main(String[] args) {
		//System.out.println(md5("123"));
	}
	
	
	 public static String SHA1(String decript) {
	        try {
	            MessageDigest digest = java.security.MessageDigest
	                    .getInstance("SHA-1");
	            digest.update(decript.getBytes());
	            byte messageDigest[] = digest.digest();
	            // Create Hex String
	            StringBuffer hexString = new StringBuffer();
	            // 字节数组转换为 十六进制 数
	            for (int i = 0; i < messageDigest.length; i++) {
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
	                if (shaHex.length() < 2) {
	                    hexString.append(0);
	                }
	                hexString.append(shaHex);
	            }
	            return hexString.toString();
	 
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return "";
	    }
	
}
