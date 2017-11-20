package com.dromedicas.servicio.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoDocumentoService;
import com.dromedicas.service.UsuarioWebService;


/**
 * 
 * @author SOFTDromedicas 
 *
 */
@Path("/afiliado")
@Stateless
public class AfiliadoServiceRs{
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private TipoDocumentoService tipodocService;
	
	@EJB
	private SucursalService sucursalService;
	
	@EJB
	private UsuarioWebService usuarioService;
	
	//crear afiliado desde formulario web y la app
	@Path("/crearafiliado")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearAfiliadoWeb(@QueryParam("documento") String documento,
									 @QueryParam("nombres") String nombres,
									 @QueryParam("apellidos") String apellidos,
									 @QueryParam("tipodocumento") int tipodocumento,
									 @QueryParam("sexo") String sexo,
									 @QueryParam("direccion") String direccion,
									 @QueryParam("fechanacimiento") String fechanacimiento,
									 @QueryParam("telefonofijo") String telefonofijo,
									 @QueryParam("celular") String celular,
									 @QueryParam("ciudad") String ciudad,
									 @QueryParam("email") String email,
									 @QueryParam("barrio") String barrio,
									 @QueryParam("usuario") String usuario){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date fecNacimiento = new Date();
		try {
			fecNacimiento = sdf.parse(fechanacimiento);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Documento recibido: " + documento);
		System.out.println("Nombres recibidos: " + nombres);
		System.out.println("apellidos recibido: " + apellidos);
		System.out.println("Documento recibido: " + tipodocumento);
		System.out.println("Documento sexo: " + sexo);
		System.out.println("Documento direccion: " + direccion);
		System.out.println("Documento fechanacimiento: " + sdf.format(fecNacimiento) );	
		System.out.println("barrio: " + barrio);
		System.out.println("usuario: " + usuario);
		
		
		
		ResponsePuntos responseObject = new ResponsePuntos();
		//valida que el afiliado no exista
		
		Afiliado afTemp = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
		System.out.println("----------"  +  afTemp.getNombres());
		
		
			//crea las entidades respectivas, las validaciones se hacen en frontend
		
		
		return null;
	}
	
	
	
	//validar correo de afiliado
	
	
	
	//obtener datos del formulario basico de afiliado
	
	
	
	//procesar segundo formulario de afiliacion
	
	

}
