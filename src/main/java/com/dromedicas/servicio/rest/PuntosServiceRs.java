package com.dromedicas.servicio.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.BalancePuntos;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoTransaccionService;
import com.dromedicas.service.TransaccionService;

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
	
	
	//Grabar transaccion de ACUMULACION puntos - Devolviendo balance de puntos
	/**
	 * Tipo Tx: R: Redencion| A:Acumulacion |
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
				responseObject.setMessage("Afilaido no encontrado");
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
		
		//se validan todos los parametros
		if( !codsucursal.equals("") && !momento.equals("") && !nrofactura.equals("") && valortx != 0 
					&& !documento.equals("") && puntosRedimidos!= 0){
			
			Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno(codsucursal);
			
			//se obtiene el afiliado	
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
			
			if(afiliado != null){				
				if (sucursal != null) {	
					//invoca al metodo de registro Tx del Bean Balance puntos
					try {
						BalancePuntos bTemp = calculoService.consultaPuntos(afiliado);
						
						if(  bTemp.getDisponiblesaredimir() >= puntosRedimidos ){ 
							int pTxActual =  calculoService.redencionPuntos(sucursal, momento, nrofactura,
													valortx, afiliado, puntosRedimidos);
							BalancePuntos balance =  calculoService.consultaPuntos(afiliado);
							balance.setGanadostxactual(pTxActual);
							responseObject.setCode(200);
							responseObject.setMessage("Transaccion exitosa");
							responseObject.setBalance(balance);
							
							return Response.status(200).entity(responseObject).build();
						}else{							
							responseObject.setCode(400);
							responseObject.setMessage("No Tiene los puntos suficientes para esta rededencion");
							responseObject.setBalance(bTemp);
							return Response.status(400).entity(responseObject).build();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						responseObject.setCode(500);
						responseObject.setMessage("Error general en el servidor");						
						return Response.status(500).entity(responseObject).build();
					}
					
				}else{ // si no se halla la sucursal			
					responseObject.setCode(401);
					responseObject.setMessage("Sucursal no encontrada");
					return Response.status(401).entity(responseObject).build();
				}
			}else{ //Si no se halla el afiliado
				responseObject.setCode(401);
				responseObject.setMessage("Afilaido no encontrado");
				return Response.status(401).entity(responseObject).build();
			}			
			
		}else{
			responseObject.setCode(400);
			responseObject.setMessage("El servidor no pudo entender la solicitud debido a una sintaxis mal formada");
			return Response.status(400).entity(responseObject).build();
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
				responseObject.setCode(401);
				responseObject.setMessage("Factura no acumula puntos");
				return Response.status(401).entity(responseObject).build();
			}

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
	

}
