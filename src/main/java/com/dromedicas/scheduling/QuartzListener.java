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
 * Clase Listener para el contexto de Quartz En esta clase se declaran todos los
 * triggers para scheduling de la aplicacion
 * 
 * @author Softdromedicas
 *
 */
public class QuartzListener implements ServletContextListener {

	// Schedule para Notificaciones de acumulacion de puntos <0 0/2 * 1/1 * ? *>
	// cada 30 min
	Scheduler schNotiCompra = null;
	Scheduler schTxPuntosF = null;
	Scheduler schEmailRechazo = null;
	Scheduler schCumpleanos = null;
	Scheduler schSMSCumple = null;
	Scheduler schSMSCampania = null;
	
	

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
//		try {
			
//			/*
//			 * Schedule Notificaciones compra - acumulacion de puntos | Cada 40 minutos
//			 */			
//			// Job para Notificaciones de acumulacion de puntos
//			JobDetail job = newJob(NotificacionCompraJob.class).withIdentity("NotificacionAcum", "Group").build();
//			
//			// Trigger para Notificaciones de acumulacion de puntos cada 30 minutos
//			// Son usadas expresiones cron
//			Trigger trigger = newTrigger().withIdentity("NotificacionAcum", "Group")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/40 * 1/1 * ? *")) //cada 40 min 	0 0/40 * 1/1 * ? *
//					.build();
//			
//			// configuracion del Setup the Job and Trigger with Scheduler & schedule jobs
//			schNotiCompra = new StdSchedulerFactory().getScheduler();
//			schNotiCompra.start();
//			schNotiCompra.scheduleJob(job, trigger);
//			
//			
//			
//			/*
//			 * Schedule Obtener transacciones de sucursales | Cada 30 minutos
//			 */
//			//Job para Obtener las transacciones de sucursales 			
//			JobDetail jobTx = newJob(RecibirTxPuntosSucursalJob.class).withIdentity("TransaccionAfiliado", "TxGroup").build();
//			
//			// Trigger recorrer todas las sucursales y obtener las ultimas transacciones 
//			// de puntos farmanorte
//			Trigger triggerTx = newTrigger().withIdentity("TransaccionAfiliado", "TxGroup")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * 1/1 * ? *")) //cada 30 min
//					.build();
//			
//			// configuracion del Setup the Job and Trigger with Scheduler & schedule jobs
//			schTxPuntosF = new StdSchedulerFactory().getScheduler();
//			schTxPuntosF.start();
//			schTxPuntosF.scheduleJob(jobTx, triggerTx);
			
			

//			/*
//			 * Schedule revision de correos rechazados | Todos los dias a las 6am. 
//			 * test  5 minutos:	0 0/5 * 1/1 * ? *
//			 */
//			//Job para que ejecuta el EJB que realiza la lectura de los email			
//			JobDetail jobEmailR = newJob(LeerEmailRechazados.class).withIdentity("EmailRechazados", "EmailGroup").build();
//			
//			// Trigger todos los dias a las 6 am abre el el buzon de despacho de correos y revisa las direcciones
//			// de email rechazadas y actualiza esta caracteristica en la base de datos
//			Trigger triggerEmailR = newTrigger().withIdentity("EmailRechazados", "EmailGroup")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * 1/1 * ? *")) //Cada 6 Horas 0 0 0/6 1/1 * ? *
//					.build();			
//						
//			 			
//			// configuracion del Setup the Job and Trigger with Scheduler & schedule jobs
//			schEmailRechazo = new StdSchedulerFactory().getScheduler();
//			schEmailRechazo.start();
//			schEmailRechazo.scheduleJob(jobEmailR, triggerEmailR);
						
			
		
			
			
//			/*
//			 * Schedule revision de cumpleanos y actualiza edad afiliados | Todos los dias a las
//			 * 6am.
//			 */
//			// Job para que ejecuta el EJB que realiza la lectura de los email
//			JobDetail cumpleanosAct = newJob(CumpleanosAfiliadoJob.class).withIdentity("Cumpleanos", "CumpleGroup")
//					.build();
//
//			// Trigger todos los dias a las 6 am abre el el buzon de despacho de
//			// correos y revisa las direcciones
//			// de email rechazadas y actualiza esta caracteristica en la base de
//			// datos
//			Trigger triggerCumple = newTrigger().withIdentity("Cumpleanos", "CumpleGroup")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 6 ? * MON-SUN *")) // Todos los dias a las 6AM  0 0 6 ? * MON-SUN *
//					.build();
//
//			
//			// configuracion del Setup the Job and Trigger with Scheduler &
//			// schedule jobs
//			schCumpleanos = new StdSchedulerFactory().getScheduler();
//			schCumpleanos.start();
//			schCumpleanos.scheduleJob(cumpleanosAct, triggerCumple);
//			
//			
//			
//			/*
//			 * Schedule revision de cumpleanos y actualiza edad afiliados SMS| 
//			 *  
//			 */
//			// Job para que ejecuta el EJB que realiza la lectura de los email
//			JobDetail smsCumpleanos = newJob(SMSCumpleanosJob.class).withIdentity("SmsCumpleanos", "SmsCumpleGroup")
//					.build();
//
//			// Trigger todos los dias a las 6 am abre el el buzon de despacho de
//			// correos y revisa las direcciones
//			// de email rechazadas y actualiza esta caracteristica en la base de
//			// datos
//			Trigger triggerSMSCumple = newTrigger().withIdentity("SmsCumpleanos", "SmsCumpleGroup")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 8 ? * MON-SUN *")) // Todos los dias a las 6AM  0 0 6 ? * MON-SUN *
//					.build();
//
//			
//			// configuracion del Setup the Job and Trigger with Scheduler &
//			// schedule jobs
//			schSMSCumple = new StdSchedulerFactory().getScheduler();
//			schSMSCumple.start();
//			schSMSCumple.scheduleJob(smsCumpleanos, triggerSMSCumple);
//		
//			
//			
//			/*
//			 * Schedule valida cada hora si hay campanias SMS
//			 *  
//			 */
//			// Job para que ejecuta el EJB que realiza la lectura de los email
//			JobDetail smsCampania = newJob(EjecutarCampanaSMS.class).withIdentity("SmsCampania", "SmsCampaniaGroup")
//					.build();
//
//			//Disparador para el schedule de sms campania
//			Trigger triggerSMSCampania = newTrigger().withIdentity("SmsCampania", "SmsCampaniaGroup")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/1 1/1 * ? *")) // 0 0 0/1 1/1 * ? *
//					.build();
//			
//			// configuracion del Setup the Job and Trigger with Scheduler &
//			// schedule jobs
//			schSMSCampania = new StdSchedulerFactory().getScheduler();
//			schSMSCampania.start();
//			schSMSCampania.scheduleJob(smsCampania, triggerSMSCampania);
		

//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}

	}
	
	

	@Override
	public void contextDestroyed(ServletContextEvent servletContext) {
		System.out.println("Context Destroyed");
		try {
			schNotiCompra.shutdown();
			schTxPuntosF.shutdown();
			schEmailRechazo.shutdown();
			schCumpleanos.shutdown();
			schSMSCumple.shutdown();
			schSMSCampania.shutdown();
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}// fin de la clase