package com.wander.mylibrary.utils.crypt;


/**
 * Seriously, you should say something about your code
 * Author: hongze
 * Date: 13-9-3
 * Time: 下午4:53
 */
public class SecurityCoder {
    /*
	 *  异或加密
	 */
	public static String encrypt (String str, String xorString) {
		try{
			byte[] strBytes = str.getBytes("GBK");
			byte[] xorBytes = xorString.getBytes("UTF8");
			int strLen=strBytes.length;
			int xorLen=xorBytes.length;
			for(int i=0; i<strLen&&xorLen>0;){
		        for (int j = 0; j < xorLen && i < strLen; j++){
		        	strBytes[i++] ^= xorBytes[j];
		        }
			}
			return Base64Coder.encode(strBytes);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	/*
	 *  异或解密
	 */

	public static String decrypt(String str, String xorString) {
		byte[] strBytes = Base64Coder.decode(str);
		int strLen = strBytes.length;
		if(strLen<=0){
			return "";
		}
		try {
			byte[] xorBytes = xorString.getBytes("UTF8");
			int xorLen=xorBytes.length;
		    for (int i = 0; i < strLen&&xorLen>0; )
		    {
		        for (int j = 0; j < xorLen && i < strLen; j++)
		        {
		            strBytes[i++] ^= xorBytes[j];
		        }
		    }
		    return new String(strBytes, "GBK");
		} catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
}
