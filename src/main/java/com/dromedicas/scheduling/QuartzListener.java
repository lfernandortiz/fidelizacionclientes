package com.dromedicas.scheduling;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Clase Listener para el contexto de Quartz
 * En esta clase se declaran todos los triggers para scheduling de la aplicacion
 * @author Softdromedicas
 *
 */
public class QuartzListener implements ServletContextListener {

	//Schedule para Notificaciones de acumulacion de puntos <0 0/2 * 1/1 * ? *> cada 30 min
	Scheduler schNotiCompra = null;
	
	//Schedule para envio SMS & Email de cumpleanos <0 0 8 ? * MON-FRI *> Todos los dias a las 8am
	
	//Schedule para actualizar edad de los afiliados <0 0 5 ? * MON-FRI *> Todos los dias a las 5am
	
	//Schedule  <0 0 0/1 1/1 * ? *> Cada hora
	
	//Schedule consulta de saldo SMS <	0 0 7 ? * MON-FRI *> Todos los dias a las 7am

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		
		try {
			/*
			 * Schedule Notificaciones compra - acumulacion de puntos
			 */
			// Job para Notificaciones de acumulacion de puntos
			JobDetail job = newJob(NotificacionCompraJob.class).withIdentity("NotificacionAcum", "Group").build();
			
			// Trigger para Notificaciones de acumulacion de puntos cada 30 minutos
			// Son usadas expresiones cron
			Trigger trigger = newTrigger().withIdentity("NotificacionAcum", "Group")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * 1/1 * ? *")) 
					.build();
			
			// configuracion del Setup the Job and Trigger with Scheduler & schedule jobs
			schNotiCompra = new StdSchedulerFactory().getScheduler();
			schNotiCompra.start();
			schNotiCompra.scheduleJob(job, trigger);
			
			/*
			 * Schedule envio SMS diario para afiliados de cumplen anios
			 */
			
			
			
			
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContext) {
		System.out.println("Context Destroyed");
		try {
			schNotiCompra.shutdown();			

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
}//fin de la clase