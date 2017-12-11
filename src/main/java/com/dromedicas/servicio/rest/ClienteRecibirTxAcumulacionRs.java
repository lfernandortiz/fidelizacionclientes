package com.dromedicas.servicio.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.SucursalService;


@Stateless
public class ClienteRecibirTxAcumulacionRs {
	
	@EJB
	private SucursalService sucursalService;
	
	
	private String servicio = "wsjson/transaccionespuntos";
	
	public void obtenerTxSucursales(){
		
		Logger log = Logger.getLogger(ClienteRecibirTxAcumulacionRs.class);
		log.info("Obteniendo Sucursales");
		List<Sucursal> sucursalList = sucursalService.findAllSucursals();
		System.out.println("Total de Sucursales: " + sucursalList.size());		
		
		// Itera Todas las sucursales
		for (Sucursal sucursal : sucursalList){
			//evalua si la sucursal es 24 Horas
			if (sucursal.getEs24horas().trim().equals("true")) {
				
				List<Transaccion> nuevasTxsList = obtenerTxFromWS(sucursal);
				
			}else{				
			}
		}//fin for itera sucursales		
	}// fin metodo obtenerTxSucursales
	
	
	private List<Transaccion> obtenerTxFromWS( Sucursal  sucursal){
		List<Transaccion> nuevasTxList = new ArrayList<Transaccion>();
		
		return null;
	}

}
