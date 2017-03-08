package com.wander.mylibrary.utils.crypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {	
	 
	private static MessageDigest md5 = null;
	private synchronized static MessageDigest getMessageDigest() {
		if (md5 == null) {
			try {
				md5 = MessageDigest.getInstance("md5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return md5;
	}
	
	public static byte[] encode(String origin) {
		if (origin == null || origin.length() == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] bytes = md.digest(origin.getBytes());
		return bytes;
	}
	
	public static byte[] encode16(String origin) {
		if (origin == null || origin.length() == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		byte[] bytes = md.digest(origin.getBytes());
		byte[] dstBytes = new byte[8];
		System.arraycopy(bytes, 4, dstBytes, 0, 8);
		bytes = null;
		return dstBytes;
	}
	
	
	public static byte[] encode(String origin, String enc) throws UnsupportedEncodingException {
		if (origin == null || origin.length() == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] bytes = md.digest(origin.getBytes(enc));
		return bytes;
	}
	
	public static byte[] encode16(String origin, String enc) throws UnsupportedEncodingException {
		if (origin == null || origin.length() == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] bytes = md.digest(origin.getBytes(enc));
		byte[] dstBytes = new byte[8];
        System.arraycopy(bytes, 4, dstBytes, 0, 8);
        bytes = null;
        return dstBytes;
	}
	
	public static byte[] encode(byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] resultbytes = md.digest(bytes);
		return resultbytes;
	}
	
	public static byte[] encode16(byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		
		MessageDigest md = getMessageDigest();
		if (md == null) {
			throw new IllegalAccessError("no md5 algorithm");
		}
		
		byte[] resultbytes = md.digest(bytes);
		byte[] dstBytes = new byte[8];
        System.arraycopy(resultbytes, 4, dstBytes, 0, 8);
        bytes = null;
        return dstBytes;
	}
	
	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			byte[] byteArray = messageDigest.digest();
			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
			return md5StrBuff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
