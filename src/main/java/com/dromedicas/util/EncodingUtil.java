package com.dromedicas.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;


public class EncodingUtil {
	
	/**
	 * Genera y retorna un Universally unique identifier.
	 * https://en.wikipedia.org/wiki/Universally_unique_identifier
	 * @return
	 */
	public static String generateUUID(){
		UUID idOne = UUID.randomUUID();
		String tocken = String.valueOf(idOne).replace("-", "");
		return tocken;
	}

	/**
	 * Recibe una cadena y la retorna codificada 
	 * @param str
	 * @return
	 */
	public static String encodeBase64(String str){	
		byte[] bytes;
		String encoded = null;
		try {
			bytes = str.getBytes("UTF-8");
			encoded = DatatypeConverter.printBase64Binary(bytes);
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}		
		return encoded;
	}
	
	
	public static String decodeBase64(String encoded){
		String decoded =  null;
		byte[] bytes = DatatypeConverter.parseBase64Binary(encoded);
		try {
			decoded = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decoded;		
	}
}
