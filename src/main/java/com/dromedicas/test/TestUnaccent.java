package com.dromedicas.test;

import java.text.Normalizer;

public class TestUnaccent {
	
	static String accents = "�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�";
	
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
