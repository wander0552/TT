package com.wander.mylibrary.utils.crypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

/*
 * auther GaoZefeng 080710
 */
public class SecurityCoderX {
	
	/**
	 * 直接加密一个文件
	 * @param fileName
	 * @param xorString
	 */
	public static boolean encryptFile(String fileName, String xorString){
		boolean succ = true;
		try{
			File f = new File(fileName);
			long size = f.length();
			if(size < Integer.MAX_VALUE){
				byte[] b = new byte[(int)size];
				FileInputStream fis = new FileInputStream(f);
				fis.read(b, 0, (int)size);
				fis.close();
				String str = encryptByte(b, xorString);
				str = str.replaceAll("\r\n", "");
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(str.getBytes());
				fos.flush();
				fos.close();
			}
			else{
				succ = false;
			}
		}catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			succ = false;
		}
		return succ;
	}
	
	/**
	 * 直接解密一个文件到临时文件里
	 * @param fileName
	 * @param tmpFile
	 * @param xorString
	 * @return
	 */
	public static boolean decryptFile(String fileName, String tmpFile, String xorString){
		boolean succ = true;
		try{
			File f = new File(fileName);
			File exp = new File(tmpFile);
			long size = f.length();
			if(size < Integer.MAX_VALUE){
				byte[] b = new byte[(int)size];
				FileInputStream fis = new FileInputStream(f);
				fis.read(b, 0, (int)size);
				fis.close();
				byte[] out = decryptByte(new String(b), xorString);
				FileOutputStream fos = new FileOutputStream(exp);
				fos.write(out);
				fos.flush();
				fos.close();
			}
			else{
				succ = false;
			}
		}catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			succ = false;
		}
		return succ;
	}
	
	/**
	 * 直接对字节数组加密
	 * @param strBytes
	 * @param xorString
	 * @return
	 */
	public static String encryptByte(byte[] strBytes, String xorString){
		try{
			int k = 0;	 
			byte[] xorBytes = xorString.getBytes("UTF8");	
			int strLen=strBytes.length;
			int xorLen=xorBytes.length;	
			for(int i=0; i<strLen && xorLen > 0;){
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
	
	
	/**
	 * 解密成字节数组
	 * @param str
	 * @param xorString
	 * @return
	 */
	public static byte[] decryptByte(String str, String xorString){
		byte[] strBytes = Base64Coder.decode(str);
		int strLen = strBytes.length;
		if(strLen<=0){
			return null;
		}
		try {
			byte[] xorBytes = xorString.getBytes("UTF8");
			int xorLen=xorBytes.length;
		    for (int i = 0; i < strLen && xorLen > 0; )
		    {
		        for (int j = 0; j < xorLen && i < strLen; j++)
		        {       
		            strBytes[i++] ^= xorBytes[j];
		        }
		    }
		    return strBytes;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 加密
	 */
	public static String encrypt (String str, String xorString) {
		try{
			int k = 0;	
			byte[] strBytes = str.getBytes("GBK");	 
			byte[] xorBytes = xorString.getBytes("UTF8");	
			int strLen=strBytes.length;
			int xorLen=xorBytes.length;	
			for(int i=0; i<strLen && xorLen>0 ;){
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
	 * 解密
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
		    for (int i = 0; i < strLen && xorLen > 0; )
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
/*
 int base64_encode(char *b64store, const char *str, int length, const char *xor_string)
{
    char *__tmp = new char[length];
    memcpy(__tmp, str, length);

    int __len = strlen(xor_string);

    for (int i = 0; i < length; )
    {
        for (int j = 0; j < __len && i < length; j++)
        {       
            __tmp[i++] ^= xor_string[j];
        }       
    }

    int ret = base64_encode(b64store, __tmp, length);
    delete []__tmp;

    return ret;
}

int base64_decode(char *to, const char *base64, const char *xor_string)
{
    int ret = base64_decode(to, base64);
    if (ret == -1)
        return -1;

    int __len = strlen(xor_string);

    for (int i = 0; i < ret; )
    {
        for (int j = 0; j < __len && i < ret; j++)
        {       
            to[i++] ^= xor_string[j];
        }       
    }

    return ret;
}

	/*
	 * 加密
	 */
	public static String encryptUTF8 (String str, String xorString) {
		try{
			System.out.println(str);
			int k = 0;	
			byte[] strBytes = str.getBytes("UTF8");	 
			byte[] xorBytes = xorString.getBytes("UTF8");	
			int strLen=strBytes.length;
			int xorLen=xorBytes.length;	
			
			System.out.println(new String(strBytes, "utf-8"));
			System.out.println("===========");
			for(int i=0; i<strLen && xorLen>0 ;){
				System.out.println(strBytes[i]);
		        for (int j = 0; j < xorLen && i < strLen; j++){ 
		        	byte ccc = (strBytes[i++] ^= xorBytes[j]);
		        }
			}
//			return Base64Coder.encode(strBytes);
			return new String(strBytes);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	/*
	 * 解密
	 */
	
	public static String decryptUTF8(String str, String xorString) {
		byte[] strBytes = Base64Coder.decode(str);
		int strLen = strBytes.length;
		if(strLen<=0){
			return "";
		}
		try {
			byte[] xorBytes = xorString.getBytes("UTF8");
			int xorLen=xorBytes.length;
		    for (int i = 0; i < strLen && xorLen > 0; )
		    {
		        for (int j = 0; j < xorLen && i < strLen; j++)
		        {       
		            strBytes[i++] ^= xorBytes[j];
		        }
		    }
		    return new String(strBytes, "UTF8");
		} catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

public static void main(String[] args) throws UnsupportedEncodingException {
//	String str="|ART=我们是中国人|p=1203";
//	System.out.println("input:");
//	System.out.println(str);
//	System.out.println("Base64后");
//	System.out.println(Base64Coder.encodeString(str));
//	System.out.println("加密后:");
//	byte[] b =str.getBytes();
	//for(int i=0;i<b.length;i++)
		//System.out.print(Integer.toHexString(b[i]));
//	String m="";
	String s=encrypt("0\r\n", "");
//	System.out.println(s);
//	System.out.println(decrypt(s, "yeelion "));
	//System.out.println(Base64Coder.encodeString("music_2.8.1.1_ug6"));
	System.out.println(decrypt("f0lHAnlfTH1/ZFJIY3R4HH5cTH1/ZFJIY3R5HH5cTH0=", "NDM1NjAwMTcx"));
	
	//String str = encrypt("aa","yeelion");
}
} // end class Base64Coder

