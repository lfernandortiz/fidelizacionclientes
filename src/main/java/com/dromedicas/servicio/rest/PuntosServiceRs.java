package com.dromedicas.servicio.rest;

import java.security.Key;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.BalancePuntos;
import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Smsplantilla;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.EmpresaService;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.RegistroNotificacionesService;
import com.dromedicas.service.SmsPlantillaService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoTransaccionService;
import com.dromedicas.service.TransaccionService;
import com.dromedicas.smsservice.SMSService;
import com.dromedicas.util.ExpresionesRegulares;
import com.dromedicas.util.SimpleKeyGenerator;

import io.jsonwebtoken.Jwts;

/**
 * Clase que implementa JAX-RS para ofrecer 
 * servicios rest. Contiene los Endpoint relacionados
 * con operaciones de puntos.
 * @author SOFTDromedicas 
 *
 */
@Path("/puntos")
@Stateless
public class PuntosServiceRs {
	
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private TipoTransaccionService tipoTxService;
	
	@EJB
	private TransaccionService txService;
	
	@EJB
	private SucursalService sucursalService;
	
	@EJB
	private OperacionPuntosService calculoService;
	
	@EJB
	private SMSService smsService;
	
	@EJB
	private SmsPlantillaService smsPlantillaService;
	
	@EJB
	private ExpresionesRegulares rgx;
	
	@EJB
	private EmpresaService empresaService;
	
	@EJB
	private RegistroNotificacionesService regNotificaciones;
	
	@Context UriInfo uriInfo;
	
	
	//Grabar transaccion de ACUMULACION puntos - Devolviendo balance de puntos
	/**
	 * End 
	 * @param codsucursal
	 * @param momento
	 * @param nrofactua
	 * @param valortx
	 * @param documento
	 * @param puntos
	 * @param tipotx
	 * @return
	 */
	@Path("/acumularpuntos/{codsucursal}/{momento}/{nrofactua}/{valortx}/{documento}")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarTransaccion(@PathParam("codsucursal") String codsucursal,
										 @PathParam("momento") String momento,
										 @PathParam("nrofactua") String nrofactura,													
										 @PathParam("valortx") Integer valortx,
										 @PathParam("documento") String documento){
		
		ResponsePuntos responseObject = new ResponsePuntos();
		
		//se validan todos los parametros
		if( !codsucursal.equals("") && !momento.equals("") && !nrofactura.equals("") && valortx != 0 
					&& !documento.equals("") ){
			
			Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno(codsucursal);
			
			//se reciben parametros y se crean los objetos necesarios			
			//se obtiene el afiliado	
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
			
			if(afiliado != null){				
				if (sucursal != null) {	
					//invoca al metodo de registro Tx del Bean Balance puntos
					try {
						int pTxActual = 
								calculoService.registrarTransaccion(sucursal, momento, nrofactura,
										valortx, afiliado);			
						BalancePuntos balance = calculoService.consultaPuntos(afiliado);
						balance.setGanadostxactual(pTxActual);
						responseObject.setBalance(balance);
						responseObject.setStatus(Status.OK.getReasonPhrase());
						responseObject.setCode(200);
						responseObject.setMessage("Transaccion exitosa");						
					} catch (Exception e) {
						e.printStackTrace();
						responseObject.setCode(500);
						responseObject.setStatus(Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
						responseObject.setMessage("Error general en el servido");
						return Response.status(500).entity(responseObject).build();
					}
					return Response.status(200).entity(responseObject).build();
				}else{ // si no se halla la sucursal	
					responseObject.setCode(401);
					responseObject.setMessage("Sucursal no encontrada");					
					return Response.status(401).entity(responseObject).build();
				}
			}else{ //Si no se halla el afiliado
				responseObject.setCode(401);
				responseObject.setMessage("Afiliado no encontrado");
				return Response.status(401).entity(responseObject).build();
			}			
		}else{ //Faltan datos en la solicitud
			responseObject.setCode(400);
			responseObject.setMessage("El servidor no pudo entender la solicitud debido a una sintaxis mal formada");			
			return Response.status(401).entity(responseObject).build();
		}		
	}///Fin del metodo Acumular puntos
		
	
	/**
	 * Consulta de Balance de puntos
	 * @param documento
	 * @return
	 */
	@Path("/consultapuntos/{documento}")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaBalancePuntos(@PathParam("documento") String documento){
		
		ResponsePuntos responseObject = new ResponsePuntos();		
		
		Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
		
		if(afiliado != null){
			
			BalancePuntos balance = calculoService.consultaPuntos(afiliado);
			
			responseObject.setCode(200);
			responseObject.setMessage("Transaccion exitosa");
			responseObject.setBalance(balance);
			responseObject.setStatus(Status.OK.getReasonPhrase());
			if(afiliado.getFotoperfil() != null){
				responseObject.setUrlFotoAfiliado(uriInfo.getBaseUri()+ "afiliado"+ "/getfotoperfil/" + afiliado.getKeycode());
			}
			responseObject.setAfiliado(afiliado);
			return Response.status(200).entity(responseObject).build();
			
		}else{
			responseObject.setCode(401);
			responseObject.setMessage("Afilaido no encontrado");
			return Response.status(401).entity(responseObject).build();
		}		
	}
		
		
	/**
	 * Endpoint para la redencion de puntos.
	 * Descuenta los puntos enviados como puntos a redimir y la diferencia
	 * de la Tx es sujeto de acumulacion de puntos.
	 * Devuelve balance del afiliado.
	 * @param codsucursal
	 * @param momento
	 * @param nrofactura
	 * @param valortx
	 * @param documento
	 * @param puntosRedimidos
	 * @return
	 */
	@SuppressWarnings("unused")
	@Path("/redimirpuntos/{codsucursal}/{momento}/{nrofactua}/{valortx}/{documento}/{puntosredimidos}")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarRedendcion(@PathParam("codsucursal") String codsucursal,
									 	@PathParam("momento") String momento,    
									 	@PathParam("nrofactua") String nrofactura,													
									 	@PathParam("valortx") Integer valortx,
									 	@PathParam("documento") String documento,
									 	@PathParam("puntosredimidos") int puntosRedimidos){
		ResponsePuntos responseObject = new ResponsePuntos();
		
		Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno(codsucursal);
		Contrato contrato =  empresaService.obtenerUltimoContrato(sucursal.getEmpresa());
		
		if( contrato.getRedensionsucursales() == 0 ){
			responseObject.setCode(200);
			responseObject.setMessage("La opcion de REDENCION ESTA DESHABILITADA temporalmente.");
			responseObject.setStatus(Status.OK.getReasonPhrase());
			return Response.status(200).entity(responseObject).build();
		}
		
		
		
		
		//se validan todos los parametros
		if( !codsucursal.equals("") && !momento.equals("") && !nrofactura.equals("") && valortx != 0 
					&& !documento.equals("") && puntosRedimidos!= 0){
			
			//Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno(codsucursal);
			
			//se obtiene el afiliado	
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
			
			if( afiliado.getSinredencion() == 1 ){
				responseObject.setCode(200);
				responseObject.setMessage("REDENCION NO PERMITIDA para este afiliado. Comuniquese con sistemas");
				responseObject.setStatus(Status.OK.getReasonPhrase());
				return Response.status(200).entity(responseObject).build();
			}
			
			if(afiliado != null){
				
				if (sucursal != null) {						
					
					if( contrato.getRedensionsucursales() == 0 ){
						responseObject.setCode(200);
						responseObject.setMessage("La opcion de REDENCION ESTA DESHABILITADA temporalmente.");
						responseObject.setStatus(Status.OK.getReasonPhrase());
						return Response.status(200).entity(responseObject).build();
						
					}
					//invoca al metodo de registro Tx del Bean Balance puntos
					try {
						BalancePuntos bTemp = calculoService.consultaPuntos(afiliado);
						
						if(  bTemp.getDisponiblesaredimir() >= puntosRedimidos ){ 
							//valida que no exista esta factura en la misma sucursal para redimir del mismo afiliado
							Transaccion txTemp = txService.obtenerRedencionPorFacturaYAfiliado(nrofactura, afiliado);
							
							//Validacion factura
							if( txTemp != null ){
								responseObject.setCode(200);
								responseObject.setMessage("Intento de redencion doble.");
								responseObject.setStatus(Status.OK.getReasonPhrase());
								//Aca va a mandar un sms
								return Response.status(200).entity(responseObject).build();
							}else{
								
								// Aca si todo el proceso de redencion normal
								int pTxActual = calculoService.redencionPuntos(sucursal, momento, nrofactura, valortx,
										afiliado, puntosRedimidos);

								BalancePuntos balance = calculoService.consultaPuntos(afiliado);
								balance.setGanadostxactual(pTxActual);
								responseObject.setCode(200);
								responseObject.setStatus(Status.OK.getReasonPhrase());
								responseObject.setMessage("Transaccion exitosa");
								responseObject.setBalance(balance);

								// aca enviar notifiacacion SMS al afiliado
								// sobre la redencion
								if (!("").equals(afiliado.getCelular())) {
									// obtengo la plantilla SMS para redencion;

									List<Smsplantilla> smsList = smsPlantillaService.bucarSMSByFields("redencion");
									String mensaje = smsList.get(0).getSmscontenido();

									// si la plantilla tiene las variables se
									// reemplaza
									if (mensaje.contains("${puntos}")) {
										NumberFormat nf = new DecimalFormat("#,###");
										mensaje = rgx.reemplazaMensaje(mensaje, "puntos", nf.format(puntosRedimidos));
									}

									if (mensaje.contains("${sucursal}")) {
										mensaje = rgx.reemplazaMensaje(mensaje, "sucursal",
												sucursal.getNombreSucursal());
									}

									if (mensaje.contains("${fecha}")) {
										mensaje = rgx.reemplazaMensaje(mensaje, "fecha",
												new SimpleDateFormat("dd/MM/YY HH:mm").format(new Date()));
									}
									int estado = this.smsService.enviarSMSDirecto(afiliado.getCelular(), mensaje,
											"redencion");
									
									
									if( estado != 2 ){
										// auditoria de mensaje enviado al afiliado
										this.regNotificaciones.auditarSMSEnviado(afiliado, mensaje, "Redencion de puntos",
												estado);
									}
								}

								return Response.status(200).entity(responseObject).build();

							} // end else Validacion factura				
						}else{							
							responseObject.setCode(200);
							responseObject.setMessage("No Tiene los puntos suficientes para esta redencion");
							responseObject.setBalance(bTemp);
							responseObject.setStatus(Status.OK.getReasonPhrase());
							return Response.status(200).entity(responseObject).build();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						responseObject.setCode(500);
						responseObject.setMessage("Error general en el servidor");	
						responseObject.setStatus(Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
						return Response.status(500).entity(responseObject).build();
					}
					
				}else{ // si no se halla la sucursal			
					responseObject.setCode(200);
					responseObject.setMessage("Sucursal no encontrada");
					responseObject.setStatus(Status.OK.getReasonPhrase());
					return Response.status(200).entity(responseObject).build();
				}
			}else{ //Si no se halla el afiliado
				responseObject.setCode(200);
				responseObject.setMessage("Afilaido no encontrado");
				responseObject.setStatus(Status.OK.getReasonPhrase());
				return Response.status(200).entity(responseObject).build();
			}			
			
		}else{
			responseObject.setCode(200);
			responseObject.setMessage("El servidor no pudo entender la solicitud debido a una sintaxis mal formada");
			responseObject.setStatus(Status.OK.getReasonPhrase());
			return Response.status(200).entity(responseObject).build();
		}
		
	}
	
	
	/**
	 * Endpoint para la devolucion de una compra.
	 * Descuenta los puntos acumulados, creando una transaccion de 
	 * devolucion, la cual resta estos puntos en el balance del afiliado
	 * @param codsucursal
	 * @param momento
	 * @param nrofactura
	 * @param valortx
	 * @param documento
	 * @return
	 */
	@Path("/devolucion/{codsucursal}/{momento}/{nrofactua}/{valortx}/{documento}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response devolucionTransaccion(@PathParam("codsucursal") String codsucursal,
			@PathParam("momento") String momento, @PathParam("nrofactua") String nrofactura,
			@PathParam("valortx") Integer valortx, @PathParam("documento") String documento) {
		
		ResponsePuntos responseObject = new ResponsePuntos();
		
		// se validan todos los parametros
		if (!codsucursal.equals("") && !momento.equals("") && !nrofactura.equals("") && valortx != 0
				&& !documento.equals("")) {
			
			//primero valida si la tx acumulo puntos
			Transaccion txADevolver =  this.txService.obtenerTransaccionPorFactura(nrofactura);
			
			if(txADevolver == null){
				responseObject.setCode(200);
				responseObject.setMessage("Factura no acumula puntos");
				return Response.status(200).entity(responseObject).build();
			}
			
			System.out.println("FACTURA ENCONTRADA NRO: " + txADevolver.getNrofactura() );
			
			Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno(codsucursal);

			// se obtiene el afiliado
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);

			if (afiliado != null) {
				if (sucursal != null) {
					// invoca al metodo de registro Tx del Bean Balance puntos
					try {
						
						int puntosDevueltos = 
								calculoService.devolucionTx(sucursal, momento, nrofactura, valortx, afiliado);
						BalancePuntos balance = calculoService.consultaPuntos(afiliado);
						balance.setGanadostxactual(puntosDevueltos * -1);
						responseObject.setCode(200);
						responseObject.setMessage("Transaccion exitosa");
						responseObject.setBalance(balance);

						return Response.status(200).entity(responseObject).build();

					} catch (Exception e) {
						e.printStackTrace();
						responseObject.setCode(500);
						responseObject.setMessage("Error general en el servidor");
						return Response.status(500).entity(responseObject).build();
					}

				} else { // si no se halla la sucursal
					responseObject.setCode(401);
					responseObject.setMessage("Sucursal no encontrada");
					return Response.status(401).entity(responseObject).build();
				}
			} else { // Si no se halla el afiliado
				responseObject.setCode(401);
				responseObject.setMessage("Afilaido no encontrado");
				return Response.status(401).entity(responseObject).build();
			}

		} else {
			responseObject.setCode(400);
			responseObject.setMessage("El servidor no pudo entender la solicitud debido a una sintaxis mal formada");
			return Response.status(400).entity(responseObject).build();
		}

	}
	
	
	/**
	 * Consulta de Balance de puntos basado en
	 * Jason Web Tokens JWT
	 * @param documento
	 * @return
	 */
	@Path("/datosafiliado/{token}")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaBalancePuntosJWT(@PathParam("token") String token){
		
		ResponsePuntos responseObject = new ResponsePuntos();		
		String uuidAfiliado = null;
		try {
			String justTheToken = token.substring("Bearer".length()).trim();
			Key key = new SimpleKeyGenerator().generateKey();			
			uuidAfiliado = Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getBody().getSubject();			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Response.Status.BAD_REQUEST.getStatusCode());
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			responseObject.setMessage("Sesion finalizada");
			return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
		}
		
		if(uuidAfiliado != null){
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoUUID(uuidAfiliado);
			
			if(afiliado != null){
				
				BalancePuntos balance = calculoService.consultaPuntos(afiliado);
				
				responseObject.setCode(200);
				responseObject.setMessage("Transaccion exitosa");
				responseObject.setBalance(balance);
				responseObject.setStatus(Status.OK.getReasonPhrase());
				if(afiliado.getFotoperfil() != null){
					responseObject.setUrlFotoAfiliado(uriInfo.getBaseUri()+ "afiliado"+ "/getfotoperfil/" + afiliado.getKeycode());
				}
				responseObject.setAfiliado(afiliado);
				return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
				
			}else{
				responseObject.setCode(401);
				responseObject.setMessage("Afilaido no encontrado");
				return Response.status(401).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
			}
		}else{
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			responseObject.setMessage("Afiliado no encontrado");
			return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();			
		}	
	}
	
	
	
	
	@Path("/ultimastxs/{token}")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerUltimasTransaccion(@PathParam("token") String token){
		ResponsePuntos responseObject = new ResponsePuntos();		
		String uuidAfiliado = null;
		try {
			String justTheToken = token.substring("Bearer".length()).trim();
			Key key = new SimpleKeyGenerator().generateKey();			
			uuidAfiliado = Jwts.parser().setSigningKey(key).parseClaimsJws(justTheToken).getBody().getSubject();			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Response.Status.BAD_REQUEST.getStatusCode());
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			responseObject.setMessage("Sesion finalizada");
			return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
		}
		
		if(uuidAfiliado != null){
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoUUID(uuidAfiliado);
			
			if(afiliado != null){
				
				List<Transaccion> txsList = this.afiliadoService.obtenerUltimasTransacciones(afiliado);
				
				responseObject.setCode(200);
				responseObject.setMessage("Transaccion exitosa");
				responseObject.setBalance(null);
				responseObject.setContenedor(txsList);
				responseObject.setStatus(Status.OK.getReasonPhrase());
				return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
				
			}else{
				responseObject.setCode(401);
				responseObject.setMessage("Afilaido no encontrado");
				return Response.status(401).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();
			}
		}else{
			responseObject.setCode(Status.BAD_REQUEST.getStatusCode());
			responseObject.setStatus(Status.BAD_REQUEST.getReasonPhrase());
			responseObject.setMessage("Afiliado no encontrado");
			return Response.status(Status.OK).entity(responseObject).header("Access-Control-Allow-Origin", "*").build();			
		}	
		
	}
	
	
}
