package com.dromedicas.servicio.rest;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Afiliadopatologia;
import com.dromedicas.domain.AfiliadopatologiaPK;
import com.dromedicas.domain.Afiliadopatologianucleo;
import com.dromedicas.domain.AfiliadopatologianucloePK;
import com.dromedicas.domain.BalancePuntos;
import com.dromedicas.domain.Estudioafiliado;
import com.dromedicas.domain.Nucleofamilia;
import com.dromedicas.domain.NucleofamiliaPK;
import com.dromedicas.domain.Ocupacion;
import com.dromedicas.domain.Patologia;
import com.dromedicas.domain.Referido;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.domain.Tipomiembro;
import com.dromedicas.domain.Usuarioweb;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.service.AfiliadoPatologiaNucleoService;
import com.dromedicas.service.AfiliadoPatologiaService;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.EstudioAfiliadoService;
import com.dromedicas.service.NucleoFamiliaService;
import com.dromedicas.service.OcupacionService;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.PatologiaService;
import com.dromedicas.service.ReferidoService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoDocumentoService;
import com.dromedicas.service.TipoMiembroService;
import com.dromedicas.service.UsuarioWebService;
import com.dromedicas.util.ExpresionesRegulares;
import com.dromedicas.util.SimpleKeyGenerator;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import io.jsonwebtoken.Jwts;

/**
 * 
 * @author SOFTDromedicas 
 *
 */
@Path("/afiliado")
@Stateless
public class AfiliadoServiceRs implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private TipoDocumentoService tipodocService;
	
	@EJB
	private SucursalService sucursalService;
	
	@EJB
	private UsuarioWebService usuarioService;
	
	@EJB
	private OcupacionService ocupacionService;
	
	@EJB
	private EstudioAfiliadoService estudioService;
	
	@EJB
	private PatologiaService patologiaService;
		
	@EJB
	private TipoMiembroService tipoMiembroService;
		
	@EJB	
	private NucleoFamiliaService nucleoFamiliaService;
		
	@EJB
	private AfiliadoPatologiaService afPatologiaService;
	
	@EJB
	private AfiliadoPatologiaNucleoService afpatoService;
	
	@EJB
	private ReferidoService referidoService;
	
	@EJB
	private ExpresionesRegulares regex;
	
	@EJB
	private OperacionPuntosService calculoService;
	
	@EJB
	private EnviarEmailAlertas emailAlerta;
	
	@Context UriInfo uriInfo;
		
	
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
	@Path("/actualizarafiliadoweb")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizarDatosAfiliadoWeb(@Context UriInfo ui) {
		
		MultivaluedMap<String, String> map = ui.getQueryParameters();
		
		ResponsePuntos responseObject = new ResponsePuntos();
		
		try {
			// se obtienen todos los valores desde la peticion
			String documento = map.getFirst("documento");
			String nombres = map.getFirst("nombres");
			String apellidos = map.getFirst("apellidos");
			int tipodocumento = Integer.parseInt(map.getFirst("tipodocumento"));
			String sexo = map.getFirst("sexo");
			String direccion = map.getFirst("direccion");
			String fechanacimiento = map.getFirst("fechanacimiento");
			String telefonofijo = map.getFirst("telefonofijo");
			String celular = map.getFirst("celular");
			String ciudad = map.getFirst("ciudad");
			String email = map.getFirst("email");
			String barrio = map.getFirst("barrio");
			String claveweb = map.getFirst("claveweb");
			String estudios = map.getFirst("estudios");
			String ocupacion = map.getFirst("ocupacion");
			int cantidadmiembro = Integer.parseInt(map.getFirst("cantidadmiembro"));
			int tipoMiembro[] = new int[cantidadmiembro];
			int valIni[] = new int[cantidadmiembro];
			int valFin[] = new int[cantidadmiembro];

			// Patologias
			List<Integer> patologiasAfiliado = new ArrayList<Integer>();
			List<Integer> patologias = new ArrayList<Integer>();

			for (int i = 0; i < 25; i++) {
				String pTemp = map.getFirst("p" + (i + 1));
				if (pTemp != null) {
					patologiasAfiliado.add(Integer.parseInt(pTemp));
				}
				String pMiembreTemp = map.getFirst("pm" + (i + 1));
				if (pMiembreTemp != null) {
					patologias.add(Integer.parseInt(pMiembreTemp));
				}
			}

			// obtiene los valores variables de miembros de familia

			System.out.println("Cantidad Miembro........: " + cantidadmiembro);
			for (int i = 0; i < cantidadmiembro; i++) {
				String tempTipo = map.getFirst("tipomiembroval" + (i + 1));

				if (tempTipo != null) {
					tipoMiembro[i] = Integer.parseInt(tempTipo);
					valIni[i] = Integer.parseInt(map.getFirst("valini" + (i + 1)));
					valFin[i] = Integer.parseInt(map.getFirst("valfin" + (i + 1)));

					System.out.println("ValIni " + (i + 1) + ": " + valIni[i]);
				}
			}

			String hijosmenoresde4 = map.getFirst("hijosmenoresde4");
			String hijosentre4y12 = map.getFirst("hijosentre4y12");
			String hijosentre13y18 = map.getFirst("hijosentre13y18");
			String hijosmayores = map.getFirst("hijosmayores");
			String cantreferido = map.getFirst("cantreferido");
			List<String> referidosList = new ArrayList<String>();
			

			if (cantreferido != null) {
				for (int i = 0; i < Integer.parseInt(cantreferido); i++) {
					String refTemp = map.getFirst("referido" + (i + 1));
					Referido refPersist = this.referidoService.obtenerReferidoPorEmail(refTemp);
					// Evita duplicidades en referidos
					if (!"".equals(refTemp) && !" ".equals(refTemp) && refTemp != null && refPersist == null) {
						if( regex.validateEmail(refTemp)){//usa validacion por medio de Regex de la dir de email
							referidosList.add(refTemp);
						}					
					}
				}
			}

			// --> Se crean las instancias respectivas
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
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
			afiliado.setClaveweb(claveweb);
			// informacion adicional formulario 2
			// Ocupacion		
			
			if (ocupacion != null && !ocupacion.toString().equals("[object HTMLDivElement]")) {
				Ocupacion ocupac = this.ocupacionService.obtenerOcupacionById(Integer.parseInt(ocupacion));
				afiliado.setOcupacionBean(ocupac);
			}
			
			// Nivel de estudios
			if (estudios != null && !estudios.toString().equals("[object HTMLDivElement]")) {
				Estudioafiliado estudiosnivel = this.estudioService.obtenerEstudioafiliadoById(Integer.parseInt(estudios));
				afiliado.setEstudioafiliado(estudiosnivel);
			}
			// Patologias afiliado
			if (!patologiasAfiliado.isEmpty()) {
				for (int e : patologiasAfiliado) {
					Patologia patologia = this.patologiaService.obtenerPatologiaById(e);
					AfiliadopatologiaPK pk = new AfiliadopatologiaPK();
					pk.setIdafiliado(afiliado.getIdafiliado());
					pk.setIdpatologia(e);
					Afiliadopatologia afPatologia = new Afiliadopatologia();
					afPatologia.setId(pk);
					afPatologia.setFecha(new Date());
					afPatologia.setAfiliado(afiliado);
					afPatologia.setPatologia(patologia);

					this.afPatologiaService.updateAfiliadopatologia(afPatologia);
				}
			}
			// nucleo de familia
			if (cantidadmiembro != 0) {
				for (int i = 0; i < tipoMiembro.length; i++) {
					// tipo miembro
					Tipomiembro tipoM = tipoMiembroService.obtenerTipomiembroById(tipoMiembro[i]);
					System.out.println("tipoM: " + tipoM.getDescripcion());

					// nucleofamilia
					NucleofamiliaPK pkNucleo = new NucleofamiliaPK();
					pkNucleo.setIdafiliado(afiliado.getIdafiliado());
					pkNucleo.setIdtipomiembro(tipoM.getIdtipomiembro());

					Nucleofamilia nucleo = new Nucleofamilia();
					nucleo.setId(pkNucleo);
					nucleo.setRangoinicio(valIni[i]);
					nucleo.setRangofin(valFin[i]);

					this.nucleoFamiliaService.updateNucleofamilia(nucleo);
				}
			}
			// hijos
			System.out.println("hijos menore de 4: " + hijosmenoresde4);

			if (hijosmenoresde4 != null) {
				afiliado.setHijosmenoresde4((byte) 1);
			}
			if (hijosentre4y12 != null) {
				afiliado.setHijosentre4y12((byte) 1);
			}
			if (hijosentre13y18 != null) {
				afiliado.setHijosentre13y18((byte) 1);
			}
			if (hijosmayores != null) {
				afiliado.setHijosmayores((byte) 1);
			}
			// patologia nucleo
			if (!patologias.isEmpty()) {
				for (int e : patologias) {
					Patologia patologia = this.patologiaService.obtenerPatologiaById(e);

					System.out.println("Patologia nucleo: " + patologia.getDrescripcion());

					AfiliadopatologianucloePK pk = new AfiliadopatologianucloePK();
					pk.setIdafiliado(afiliado.getIdafiliado());
					pk.setIdpatologia(e);
					Afiliadopatologianucleo afPatologia = new Afiliadopatologianucleo();
					afPatologia.setId(pk);
					afPatologia.setAfiliado(afiliado);
					afPatologia.setPatologia(patologia);
					 
					this.afpatoService.updateAfiliadopatologianucleo(afPatologia);
				}
			}
				//referido
			if ( !referidosList.isEmpty() ) {
				for (String dir : referidosList) {
					//Crea una nueva instancia de Referido
					Referido referido = new Referido();
					referido.setEmailreferido(dir);
					referido.setAfiliado(afiliado);
					// Persiste el objeto Referido en su tabla
					this.referidoService.updateReferido(referido);
				}
			}
			
			// se actualizan los valores
			this.afiliadoService.actualizarAfiliado(afiliado);

			//Envia los email de referidos
			if(!referidosList.isEmpty()){
				this.emailAlerta.emailNotificacionReferido(referidosList);
			}
			
			//Envia email de confirmacion de suscripcion
			if( !afiliado.getEmail().equals("") && afiliado.getEmail() != null){
				this.emailAlerta.emailConfirmacionFinalSuscripcion(afiliado);
			}
			
			
			System.out.println(Response.Status.OK.getStatusCode());
			responseObject.setCode(Status.OK.getStatusCode());
			responseObject.setAfiliado(afiliado);
			responseObject.setStatus(Status.OK.getReasonPhrase());
			responseObject.setMessage("Afiliado encontrado correctamente.");
			
			System.out.println("Nombre: " + afiliado.getNombres() +" "+afiliado.getApellidos() );
			
			return 
					Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Response.Status.BAD_REQUEST.getStatusCode());
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			responseObject.setMessage("Error en la actualizacion de los datos.");
			return 
					Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
		}		
	}
	
	
	/**
	 * End Point para la actualizacion de los datos basicos del afiliado
	 * desde el perfil web y la App.
	 * Retorna un objeto ResponsePuntos wrap con la informacion del afiliado
	 * puntos acumulados y url de la foto del perfil.
	 * @param ui
	 * @return Response
	 */
	@Path("/updateprofilepartner")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizaPerfil(@Context UriInfo ui) {

		MultivaluedMap<String, String> map = ui.getQueryParameters();

		ResponsePuntos responseObject = new ResponsePuntos();

		try {
			Thread.sleep(2000);
			
			String documento = map.getFirst("documento");
			String nombres = map.getFirst("nombres");
			String apellidos = map.getFirst("apellidos");
			int tipodocumento = Integer.parseInt(map.getFirst("tipodocumento"));
			String sexo = map.getFirst("sexo");
			String direccion = map.getFirst("direccion");
			String fechanacimiento = map.getFirst("fechanacimiento");
			String telefonofijo = map.getFirst("telefonofijo");
			String celular = map.getFirst("celular");
			String ciudad = map.getFirst("ciudad");
			String email = map.getFirst("email");
			String barrio = map.getFirst("barrio");
			String token = this.getToken(map.getFirst("token"));

			// --> Se crean las instancias respectivas
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoUUID(token);
			afiliado.setNombres(nombres);
			afiliado.setApellidos(apellidos);
			
			Tipodocumento tdocumento = tipodocService.obtenerTipodocumentoByIdString(tipodocumento);
			afiliado.setTipodocumentoBean(tdocumento);
			afiliado.setNacionalidad("Colombia");
			afiliado.setSexo(sexo);

			afiliado.setStreet(direccion);
			afiliado.setStreetdos(barrio);
			afiliado.setCiudad(ciudad);
			afiliado.setDepartamento("");

			Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno("00");
			afiliado.setSucursal(sucursal);
			afiliado.setTelefonofijo(telefonofijo);
			afiliado.setCelular(celular);
			afiliado.setEmail(email);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				
				System.out.println("fecha recibida: " + fechanacimiento);
				
				Date f = sdf.parse(fechanacimiento);
				
				
				afiliado.setFechanacimiento(f);
				
				System.out.println("Fecha de Nacimiento Comprobacion updateprofilepartner: " + f);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			//valida que no exista otro afiliado con el documento que se intenta actualizar
			
			boolean validaDocumento = afiliado.getIdafiliado() != this.afiliadoService.obtenerAfiliadoByDocumento(documento).getIdafiliado();
			
			if( validaDocumento ){
				System.out.println(Response.Status.BAD_REQUEST.getStatusCode());
				responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
				responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
				responseObject.setMessage("Error en la actualizacion de los datos, Este numero de documento ya esta asignado a otro afiliado.");
				return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
			}
			
			
			afiliado.setDocumento(documento);
			
			// se actualizan los valores
			this.afiliadoService.actualizarAfiliado(afiliado);
			
			Afiliado tempA=  this.afiliadoService.obtenerAfiliadoById(afiliado);
			
			BalancePuntos balance = calculoService.consultaPuntos(tempA);
			
			System.out.println(Response.Status.OK.getStatusCode());
			responseObject.setCode(Status.OK.getStatusCode());
			responseObject.setBalance(balance);
			responseObject.setAfiliado(tempA);
			if(afiliado.getFotoperfil() != null){
				responseObject.setUrlFotoAfiliado(uriInfo.getBaseUri()+ "afiliado"+ "/getfotoperfil/" + afiliado.getKeycode());
			}
			responseObject.setStatus(Status.OK.getReasonPhrase());
			responseObject.setMessage("Afiliado encontrado correctamente.");
			
			System.out.println("Nombre: " + afiliado.getNombres() + " " + afiliado.getApellidos());

			return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Response.Status.BAD_REQUEST.getStatusCode());
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			responseObject.setMessage("Error en la actualizacion de los datos.");
			return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	
	
	/**
	 * End Point para actualizar la foto del perfil del afiliado
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @param token
	 * @return Response 
	 */
	@POST
	@Path("/uploadfoto")  //Your Path or URL to call this service
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
							   @FormDataParam("file") FormDataContentDisposition fileDetail,
							   @FormDataParam("token") String token) {
		
		try {
		    //Se obtiene el flujo de bits de la imagen
			//Se usa la api IOUtils para obtener el array de bits del objeto InputStream
		    byte[] foto = IOUtils.toByteArray( uploadedInputStream );
		    
		    //obtenemos el JWT (json web token) y extraemos el codigo UUID del afiliado
		    String uuid = getToken(token);
		    
		    //usando el EJB de servicio de afiliado obtenemos el afiliado por medio del UUID
		    Afiliado afTemp = this.afiliadoService.obtenerAfiliadoUUID( uuid );
		    //Al objeto afiliado establecemos la imagen (blob)
		    afTemp.setFotoperfil(foto);
		    
		    //Por medio del EJB de servicio para afiliado actualizamos los cambiod de la foto
		    this.afiliadoService.actualizarAfiliado(afTemp);
		    
		    //Crea Wrap o envoltorio para la respuesta del JSON
		    ResponsePuntos responseObject = new ResponsePuntos();
		    responseObject.setCode(Status.OK.getStatusCode());
		    responseObject.setUrlFotoAfiliado(uriInfo.getBaseUri()+ "afiliado"+ "/getfotoperfil/" + afTemp.getKeycode());
			responseObject.setAfiliado(afTemp);
			responseObject.setStatus(Status.OK.getReasonPhrase());
			responseObject.setMessage("Afiliado encontrado correctamente.");
		   
		    return
		    		Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
		    
		} catch (Exception e) {
			
			return null;
		}
	}
	
	
	@GET
	@Path("/getfotoperfil/{token}")
	@Produces("image/jpg")
	public Response getFile(@PathParam("token") String token) {
		
		Afiliado afTemp = this.afiliadoService.obtenerAfiliadoUUID(token);
		System.out.println("Afiliado foto: " + afTemp.getNombres());
		
		System.out.println("--------------" + afTemp.getFotoperfil());
		
		if( afTemp.getFotoperfil() != null ){
			return  
					Response.status(Status.OK).entity(new ByteArrayInputStream(afTemp.getFotoperfil())).
											header("Access-Control-Allow-Origin", "*").build();
		}else{
			return null;
		}
	}
	
	/**
	 * End point para la recuperacion de la clave de afiliado
	 * por medio del correo electronico.
	 * @param email
	 * @return
	 */
	@GET
	@Path("/recuperaclave/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperaClave(@PathParam("email") String email) {
		
		Afiliado afTemp = null;
		
		try {
			Thread.sleep(1000); // esto es por visaje ;-)
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {			
			afTemp = this.afiliadoService.obtenerAfiliadoByEmail(email);			
		}catch(Exception e) {
			e.printStackTrace();
			ResponsePuntos responseObject = new ResponsePuntos();
		    System.out.println(Response.Status.BAD_REQUEST.getStatusCode());
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			
			String mensaje = e.getCause().getCause().getMessage();//Cool
			responseObject.setMessage(mensaje);		   
		    return
		    		Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
		}
		System.out.println("Afiliado encontrado por email :-)");
		
		if( afTemp != null ){			
			//se envia un correo de recuperacion de clave
			
			this.emailAlerta.emailRecuparacionClave(afTemp);
			
			ResponsePuntos responseObject = new ResponsePuntos();
		    responseObject.setCode(Status.OK.getStatusCode());
			responseObject.setAfiliado(afTemp);
			responseObject.setStatus(Status.OK.getReasonPhrase());
			responseObject.setMessage("Correo de recuperacion encontrado satisfactoriamente.");
		   
		    return
		    		Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
		}else{
			return null;
		}
	}
		
	
	@GET
	@Path("/reestablecerclave")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reestablecerClave(@QueryParam("pswd") String pswd, 
									  @QueryParam("token") String token) {
		
		try {
			Thread.sleep(1000); // esto es por visaje ;-)
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Afiliado afTemp = this.afiliadoService.obtenerAfiliadoUUID(token);
		afTemp.setClaveweb(pswd);
		this.afiliadoService.actualizarAfiliado(afTemp);

		this.emailAlerta.emailConfirmacionClave(afTemp);

		ResponsePuntos responseObject = new ResponsePuntos();
		responseObject.setCode(Status.OK.getStatusCode());
		responseObject.setStatus(Status.OK.getReasonPhrase());
		responseObject.setMessage("Se ha reestablecido correctamente la nueva contraseña.");

		return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();

	}
	
	

	private String getToken(String jWT){
		String uuidAfiliado = null;
		String justTheToken = jWT.substring("Bearer".length()).trim();
		Key key = new SimpleKeyGenerator().generateKey();			
		uuidAfiliado = Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getBody().getSubject();
		return uuidAfiliado;
		
	}
	
	
	

}
