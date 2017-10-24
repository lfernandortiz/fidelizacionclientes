package com.dromedicas.util;

import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="regex")
@SessionScoped
@Stateless
public class ExpresionesRegulares {
	/**
	 * Pasa la primera letra a mayuscula si recibe un valor booleano
	 * <code>true</code> como segundo argumento; si el valor es
	 * <code>false</code> solo elimina los espacios en blanco y deja la cadena
	 * en minuscula
	 */
	public String nombrePropio(String texto, boolean flag) {
		StringTokenizer tokens = new StringTokenizer(texto.toLowerCase());
		StringBuilder textoFinal = new StringBuilder();

		while (tokens.hasMoreTokens()) {
			String palabra = tokens.nextToken();
			if (flag)
				textoFinal.append(" ").append(nombrePropioAux(palabra));
			else
				textoFinal.append(" ").append(palabra);
		}
		return textoFinal.toString().trim();
	}

	/**
	 * El metodo <tt>aMayusculaPrimera</tt> pasa a mayuscula la primera letra de una 
	 * cadena. Este metodo puede ser usado como axuliar de otros que esten iterando un 
	 * objeto por ejemplo <tt>StringTokenizer</tt>.
	 * @param palabra
	 * @return
	 */
	public  String nombrePropioAux(String palabra) {
		StringBuilder palabraFinal = new StringBuilder();
		Character letra = palabra.charAt(0);

		palabraFinal.append(Character.toUpperCase(letra));
		palabraFinal.append(palabra.substring(1, palabra.length()));
		return palabraFinal.toString();
	}

	/**
	 * El metodo <tt>getDigitsOnly </tt>retorna los digitos sin espeacios en una
	 * cadena, puede ser usado para extraer los digitos en numeros telfonicos
	 * cuando estos registrados se hacen con espacios.
	 * 
	 * @param String
	 * @return String con solo digitos
	 */
	public  String getDigitsOnly(String s) {
		StringBuffer digitsOnly = new StringBuffer();
		char c;

		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (Character.isDigit(c)) {
				digitsOnly.append(c);
			}
		}
		return digitsOnly.toString(); 
	}// fin del metodo getDigitsOnly - obtener solo digitos

		
	/**
	 * El metodo <tt>validateEmail </tt> retorna true si la direccion de correo 
	 * electronico es una direccion valida. Este metodo usa expresiones regulares
	 * (regex) para hacer el analisis.
	 * 
	 * http://docs.oracle.com/javase/tutorial/essential/regex/intro.html
	 * 
	 * @param mail
	 * @return
	 */
	public  boolean validateEmail(String mail){
		
		if (!mail
				.matches("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
			return false;
		} else {
			return true;
		}		
		
	}
	
	/**
	 * Abrevia la segunda palabara de un nombre, si la longitud de este
	 * es mayor o igual a tres palabras
	 * @param texto
	 * @return
	 */
	public String puntoSegundoNombre(String texto){
		StringTokenizer tokens = new StringTokenizer(texto.toLowerCase());
		int numTokens = tokens.countTokens();
		StringBuilder textoFinal = new StringBuilder();
		
		if(numTokens >= 3){
			int flag = 0;
			while (tokens.hasMoreTokens()) {
				String palabra = tokens.nextToken();
				flag++;
				if(flag == 2){
					StringBuilder segPalabra = new StringBuilder();
					Character letra = palabra.charAt(0);

					segPalabra.append(Character.toUpperCase(letra));
					segPalabra.append(".");
					textoFinal.append(" ").append(segPalabra);
				}else{
					textoFinal.append(" ").append(nombrePropioAux(palabra));
				}					
			}
		}else{
			while (tokens.hasMoreTokens()) {
				String palabra = tokens.nextToken();
				textoFinal.append(" ").append(nombrePropioAux(palabra));
			}		
		}		
		return textoFinal.toString().trim();
	}
	
	
	/**
	 * Reemplaza los acentos por vocales simples, y la letra
	 * N tildel po "n" Simple
	 */	
	public String removerAcentosNtildes(String src) {
		System.out.println("Cadena Recibida en RGX: " + src);
		src = src.toLowerCase();
		String result = Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		return result.toUpperCase().replace("Ñ", "N");
	}
	
	
	public int getAge(Date dateOfBirth) {
	    Calendar today = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();
	    birthDate.setTime(dateOfBirth);
	    if (birthDate.after(today)) {
	        throw new IllegalArgumentException("You don't exist yet");
	    }
	    int todayYear = today.get(Calendar.YEAR);
	    int birthDateYear = birthDate.get(Calendar.YEAR);
	    int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
	    int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
	    int todayMonth = today.get(Calendar.MONTH);
	    int birthDateMonth = birthDate.get(Calendar.MONTH);
	    int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
	    int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
	    int age = todayYear - birthDateYear;

	    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
	    if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)){
	        age--;
	    
	    // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
	    } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)){
	        age--;
	    }
	    return age;
	}

}
