package it.unicam.cs.cohesion.servlet.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class GeneralUtils {

	public static void copyInputStreamToOutputStream(InputStream input, OutputStream output) throws Exception{
		int n = 0;
		int DEFAULT_BUFFER_SIZE = 1024 * 4;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		while (-1 != (n = input.read(buffer)))
			output.write(buffer, 0, n);
	}
	
	public static byte[] toByteArray(InputStream is) throws Exception{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copyInputStreamToOutputStream(is, out);
	    return out.toByteArray();
	}
	
	public static String base64Encode(byte[] data){
		return DatatypeConverter.printBase64Binary(data);
	}
	
	public static byte[] base64Decode(String data){
		return DatatypeConverter.parseBase64Binary(data);
	}
	
	public static byte[] cipher3DES(boolean encrypt, byte[] message, byte[] key, byte[] ivParameter, boolean noPadding) throws Exception {
		try{

	    	if(key.length != 24)
	    		throw new Exception("key size must be 24 bytes");
	    	
			String blockMode = "CBC";
			if(ivParameter == null)
				blockMode = "ECB";
			
			int cipherMode = Cipher.DECRYPT_MODE;
			if(encrypt)
				 cipherMode = Cipher.ENCRYPT_MODE;
			String padding = "PKCS5Padding";
			if(noPadding)
				padding = "NoPadding";
			Cipher sendCipher = Cipher.getInstance("DESede/"+blockMode+"/"+padding);
			SecretKey myKey =  new SecretKeySpec(key, "DESede");
			
			if(ivParameter==null)
				sendCipher.init(cipherMode, myKey);
			else
				sendCipher.init(cipherMode, myKey, new IvParameterSpec(ivParameter));
			
			return sendCipher.doFinal(message);
			
		}catch(Exception e){e.printStackTrace();}
		return new byte[0];
    }
}
