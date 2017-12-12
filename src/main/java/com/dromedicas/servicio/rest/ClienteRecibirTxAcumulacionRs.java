package com.dromedicas.servicio.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.EmpresaService;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoTransaccionService;
import com.dromedicas.service.TransaccionService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Stateless
public class ClienteRecibirTxAcumulacionRs {

	Logger log = Logger.getLogger(ClienteRecibirTxAcumulacionRs.class);

	@EJB
	private SucursalService sucursalService;	
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private TipoTransaccionService tipoTxService;
	
	@EJB
	private TransaccionService txService;
		
	@EJB
	private OperacionPuntosService calculoService;
	
	@EJB
	private EmpresaService empresaService;

	private String servicio = "wsjson/fptransacciones";
	
	
	
	/**
	 * 
	 */
	public void obtenerTxSucursales() {

		log.info("Obteniendo Sucursales");
		List<Sucursal> sucursalList = sucursalService.findAllSucursals();
		System.out.println("Total de Sucursales: " + sucursalList.size());

		// Itera Todas las sucursales
		for (Sucursal sucursal : sucursalList) {
			// evalua si la sucursal es 24 Horas
			if (sucursal.getEs24horas().trim().equals("true")) {

				List<Transaccion> nuevasTxsList = obtenerTxFromWS(sucursal);

				// persiste las nuevas transacciones de acumulacion

			} else {
			}
		} // fin for itera sucursales
	}// fin metodo obtenerTxSucursales

	
	
	/**
	 * Retorna la Transacciones por acumular puntos en la sucursal enviada como parametro
	 * @param sucursal
	 * @return
	 */
	private List<Transaccion> obtenerTxFromWS(Sucursal sucursal) {
		//Lista de transacciona a persistir
		List<Transaccion> txList = new ArrayList<Transaccion>();
		
		//Objeto cliente que consume el servicio
		Client client = Client.create();
		WebResource webResource = client.resource(sucursal.getRutaweb() + this.servicio);
		
		//Emboltorio para el JSON de la respuesta
		TransaccionWrap response = webResource.accept("application/json").get(TransaccionWrap.class);
		try {
			//se obtiene el detalle de los datos del JSON
			List<TransaccionWrapDatum> detalle = response.getData();
			if (detalle != null) {
				if (!detalle.isEmpty()) {
					//Itera las Tx's
					for (TransaccionWrapDatum e : detalle) {
						//obtener el objeto afiliado y sucursal
						Afiliado afiliado = this.afiliadoService.obtenerAfiliadoByDocumento(e.getDni());
						
						//si el clientes esta afiliado crea el objeto Transaccion
						if(afiliado != null){
							Transaccion tx = new Transaccion();//nueva transaccion
							
							// se reciben parametros y se crean los objetos necesarios		
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

							Date momentotx = new Date();

							try {
								//parsin fecha tx
								momentotx = sdf.parse(e.getFechadoc());

								System.out.println("Fecha Recibida: " + momentotx);

							} catch (ParseException ex) {
								ex.printStackTrace();
							} // end catch
							
							//setea el objeto Transaccion
							int idTipoTx = 1; //1 es acumulacion
							Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(idTipoTx);
							tx.setAfiliado(afiliado);
							tx.setSucursal(sucursal);
							tx.setFechatransaccion(momentotx);
							String factura = e.getTipodoc()+e.getPrefijo()+e.getNumero();
							tx.setNrofactura(factura);
							
							int valortx = Integer.parseInt(e.getTotal());							
							tx.setValortotaltx(valortx);
							
							Date fechavencimientopuntos = this.calculoService.addDays(momentotx, 365);//-> Cambiar (365) por paramatreo optenico de consulta  
							tx.setVencen(fechavencimientopuntos);
							tx.setTipotransaccion(tipoTx);
							tx.setEnvionotificacion((byte)0);
							//Aca se debe traer el parametros por consulta de base de datos del factor de acumulacion 
							Contrato contrato =  empresaService.obtenerUltimoContrato(sucursal.getEmpresa());
							int baseAc = contrato.getBasegravable();
							
							int mathPuntos = (valortx / baseAc);	//-> Cambiar (100) por paramatreo optenico de consulta  
							System.out.println("----Puntos acumulados: "+ mathPuntos);
														
							tx.setPuntostransaccion(mathPuntos);
							
							//anade le nuevo objeto Transaccion a la coleccion
							txList.add(tx);
							
						}//fin del if afiliado != null
					}// fin del for que itera el Datum del JSON
				}
			} else {
				log.info("No hay informacion de Tx's para la sucursal actual");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txList;
	}
	
	
	
	
}
