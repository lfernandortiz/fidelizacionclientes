package com.dromedicas.scheduling;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.service.CampaniaService;

public class EjecutarCampanaSMS implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Context jndi;
		try {
			jndi = new InitialContext();
			// Dado que el framework de Quartz se ejecuta en su propio
			// contenedor
			// no es posible inyectar EJB dentro de este contexto.
			// Por lo tanto se hace un lookup via JNDI de los EJB necesarios
			// en el objeto Job del scheduling.

			CampaniaService smsCampaniaService = (CampaniaService) jndi
					.lookup("java:global/puntosfarmanorte/CampaniaService!com.dromedicas.service.CampaniaService");			

			System.out.println("Buscando campanias SMS...");
			//Solo llama al EJB de servicio CampaniaService y al metodo revisar campaniasms
			smsCampaniaService.revisarCampaniaSMSProgramadas();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
