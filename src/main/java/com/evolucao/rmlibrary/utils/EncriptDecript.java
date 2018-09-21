package com.evolucao.rmlibrary.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncriptDecript {
	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };

	public static String encrypt(String Data) {
		String encryptedValue = null;
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			
			// Encoded para UTF8 para representar corretamente acentos
			byte[] encUtf8 = Data.getBytes("UTF8");
			
			//byte[] encVal = c.doFinal(Data.getBytes("UTF8"));
			byte[] encVal = c.doFinal(encUtf8);
			
			// Encoded BASE64 para transmissao
			encryptedValue = new BASE64Encoder().encode(encVal);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return encryptedValue;
    }

    public static String decrypt(String encryptedData) {
    	String decryptedValue = null;
    	try {
    		Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            
            // Decode do BASE64 vindo de uma transmissao
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
            
            // Decriptou o conteudo
            byte[] decValue = c.doFinal(decordedValue);
            
            // Decodificou do UTF8 para colocar novamente os acentos 
            decryptedValue = new String(decValue, "UTF8");    		
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
        
        return decryptedValue;
    }
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }
}
