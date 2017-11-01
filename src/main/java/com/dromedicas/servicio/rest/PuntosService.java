package com.dromedicas.servicio.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.BanlancePuntos;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoTransaccionService;
import com.dromedicas.service.TransaccionService;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Path("/puntos")
@Stateless
public class PuntosService {
	
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
	@Path("/acumularpuntos/{codsucursal}/{momento}/{nrofactua}/{valortx}/{documento}/{puntos}")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarTransaccion(@PathParam("codsucursal") String codsucursal,
										 @PathParam("momento") String momento,
										 @PathParam("nrofactua") String nrofactura,													
										 @PathParam("valortx") Integer valortx,
										 @PathParam("documento") String documento,
										 @PathParam("puntos") int puntos){
		
		Map<String, Object> mapResponse = new HashMap<>();
		
		//se validan todos los parametros
		if( !codsucursal.equals("") && !momento.equals("") && !nrofactura.equals("") && valortx != 0 
					&& !documento.equals("") && puntos!= 0){
			
			Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno(codsucursal);
			
			//se reciben parametros y se crean los objetos necesarios			
			//se obtiene el afiliado	
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
			
			if(afiliado != null){				
				if (sucursal != null) {	
					//invoca al metodo de registro Tx del Bean Balance puntos
					try {
						calculoService.registrarTransaccion(sucursal, momento, nrofactura,
								valortx, afiliado, puntos);
						BanlancePuntos balance = calculoService.consultaPuntos(afiliado);
						mapResponse.put("code","200");
						mapResponse.put("message","Transaccion exitosa");
						mapResponse.put("balance",balance);
					} catch (Exception e) {
						e.printStackTrace();
						mapResponse.put("code","500");
						mapResponse.put("message","Error general en el servidor");
						return Response.status(500).entity(mapResponse).build();
					}
					return Response.status(200).entity(mapResponse).build();
				}else{ // si no se halla la sucursal			
					mapResponse.put("code","401");
					mapResponse.put("message","Sucursal no encontrada");
					return Response.status(401).entity(mapResponse).build();
				}
			}else{ //Si no se halla el afiliado
				mapResponse.put("code","401");
				mapResponse.put("message","Afilaido no encontrado");
				return Response.status(401).entity(mapResponse).build();
			}			
		}else{ //Faltan datos en la solicitud
			mapResponse.put("code","400");
			mapResponse.put("message","El servidor no pudo entender la solicitud debido a una sintaxis mal formada");
			
			return Response.status(401).entity(mapResponse).build();
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
		Map<String, Object> mapResponse = new HashMap<>();
		
		Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
		
		if(afiliado != null){
			
			BanlancePuntos balance = calculoService.consultaPuntos(afiliado);
			
			mapResponse.put("code","200");
			mapResponse.put("message","Transaccion exitosa");
			mapResponse.put("balance",balance);
			return Response.status(200).entity(mapResponse).build();
		}else{
			mapResponse.put("code","401");
			mapResponse.put("message","Afilaido no encontrado");
			return Response.status(401).entity(mapResponse).build();
		}		
	}
		
		
	
	//Grabar transaccion de REDENCION de puntos - Devolviendo balance de puntos
	@Path("/redimirpuntos/{codsucursal}/{momento}/{nrofactua}/{valortx}/{documento}/{puntosredimidos}")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarRedendcion(@PathParam("codsucursal") String codsucursal,
									 	@PathParam("momento") String momento,    
									 	@PathParam("nrofactua") String nrofactura,													
									 	@PathParam("valortx") Integer valortx,
									 	@PathParam("documento") String documento,
									 	@PathParam("puntosredimidos") int puntosRedimidos){
		Map<String, Object> mapResponse = new HashMap<>();
		
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
						BanlancePuntos bTemp = calculoService.consultaPuntos(afiliado);
						
						if(bTemp.getDisponibles() > puntosRedimidos ){
							
							calculoService.redencionPuntos(sucursal, momento, nrofactura,
									valortx, afiliado, puntosRedimidos);
							BanlancePuntos balance =  calculoService.consultaPuntos(afiliado);
							mapResponse.put("code","200");
							mapResponse.put("message","Transaccion exitosa");
							mapResponse.put("balance",balance);
							
							return Response.status(200).entity(mapResponse).build();
						}else{							
							mapResponse.put("code","400");
							mapResponse.put("message","No Tiene los puntos suficiente para esta rededencio");
							return Response.status(400).entity(mapResponse).build();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						mapResponse.put("code","500");
						mapResponse.put("message","Error general en el servidor");
						return Response.status(500).entity(mapResponse).build();
					}
					
				}else{ // si no se halla la sucursal			
					mapResponse.put("code","401");
					mapResponse.put("message","Sucursal no encontrada");
					return Response.status(401).entity(mapResponse).build();
				}
			}else{ //Si no se halla el afiliado
				mapResponse.put("code","401");
				mapResponse.put("message","Afilaido no encontrado");
				return Response.status(401).entity(mapResponse).build();
			}			
			
		}else{
			mapResponse.put("code","400");
			mapResponse.put("message","El servidor no pudo entender la solicitud debido a una sintaxis mal formada");
			return Response.status(400).entity(mapResponse).build();
		}
		
		
	}
	
	
	
	
	
	//Consulta de afiliado | Retorna la infomracion basica del afiliado y su balance de puntos.
	

}
