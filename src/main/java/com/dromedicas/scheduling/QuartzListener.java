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

public class QuartzListener implements ServletContextListener {

	Scheduler scheduler = null;

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		
		System.out.println("Context Initialized");
		
		
		try {
			// Setup the Job class and the Job group
			JobDetail job = newJob(NotificacionCompraJob.class).withIdentity("NotificacionAcum", "Group").build();
			

			Trigger trigger = newTrigger().withIdentity("NotificacionAcum", "Group")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 * * * ?")) // cada 10 minutos
//					.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))  // cada 5 segundos
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))  // cada minuto
					.build();
			
			
			
			// Setup the Job and Trigger with Scheduler & schedule jobs
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
	
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContext) {
		System.out.println("Context Destroyed");
		try {
			scheduler.shutdown();			

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
}//fin de la clase