package com.dromedicas.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestStringSmsReplace {
	
	public static void main(String[] args) {
		 
		 
//		 String original = "Puntos Farmanorte le informa que ha redimido ${puntos} puntos en la sucursal ${sucursal}.";
//		 System.out.println("Original String: " + original);
//		 System.out.println("Longitud: " + original.length());
//		 
//		 Pattern p = Pattern.compile("(\\{.*?\\})");
//		 Matcher m = p.matcher(original);		 
//		 StringBuffer sb = new StringBuffer();
//		 while (m.find()) {
//		     m.appendReplacement(sb, "");
//		 }
//		 m.appendTail(sb);
//		 
//		 System.out.println("Final String:" + sb.toString());
//		 System.out.println("Longitud: " + sb.toString().length());
		  

//		String txt="${Texto}";
//
//	    String re1=".*?";	// Non-greedy match on filler
//	    String re2="(\\{.*?\\})";	// Curly Braces 1
//
//	    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
//	    Matcher m = p.matcher(txt);
//	    if (m.find())
//	    {
//	        String cbraces1=m.group(1);
//	        System.out.print(""+cbraces1.toString()+""+"\n");
//	    }
		 
	    
//		String original = "Puntos Farmanorte le informa que ha redimido ${puntos} puntos en la sucursal ${sucursal}.";
//		System.out.println("Original String: " + original);
//
//		String re1 = ".*?"; // Non-greedy match on filler
//		String re2 = "(\\{.*?\\})"; // Curly Braces 1
//		Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
//		Matcher m = p.matcher(original);
//		StringBuffer sb = new StringBuffer();
//		while (m.find()) {
//			m.appendReplacement(sb, "");
//		}
//		m.appendTail(sb);
//		System.out.println("Final String:" + sb.toString());
		
		
		String original = "Puntos Farmanorte le informa que ha redimido ${puntos} puntos en la sucursal ${sucursal}.";
		 System.out.println("Original String: " + original);
		 String temp = reemplazaMensaje(original, "puntos", "8.000");
		 System.out.println("Modificadno 1vr: " +  temp);
		 temp = reemplazaMensaje(temp, "sucursal", "Farmanorte");
		 System.out.println("Modificadno 2vr: " + temp );
	    
	    
	}//end main
	
	
	
	private static String reemplazaMensaje(String mensaje, String variable, String valor ){
		Pattern p = Pattern.compile("(\\$\\{.*"+variable+"?\\})");
		 Matcher m = p.matcher(mensaje);		 
		 StringBuffer sb = new StringBuffer();
		 while (m.find()) {
		     m.appendReplacement(sb, valor);
		 }
		 m.appendTail(sb);
		 return sb.toString();
	}
	

}
