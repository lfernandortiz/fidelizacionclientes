package com.dromedicas.servicio.rest;


import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.util.SimpleKeyGenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/apiwebafiliado")
@Stateless
public class UserEndpoint {
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private SimpleKeyGenerator keyGenerator;
	
	private UriInfo uriInfo;	
	
	@Path("/login")
	@POST    
	@Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@QueryParam("user") String login,
    								 @QueryParam("password") String password) {
		
		System.out.println("ENTRE AL METODO");
		
        try {
            System.out.println("#### login/password : " + login + "/" + password);
            
            Thread.sleep(3000);
            // Autentica el usuario usando las credenciales proporcionadas
//            String uuid = authenticate(login, password);
            //cambio por nueva version ( 27/06/2018 )
            String uuid = authenticateWithDocument(login);
            
            // Emite un token para el usuario 
            String token = issueToken(uuid);            

           
            // Retorna el tocken en la respuesta
            
            return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*")
            								 .header("Access-Control-Expose-Headers","Session-Token")
            								 .header("Access-Control-Expose-Headers", "ETag")
            								 .header("Access-Control-Expose-Headers", "AUTHORIZATION")
            								 .header("Access-Control-Allow-Credentials", "true")
            								 .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")	
            								 .header(AUTHORIZATION, "Bearer " + token).build();
            
        } catch (Exception e) {
        	Map<String, String> rep = new HashMap<String, String>();
        	rep.put("code", "404");
        	rep.put("message", "Usuario no resgistrado.");
            return Response.status(Status.OK).entity(rep).header("Access-Control-Allow-Origin", "*").build();
        }
    }
	
	
	/**
	 * Retorna el token o codigo UUID de una afiliado, recibiendo como parametro
	 * su documento de identidad y la clave web
	 * @param login
	 * @param password
	 * @return UUID
	 * @throws Exception
	 */
	private String authenticate(String login, String password) throws Exception {
		System.out.println("---- login/password : " + login + "/" + password);
		
        Afiliado afTemp = this.afiliadoService.obtenerAfiliadoByCredenciales(login, password);
        
        if (afTemp == null)
            throw new SecurityException("Invalid user/password");
        
        return afTemp.getKeycode();
        
    }
	
	
	/**
	 * Retorna el token o codigo UUID de una afiliado, recibiendo como parametro
	 * su documento de identidad
	 * @param login
	 * @param password
	 * @return UUID
	 * @throws Exception
	 */
	private String authenticateWithDocument(String login) throws Exception {
		System.out.println("---- login : " + login) ;
		
        Afiliado afTemp = this.afiliadoService.obtenerAfiliadoByDocumento(login);
        
        if (afTemp == null)
            throw new SecurityException("Invalid user/password");
        
        return afTemp.getKeycode();
        
    }
	
	

	
    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        Date date =new Date();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                //.setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(date)                
                .setExpiration(addMinutesToDate(172800000, date))                
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.println("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;
    }

    
    private static Date addMinutesToDate(int minutes, Date beforeTime){
    	
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

}
