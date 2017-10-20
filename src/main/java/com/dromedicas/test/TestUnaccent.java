package com.dromedicas.test;

import java.text.Normalizer;

public class TestUnaccent {
	
	static String accents = "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,Ç,ç,Ã,ã,Õ,õ";
	
	public static void main(String[] args){
		
		String result = unaccent(accents);
		
		System.out.println("-->"+ result);
		
	}
	
	
	public static String unaccent(String src) {
		return Normalizer
				.normalize(src, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
	}
}
