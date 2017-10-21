package com.dromedicas.util;

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
		}
		
		return textoFinal.toString().trim();
	}

}
