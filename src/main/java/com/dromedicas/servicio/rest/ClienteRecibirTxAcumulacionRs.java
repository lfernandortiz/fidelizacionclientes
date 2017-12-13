package com.dromedicas.servicio.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	private OperacionPuntosService puntosService;
	
	@EJB
	private EmpresaService empresaService;

	private String servicio = "wsjson/fptransacciones";
	/**
	 * 
	 */
	public void obtenerTxSucursales() {

		log.info("Obteniendo Sucursales");
		//se obtiene un list con todas las sucursales
		List<Sucursal> sucursalList = sucursalService.findAllSucursals();
		System.out.println("Total de Sucursales: " + sucursalList.size());

		// Itera Todas las sucursales
		for (Sucursal sucursal : sucursalList) {
			// evalua si la sucursal es 24 Horas
			if (sucursal.getEs24horas().trim().equals("true")) {
				
				//obtiene las transacciones pendientes por reportar a puntos para 
				//la sucursal actual asignandolas a un list de transacciones
				List<Transaccion> nuevasTxsList = obtenerTxFromWS(sucursal);

				// persiste las nuevas transacciones de acumulacion
				// en un metodo del EJB de operaciones de puntos
				if( !nuevasTxsList.isEmpty() ){
					this.puntosService.registrarListTransacciones(nuevasTxsList);					
				}
				

			} else {//no es 24 horas
				try {					
					//evalua si la sucursal esta abierta
					if ( estaAbierta(sucursal) ) {
						
						//obtiene las transacciones pendientes por reportar a puntos para 
						//la sucursal actual asignandolas a un list de transacciones
						List<Transaccion> nuevasTxsList = obtenerTxFromWS(sucursal);

						// persiste las nuevas transacciones de acumulacion
						// en un metodo del EJB de operaciones de puntos
						if( !nuevasTxsList.isEmpty() ){
							this.puntosService.registrarListTransacciones(nuevasTxsList);					
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}//fin del else 24 horas				 
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
		
		//Consumiendo el serivicio
		System.out.println("SUCURSAL------>" + sucursal.getNombreSucursal());
		try {
			TransaccionWrap response = webResource.accept("application/json").get(TransaccionWrap.class);
			//se obtiene el detalle de los datos del JSON
			List<TransaccionWrapDatum> detalle = response.getMessage().getData();
			
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
							
							//Setea el objeto Transaccion
							int idTipoTx = 1; //1 es acumulacion
							Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(idTipoTx);
							tx.setAfiliado(afiliado);
							tx.setSucursal(sucursal);
							tx.setFechatransaccion(momentotx);
							String factura = e.getTipodoc()+e.getPrefijo()+e.getNumero();
							tx.setNrofactura(factura);
							
							int valortx = Integer.parseInt(e.getTotal());							
							tx.setValortotaltx(valortx);
							
							Date fechavencimientopuntos = this.puntosService.addDays(momentotx, 365);//-> Cambiar (365) por paramatreo optenico de consulta  
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
	
	
	/**
	 * Valida si la sucursal actual se encuentra abierte con base en el horario
	 * de la misma. Recibe como parametro un objeto <code>Sucursales</code>. 
	 * 
	 * @param instance
	 * @return
	 * @throws ParseException
	 */
	public boolean estaAbierta(Sucursal instance) throws ParseException {
		boolean abierto = false;
		Date currentDate = new Date();		

		Calendar aperturaN = Calendar.getInstance();
		aperturaN.setTime(instance.getHoraperturagen());

		Calendar cierreN = Calendar.getInstance();
		cierreN.setTime(instance.getHoracierregen());

		Calendar aperturaEs = Calendar.getInstance();
		aperturaEs.setTime(instance.getHoraperturaes());

		Calendar cierreEs = Calendar.getInstance();
		cierreEs.setTime(instance.getHoracierrees());

		Calendar horaActual = Calendar.getInstance();
		horaActual.setTime(currentDate);		
				
		int diaSemana = horaActual.get(Calendar.DAY_OF_WEEK);	
		
		
		if (diaSemana >= 2 && diaSemana <= 7) {

			if (horaActual.get(Calendar.HOUR_OF_DAY) > aperturaN.get(Calendar.HOUR_OF_DAY)
					&& horaActual.get(Calendar.HOUR_OF_DAY) < (cierreN.get(Calendar.HOUR_OF_DAY) + 1)) {
				abierto = true;
			}

			if (horaActual.get(Calendar.HOUR_OF_DAY) == aperturaN.get(Calendar.HOUR_OF_DAY)) {
				if (horaActual.get(Calendar.MINUTE) >= aperturaN.get(Calendar.MINUTE))
					abierto = true;				
			}

			if (horaActual.get(Calendar.HOUR_OF_DAY) == cierreN.get(Calendar.HOUR_OF_DAY)) {
				if (horaActual.get(Calendar.MINUTE) < cierreN.get(Calendar.MINUTE))
					abierto = true;
			}
		} else {// Domingos
			if (horaActual.get(Calendar.HOUR_OF_DAY) > aperturaEs.get(Calendar.HOUR_OF_DAY)
					&& horaActual.get(Calendar.HOUR_OF_DAY) < cierreEs.get(Calendar.HOUR_OF_DAY) ) {
				abierto = true;
			}

			if (horaActual.get(Calendar.HOUR_OF_DAY) == aperturaEs.get(Calendar.HOUR_OF_DAY)) {
				if (horaActual.get(Calendar.MINUTE) >= aperturaEs.get(Calendar.MINUTE))
					abierto = true;
			}

			if (horaActual.get(Calendar.HOUR_OF_DAY) == (cierreEs.get(Calendar.HOUR_OF_DAY) + 1)) {
				if (horaActual.get(Calendar.MINUTE) >= cierreEs.get(Calendar.MINUTE))
					abierto = true;
			}
		} // fin del else ppal

		return abierto;
	}
	
	
	
	
}