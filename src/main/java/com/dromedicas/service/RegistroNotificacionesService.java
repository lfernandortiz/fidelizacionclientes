package com.dromedicas.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Emailenvio;
import com.dromedicas.domain.Smsenvio;
import com.dromedicas.domain.Tipoemail;
import com.dromedicas.domain.Tiposm;


@Stateless
public class RegistroNotificacionesService {
	
	@EJB
	private TipoEmailService tipoEmailSevice;
	
	@EJB
	private TipoEstadoEmailService tipoEstadoEmail; 
	
	@EJB
	private TipoSmsService tipoSmsServie;
	
	@EJB
	private EmailEnvioService emailEnvioService;
	
	@EJB
	private AfiliadoService afService;
	
	@EJB
	private SmsEnvioService smsEnvioService;
	
	//metoddo que registra en auditor los email enviados a un afiliado
	public void auditarEmailEnviado(Afiliado afiliado, String contenido, String tipo){
		
		//metodo que registra en auditor los sms enviados a un afiliado
				
		////EMAIL
		//suscripcion
		//confirmacion final
		//recuperacion de contrasenia
		//recuperacion contrasenia exitosa
		//compra o acumulacion
		
		
		//crea un objeto tipo email
		Tipoemail tipoEmail = this.tipoEmailSevice.obtenerTipoEmialPorDescripcion(tipo);
		
		Afiliado afiliadoP = this.afService.obtenerAfiliadoNacionalidad(afiliado);
		
		//Tipoestaestadoemail tipoEstado = this.tipoEstadoEmail.obtenerTipoEstadoPorDescripcion("Enviado");
		try {		
			
			Emailenvio emailEnvio = new Emailenvio();
			emailEnvio.setAfiliado(afiliadoP);
			emailEnvio.setTipoemailBean(tipoEmail);
			emailEnvio.setFechaenvio(new Date());
			emailEnvio.setEstadoemail(1);
			emailEnvio.setFechaestado(new Date());
			emailEnvio.setMensaje(contenido);
			emailEnvio.setEmail(afiliadoP.getEmail());
		
			//persiste 
			emailEnvioService.updateEmailenvio(emailEnvio);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	
	public Integer auditarSMSEnviado(Afiliado afiliado, String mensaje, String tipoSms, int estado){
		
		//SMS
		//cumpleanios afiliado
		//redencion
		//sms directo
				
		//crea un objeto tipo email
		Tiposm tipoMensaje = this.tipoSmsServie.obtenerTipoSMSPorDescripcion(tipoSms);
				
		Afiliado afiliadoP = this.afService.obtenerAfiliadoNacionalidad(afiliado);
		
		Smsenvio sms = new Smsenvio();
		sms.setAfiliado(afiliadoP);
		sms.setFechaenvio(new Date());
		sms.setTiposm(tipoMensaje);
		sms.setIdestadosms(estado);
		sms.setCelular(afiliadoP.getCelular());
		sms.setMensaje(mensaje);
		
		return this.smsEnvioService.updateSmsenvio(sms);
	}
	
	
}
