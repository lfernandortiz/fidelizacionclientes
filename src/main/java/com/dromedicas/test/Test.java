package com.dromedicas.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

public class Test {
	
	static EntityManager em = null;
	static EntityTransaction tx = null;
	static EntityManagerFactory emf = null;
	
	
	public static void main(String[] args) {

		try {
//			String str = "An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!";
//			String resultDecimal = EmojiParser.parseToHtmlDecimal(str);
//			System.out.println(resultDecimal);
			// Prints:+
			
			
			
			try {
				String subjectEmojiRaw = ":large_blue_circle: Confirmacion de suscripcion :memo:";
				String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);	
				System.out.println("----------->" + subjectEmoji);
				
				subjectEmoji = "Mensaje de pruba";
				// Thread.sleep(5500);
				String query = String.format("cliente=%s&api=%s&numero=%s&sms=%s", URLEncoder.encode("10010333", "UTF-8"),
						URLEncoder.encode("4z1MlW6lsQHKiJ6x909E7zS8Rp5PRF", "UTF-8"),
						URLEncoder.encode( "3102097474", "UTF-8"), 
						URLEncoder.encode( subjectEmoji, "UTF-8"));

				URL url = new URL("https://ws.hablame.co/sms_http.php" + "?" + query);
				System.out.println(url);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				Map<String, List<String>> header = conn.getHeaderFields();
				int responseCode = conn.getResponseCode();
				System.out.println("Headers : " + header);
				System.out.println("Respuesta : " + responseCode);
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
			
			
			
			
		
//			Key k = MacProvider.generateKey();
//			String k = "CSK395";
//			String jwt = Jwts.builder()
//			 .setSubject("PuntosFarmanorte")
//			 .signWith(SignatureAlgorithm.HS256, k)
//			 .setIssuedAt(new Date(t1))
//			 .setExpiration(new Date(t1 + 900000))
//			 .claim("email", "cliente@yahoo.com")
//			 .compact();
//		
//			System.out.println("K: " + k.toString());
			
//			JsonObject json ;
//			json = Json.createObjectBuilder().add("JWT", compactJws).build();
//			
			
			
			
//			Logger log = Logger.getLogger("TestPuntosFarmanorte");
//
//			log.debug("Preparando contexto de persistencia");
//			
//			emf = Persistence.createEntityManagerFactory("PuntosFPU");
//			
//			em = emf.createEntityManager();
//
//			log.debug("Iniciando test Persona Entity con JPA");
//			@SuppressWarnings("unused")
//			EntityTransaction tx = em.getTransaction();
//
////			tx.begin();
//
////			Empresa emp = new Empresa();
////			emp.setNit("900265730-0");
////			emp.setNombreEmpresa("Dromedicas del Oriente SAS");
////			emp.setDireccion("Avenida 7A # 0BN - 36 Sevilla");
////			emp.setTelefono("5781240");
////			emp.setEmailNotificaciones("info@dromedicas.com.co");
//			
//			
//			@SuppressWarnings("unchecked")
//			List<Empresa> empresaList = em.createNamedQuery("Empresa.findAll").getResultList();
//			
//			for(Empresa e: empresaList )
//				System.out.println(e);
//
////			log.debug("Objeto a persistir: " + emp);
////
////			em.persist(emp);
////			
//	//		tx.commit();
////
////			log.debug("Objeto persistido: " + emp);
//
//			log.debug("Fin test Persona Entity con JPA");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
