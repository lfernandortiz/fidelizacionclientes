package com.dromedicas.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Emailenvio;
import com.dromedicas.domain.Tipoemail;


@Stateless
public class RegistroNotificacionesService {
	
	@EJB
	private TipoEmailService tipoEmailSevice;
	
	@EJB
	private TipoEstadoEmailService tipoEstadoEmail; 
	
	@EJB
	private EmailEnvioService emailEnvioService;
	
	@EJB
	private AfiliadoService afService;
	
	//metoddo que registra en auditor los email enviados a un afiliado
	public void auditarEmailEnviado(Afiliado afiliado, String contenido, String tipo){
		
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
			
			System.out.println("Afiliado En email envio: " + emailEnvio.getAfiliado().getNombres() );
			
			System.out.println("Longitud del Mensaje: " + emailEnvio.getMensaje().length() );
			//persiste 
			emailEnvioService.updateEmailenvio(emailEnvio);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	
	
	//metodo que registra en auditor los sms enviados a un afiliado
	
	
	
	
	////EMAIL
	//suscripcion
	//confirmacion final
	//recuperacion de contrasenia
	//recuperacion contrasenia exitosa
	//compra o acumulacion
	
	
	//SMS
	//cumpleanios afiliado
	//redencion
	
}
