package com.dromedicas.scheduling;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.mailservice.JavaMailService;
import com.dromedicas.service.AfiliadoService;

public class LeerEmailRechazados implements Job {

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

			System.out.println("-------------CONSULTANDO SUCURSALES");

			JavaMailService mailService = (JavaMailService) jndi
					.lookup("java:global/puntosfarmanorte/JavaMailService!com.dromedicas.mailservice.JavaMailService");

			AfiliadoService afiliadoService = (AfiliadoService) jndi
					.lookup("java:global/puntosfarmanorte/AfiliadoService!com.dromedicas.service.AfiliadoService");

			// se conecta al servicio de correo recibiendo la cuenta de correo
			// usada para este fin

			mailService.setConnectionValues("notificaciones");

			// se obtienen todo los mensajes de la bandeja de entrada de la
			// cuenta
			List<Message> inboxM = mailService.getNewMessages();// Mensajes recibidos
			ArrayList<Message> archivo = new ArrayList<Message>();// coleccion mensajes errados

			System.out.println("---------TAMANIO INBOX:" + inboxM.size());

			String emailAddres = null; // direccion de email de mensaje

			for (int i = 0; i < 100; i++) {
				Message m = inboxM.get(i);

				// metodo perdicado que determina si el mensaje es fallido
				if (mailService.isFailedMessage(m)) {

					// metodo que devuelve la direccion email
					emailAddres = mailService.getEmailFailed(m);

					System.out.println("Email Fallido>>>\t" + emailAddres);
					// Actualizo el usuario como email fallido
					try {
						Afiliado aTemp = afiliadoService.obtenerAfiliadoByEmail(emailAddres.trim());
						aTemp.setEmailrechazado((byte) 1);
						afiliadoService.actualizarAfiliado(aTemp);

					} catch (Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}

					// metodo que devuelve el message-id
					// messageId = mailService.messageId(m);
					
					// añado el mensaje a la coleecion de leidos
					// archivo.add(m);

					// copio el mensaje a la carperta de leidos
					mailService.copiarMensajes(mailService.getMailInbox(), mailService.getAchivedFolder(),
							new Message[]{m});

					// borro del inbox el mensaje actual
				    mailService.deleteMessage(m);
				}
				
			}// fin del for
			mailService.closeFolderInbox();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
