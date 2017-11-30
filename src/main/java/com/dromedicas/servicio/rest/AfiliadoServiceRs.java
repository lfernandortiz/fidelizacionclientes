package com.dromedicas.servicio.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.domain.Usuarioweb;
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
		
		ResponsePuntos responseObject = new ResponsePuntos();
		//valida que el afiliado no exista
		
		Afiliado afTemp = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
		if (afTemp == null) {

			// crea las entidades respectivas, las validaciones se hacen en
			// frontend
			Afiliado afiliado = new Afiliado();
			afiliado.setNombres(nombres);
			afiliado.setApellidos(apellidos);
			
			Tipodocumento tdocumento = tipodocService.obtenerTipodocumentoByIdString(tipodocumento);
			afiliado.setTipodocumentoBean(tdocumento);
			afiliado.setDocumento(documento);
			afiliado.setNacionalidad("Colombia");
			afiliado.setSexo(sexo);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");			
			try {
				afiliado.setFechanacimiento(sdf.parse(fechanacimiento));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}			
			afiliado.setStreet(direccion);
			afiliado.setStreetdos(barrio);
			afiliado.setCiudad(ciudad);
			afiliado.setDepartamento("");
			
			Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno("00");
			afiliado.setSucursal(sucursal);
			afiliado.setTelefonofijo(telefonofijo);
			afiliado.setCelular(celular);
			afiliado.setEmail(email);
			afiliado.setMomento(new Date());
			Usuarioweb user =  this.usuarioService.obtenerUsuarioPorUsuario(usuario);
			afiliado.setUsuariowebBean(user);
			
			UUID uniqueKey = UUID.randomUUID(); // codigo usado para validaciones web
			afiliado.setKeycode(uniqueKey.toString().replace("-", ""));
			// crearAfiliado persiste el nuevo objeto Afiliado guarda los puntos 
			// iniciales por inscripcion y envia el emial de notificacion
			this.afiliadoService.crearAfiliado(afiliado);
			
			
			System.out.println(Response.Status.OK.getStatusCode());
			responseObject.setCode(Status.OK.getStatusCode());
			responseObject.setStatus(Status.OK.getReasonPhrase());
			responseObject.setMessage("Afiliado creado correctamente.");
			return 
					Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();

		} else {
			System.out.println(Response.Status.BAD_REQUEST.getStatusCode());
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			responseObject.setMessage("El documento ya se encuentra registrado.");
			return 
					Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
		}
		
		
	}
	
	
	
	//validar correo de afiliado
	@Path("/validacorreo")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response validarCorreoAfiliado(@QueryParam("id") String id){
		Afiliado afiliado = this.afiliadoService.obtenerAfiliadoUUID(id);
		
		//se marca como validado el correo 
		afiliado.setEmailvalidado((byte)1);

		//se actualiza el cambio 
		this.afiliadoService.actualizarAfiliado(afiliado);
		
		
		ResponsePuntos responseObject = new ResponsePuntos();
		System.out.println(Response.Status.OK.getStatusCode());
		responseObject.setCode(Status.OK.getStatusCode());
		responseObject.setAfiliado(afiliado);
		responseObject.setStatus(Status.OK.getReasonPhrase());
		responseObject.setMessage("Afiliado encontrado correctamente.");
		return 
				Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	
	//obtener datos del formulario basico de afiliado
	@Path("/obtenerafiliado")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response datosAfiliado(@QueryParam("id") String id){
		Afiliado afiliado = this.afiliadoService.obtenerAfiliadoUUID(id);
		
		ResponsePuntos responseObject = new ResponsePuntos();
		System.out.println(Response.Status.OK.getStatusCode());
		responseObject.setCode(Status.OK.getStatusCode());
		responseObject.setAfiliado(afiliado);
		responseObject.setStatus(Status.OK.getReasonPhrase());
		responseObject.setMessage("Afiliado encontrado correctamente.");
		
		System.out.println("Nombre: " + afiliado.getNombres() +" "+afiliado.getApellidos() );
		
		
		return 
				Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	//procesar segundo formulario de afiliacion
	
	
	
	//login perfil del usuario
	
	
	
	//actualizacion de datos basicos de afiliado
	
	

}
