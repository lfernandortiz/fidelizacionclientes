package com.dromedicas.scheduling;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.mailservice.JavaMailService;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.smsservice.SMSService;

public class CumpleanosAfiliadoJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Context jndi;
		try {
			jndi = new InitialContext();
			// Dado que el framework de Quartz se ejecuta en su propio
			// contenedor
			// no es posible inyectar EJB dentro de este contexto.
			// Por lo tanto se hace un lookup via JNDI de los EJB necesarios
			// en el objeto Job del scheduling.

			AfiliadoService afService = (AfiliadoService) jndi
					.lookup("java:global/puntosfarmanorte/AfiliadoService!com.dromedicas.service.AfiliadoService");
			
			EnviarEmailAlertas email= (EnviarEmailAlertas) 
					jndi.lookup("java:global/puntosfarmanorte/EnviarEmailAlertas!com.dromedicas.mailservice.EnviarEmailAlertas");
			
			// de la clase de servicio de afiliado obtengo los cumpleaneros para la fecha actual
			List<Afiliado> listCumpleanos =  afService.obtenerAfiliadosPorCumpleanos();
			// Actualizo las edades de estos cumpleaner
			afService.actualizarCumpleanosList(listCumpleanos);
			// obtengo nuevamente el List de cumpleaneros esta vez con su edad actualizada 
			listCumpleanos =  afService.obtenerAfiliadosPorCumpleanos();
			
			//envio email al area interesada
			email.emailCumpleanosAfiliadoAdmin(listCumpleanos);
			
			//envio email al cliente
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
