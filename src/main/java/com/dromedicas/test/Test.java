package com.dromedicas.test;

import java.util.ArrayList;
import java.util.Collection;

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
			// Prints:
			
			
			String str = "An 😀awesome 😃string with a few 😉emojis!";
			Collection<Emoji> collection = new ArrayList<Emoji>();
			collection.add(EmojiManager.getForAlias("wink")); // This is 😉

			System.out.println(EmojiParser.removeAllEmojis(str));
			System.out.println(EmojiParser.removeAllEmojisExcept(str, collection));
			System.out.println(EmojiParser.removeEmojis(str, collection));
			
			String strs= "An :pill:  awesome :smiley:string &#128516;with a few :wink:emojis   :syringe:!";
			String result = EmojiParser.parseToUnicode(strs);
			System.out.println(result);
	        
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
