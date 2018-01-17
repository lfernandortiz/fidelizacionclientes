package com.dromedicas.scheduling;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dromedicas.service.TransaccionService;
import com.dromedicas.servicio.rest.ClienteRecibirTxAcumulacionRs;

public class RecibirTxPuntosSucursalJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		Context jndi;
		try {
			jndi = new InitialContext();
			//Dado que el framework de Quartz se ejecuta en su propio contenedor
			//no es posible inyectar EJB dentro de este contexto.
			//Por lo tanto se hace un  lookup via JNDI de los  EJB necesarios 
			//en el objeto Job del scheduling.		
			
			System.out.println("-------------CONSULTANDO SUCURSALES");
			
			ClienteRecibirTxAcumulacionRs clienteTx= (ClienteRecibirTxAcumulacionRs)
					jndi.lookup("java:global/puntosfarmanorte/ClienteRecibirTxAcumulacionRs!com.dromedicas.servicio.rest.ClienteRecibirTxAcumulacionRs");
			//Se consume los servicios en cada sucursal para obtener las tx's
			
			clienteTx.obtenerTxSucursales();			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
