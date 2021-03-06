package com.dromedicas.scheduling;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dromedicas.domain.Transaccion;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.service.TransaccionService;

@Stateless

public class NotificacionCompraJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		Context jndi;
		try {
			//Dado que el framework de Quartz se ejecuta en su propio contenedor
			//no es posible inyectar EJB dentro de este contexto.
			//Por lo tanto se hace un  lookup via JNDI de los  EJB necesarios 
			//en el objeto Job del scheduling.
			jndi = new InitialContext();
			TransaccionService txService= (TransaccionService)
					jndi.lookup("java:global/puntosfarmanorte/TransaccionService!com.dromedicas.service.TransaccionService");
			EnviarEmailAlertas email= (EnviarEmailAlertas) 
					jndi.lookup("java:global/puntosfarmanorte/EnviarEmailAlertas!com.dromedicas.mailservice.EnviarEmailAlertas");
			
			//Se buscan todas las Tx's que no han sido notificadas, el afiliado debe tener correo
			List<Transaccion>  txList = txService.obtenerTxSinNotificacion();			
			
			System.out.println("Total de Txs a notificiar: " + txList.size());
			
			if( txList.size() > 0 ){
				email.emailNotificacionCompra(txList);
				
				//Marca como enviada notificacion a las tx's
				for(Transaccion tx : txList){
					tx.setEnvionotificacion((byte) 1);
					txService.updateTransaccion(tx);
				}
			}					
			
		} catch (Exception e) {
			System.out.println("ERROR EN EL ENVIO DE EMAIL TX");
			e.printStackTrace();
		}
	}
	

}
