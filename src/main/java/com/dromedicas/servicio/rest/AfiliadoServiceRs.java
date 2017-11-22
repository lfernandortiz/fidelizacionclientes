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
			// crearAfiliado persiste el nuevo objeto Afiliado guarda los puntos 
			// iniciales por inscripcion y envia el emial de notificacion
			this.afiliadoService.crearAfiliado(afiliado);
			
			
			System.out.println(Response.Status.OK.getStatusCode());
			responseObject.setCode(Status.OK.getStatusCode());
			responseObject.setStatus(Status.OK.getReasonPhrase());
			responseObject.setMessage("Afiliado creado correctamente");
			return Response.status(Status.OK).entity(responseObject).build();

		} else {
			System.out.println(Response.Status.BAD_REQUEST.getStatusCode());
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			responseObject.setMessage("El documento ya se encuentra registrado");
			return Response.status(Status.BAD_REQUEST).entity(responseObject).build();
		}
		
		
	}
	
	
	
	//validar correo de afiliado
	
	
	
	//obtener datos del formulario basico de afiliado
	
	
	
	//procesar segundo formulario de afiliacion
	
	

}
