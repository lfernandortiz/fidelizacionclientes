package com.dromedicas.servicio.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Afiliadopatologia;
import com.dromedicas.domain.AfiliadopatologiaPK;
import com.dromedicas.domain.Estudioafiliado;
import com.dromedicas.domain.Ocupacion;
import com.dromedicas.domain.Patologia;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.domain.Usuarioweb;
import com.dromedicas.service.AfiliadoPatologiaService;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.EstudioAfiliadoService;
import com.dromedicas.service.OcupacionService;
import com.dromedicas.service.PatologiaService;
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
	
	@EJB
	private OcupacionService ocupacionService;
	
	@EJB
	private EstudioAfiliadoService estudioService;
	
	@EJB
	private PatologiaService patologiaService;
	
	@EJB
	private AfiliadoPatologiaService afPatologiaService;
	
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
	public Response actualizarDatosAfiliadoWeb(@Context UriInfo ui){
		MultivaluedMap<String, String> map = ui.getQueryParameters();
		
		//se obtienen todos los valores desde la peticion
		 String documento = map.getFirst("documento");
		 String nombres = map.getFirst("nombres");
		 String apellidos = map.getFirst("apellidos");
		 int tipodocumento =Integer.parseInt(map.getFirst("tipodocumento"));
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
		 int cantidadmiembro =Integer.parseInt(map.getFirst("cantidadmiembro"));
		 int tipoMiembro[] = new int[cantidadmiembro];
		 int valIni[] = new int[cantidadmiembro];
		 int valFin[] = new int[cantidadmiembro];
		 
		 //Patologias		 
		 List<Integer> patologiasAfiliado = new ArrayList<Integer>();
		 List<Integer> patologias = new ArrayList<Integer>();
		 
		 
		 for(int i = 0; i < 25; i++){
			 String pTemp = map.getFirst( "p" + (i+1) );			 
			 if(pTemp != null){				 
				 patologiasAfiliado.add(Integer.parseInt(pTemp));
			 }			 
			 String pMiembreTemp = map.getFirst( "pm" + (i+1) );			
			 if(pMiembreTemp != null){
				 patologias.add(Integer.parseInt(pTemp));
			 }
		 }
		 
		//obtiene los valores variables de miembros de familia
		 for(int i = 0; i < cantidadmiembro; i++){	
			 String tempTipo = map.getFirst( "tipomiembroval" + (i+1) );
			 
			 System.out.println("indice: " + i);
			 if( tempTipo != null){
				 tipoMiembro[i] = Integer.parseInt(tempTipo);
				 valIni[i] = Integer.parseInt(map.getFirst( "valini" + (i+1) ));
				 valFin[i] = Integer.parseInt(map.getFirst( "valfin" + (i+1) ));
				 
				 System.out.println("ValIni " + (i+1) + ": " +valIni[i]);
			 }
		 }
		 
		 
		 String hijosmenoresde4 = map.getFirst("hijosmenoresde4");
		 String hijosentre4y12 = map.getFirst("hijosentre4y12");
		 String hijosentre13y18 = map.getFirst("hijosentre13y18");
		 String hijosmayores = map.getFirst("hijosmayores");
		 String cantreferido = map.getFirst("cantreferido");
		 String referidos[] = null;//Email de referidos 
		 
		 if( cantreferido != null ){			 
			 referidos = new String[Integer.parseInt(cantreferido)];
			 for(int i = 0; i < referidos.length; i++){
				 String refTemp = map.getFirst("referido" + (i+1) );
				 referidos[i] = refTemp;
			 }	 
		 }
		 
		 //se crean las instancias respectivas
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
		//informacion adicional formulario 2
			//Ocupacion
		if(ocupacion != null){
			Ocupacion ocupac = this.ocupacionService.obtenerOcupacionById(Integer.parseInt(ocupacion));	
			afiliado.setOcupacionBean(ocupac);
		}
			//Nivel de estudios
		System.out.println("Estudios: " +  estudios) ;
		if(estudios != null){
			Estudioafiliado estudiosnivel = 
					this.estudioService.obtenerEstudioafiliadoById(Integer.parseInt(estudios));
			afiliado.setEstudioafiliado(estudiosnivel);
			
		}
			//Patologias afiliado
		if( !patologiasAfiliado.isEmpty()){
			
			for(int e : patologiasAfiliado){
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
		
		
		
		 
		//se actualizan los valores
		this.afiliadoService.actualizarAfiliado(afiliado);
		 
		 
		 
		 //se envia correo de refereidos
		 
	    
		 
		 
		 
		 
		 
	    System.out.println(nombres);
	    System.out.println(apellidos);
	    
	    
	    return null;
	}
	
	
	//login perfil del usuario
	
	
	
	//actualizacion de datos basicos de afiliado
	
	

}
