package com.dromedicas.scheduling;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Smsplantilla;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.SmsPlantillaService;
import com.dromedicas.smsservice.SMSService;

public class SMSCumpleanosJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Context jndi;
		
		try {
			jndi = new InitialContext();
			
			AfiliadoService afService = (AfiliadoService) jndi
					.lookup("java:global/puntosfarmanorte/AfiliadoService!com.dromedicas.service.AfiliadoService");
			
			SMSService sms = (SMSService) jndi
					.lookup("java:global/puntosfarmanorte/SMSService!com.dromedicas.smsservice.SMSService");
			
			SmsPlantillaService plantillaService = (SmsPlantillaService) jndi
					.lookup("java:global/puntosfarmanorte/SmsPlantillaService!com.dromedicas.service.SmsPlantillaService");
			
			//envio SMS al cliente
			List<Afiliado> listAfiliadoCumpleSms =  afService.obtenerAfiliadosCumpleanosCelular();
			
			Smsplantilla planitlla = plantillaService.bucarSMSByDescription("Plantilla SMS Cumpleanos");
			sms.envioSMSCumpleanos(listAfiliadoCumpleSms, planitlla);
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	

}
