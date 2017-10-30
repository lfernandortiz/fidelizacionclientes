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

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.CalculoPuntosService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoTransaccionService;
import com.dromedicas.service.TransaccionService;

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
	private CalculoPuntosService calculoService;
	
	
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
	public Map<String, Object> registrarTransaccion(@PathParam("codsucursal") String codsucursal,
													@PathParam("momento") String momento,
													@PathParam("nrofactua") String nrofactua,													
													@PathParam("valortx") Integer valortx,
													@PathParam("documento") String documento,
													@PathParam("puntos") int puntos){
		
		Map<String, Object> mapResponse = new HashMap<>();
		
		//se validan todos los parametros
		if( !codsucursal.equals("") && !momento.equals("") && !nrofactua.equals("") && valortx != 0 
					&& !documento.equals("") && puntos!= 0){
			//se reciben parametros y se crean los objetos necesarios			
			//se obtiene el afiliado	
			Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
			
			if(afiliado != null){				
				//establece la sucursal de la Tx
				Sucursal sucursal = this.sucursalService.obtenerSucursalPorIdIterno(codsucursal);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				Date momentotx  = new Date();
				
				try {
					momentotx = sdf.parse(momento);
					
					System.out.println("Fecha Recibida: " + momento);
					
				} catch (ParseException e) {					
					e.printStackTrace();
				}
				
				if( sucursal != null ){
					
					int idTipoTx = 1;
					Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(idTipoTx);
					Transaccion tx = new Transaccion(); 
					tx.setAfiliado(afiliado);
					tx.setSucursal(sucursal);
					tx.setFechatransaccion(momentotx);
					tx.setNrofactura(nrofactua);
					tx.setValortotaltx(valortx);
					Date fechavencimientopuntos = this.calculoService.addDays(momentotx, 365);
					tx.setVencen(fechavencimientopuntos);
					tx.setTipotransaccion(tipoTx);
					tx.setPuntostransaccion(puntos);
						//graba los puntos iniciales
					txService.updateTransaccion(tx);
					
					
					mapResponse.put("status","200");
					mapResponse.put("message","Transaccion exitosa");
					mapResponse.put("fechavence",sdf.format(fechavencimientopuntos));
					
					
				}else{ // si no se halla la sucursal			
					mapResponse.put("status","401");
					mapResponse.put("message","Sucursal no encontrada");
				}
			}else{ //Si no se halla el afiliado
				mapResponse.put("status","401");
				mapResponse.put("message","Afilaido no encontrado");
			}			
		}else{ //Faltan datos en la solicitud
			mapResponse.put("status","400");
			mapResponse.put("message","El servidor no pudo entender la solicitud debido a una sintaxis mal formada");
		}
		return mapResponse;
	}///Fin del metodo Acumular puntos
		
	
	
	
	
	
	
	
	
	
	
	//Grabar transaccion de REDENCION de puntos - Devolviendo balance de puntos
	
	
	
	//Consulta de Balance de puntos
	
	
	
	//Consulta de afiliado 
	

}
