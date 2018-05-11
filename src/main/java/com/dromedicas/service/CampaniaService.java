package com.dromedicas.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Campania;
import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Empresa;
import com.dromedicas.eis.CampaniaDao;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.smsservice.SMSService;

@Stateless
@TransactionManagement (TransactionManagementType.BEAN) 
public class CampaniaService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private CampaniaDao dao;
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private SMSService smsService;
	
	@EJB
	private EnviarEmailAlertas emailArlertas;
	
	@EJB
	private SmsCampaniaService smsCampaniaService;
	
	@EJB
	private EmpresaService empresaService;
		
	public List<Campania> findAllCampanias() {
		return dao.findAllCampanias();
	}
	
	public List<Campania> findAllCampaniasSMS() {
		Query query = em.createQuery("FROM Campania c WHERE c.tipocampania = 'S' order by c.idcampania desc");		
		List temp = null;	
		try { 
			temp =  query.getResultList();
			
		} catch (NoResultException e) {
			System.out.println("Campanaia no encontrado");			
		}		
		return temp;
	}
	
	public Campania obtenerCampaniaById(Campania instance) {
		return dao.obtenerCampaniaById(instance);
	}
	
	public Campania obtenerCampaniaById(Integer id) {		
		Query query = em.createQuery("FROM Campania c WHERE c.idcampania = :id ");
		query.setParameter("id", id);
		Campania temp = null;	
		try { 
			temp = (Campania) query.getSingleResult();
			
		} catch (NoResultException e) {
			System.out.println("Campanaia no encontrado");			
		}		
		return temp;
	}
	
	
	public void insertCampania(Campania instance) {
		dao.insertCampania(instance);

	}

	
	public Integer updateCampania(Campania instance) {
		return dao.updateCampania(instance);

	}

	
	public void deleteCampania(Campania instance) {
		dao.deleteCampania(instance);
	}
	
	
	/**
	 * Retorna un objeto <code>Campania</code> que fue
	 * programada para el envio en la fecha actual y la hora 
	 * actual.
	 * @return
	 */
	public Campania obtenerCampaniaProgramadaSMS(){
		Query query = em.createQuery("from Campania c where date(c.fechainicio) = current_date and "
				+ " HOUR(c.fechainicio) = HOUR(current_time) and c.tipocampania = 'S' and c.estadocampania = 0");
		Campania temp = null;	
		try { 
			temp = (Campania) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Campania no encontrado");			
		}		
		return temp;
	}
	
	
	/**
	 * Retorna una <code>Campania</code> creada para la fecha dada
	 * como parametro
	 * @param fecha
	 * @return
	 */
	public Campania obtenerCampaniaPorFecha(Date fecha){
		Query query = em.createQuery("from Campania c where date(c.fechainicio) = :fecha_one and "
				+ " HOUR(c.fechainicio) = HOUR(:fecha_two) ");
		query.setParameter("fecha_one", fecha);
		query.setParameter("fecha_two", fecha);
		Campania temp = null;	
		try { 
			temp = (Campania) query.getSingleResult();
			
		} catch (NoResultException e) {
			System.out.println("Campania no encontrado");			
		}		
		return temp;
	}
	
	
	
	public void revisarCampaniaSMSProgramadas() {
		// primero revisa que existan campanias SMS
		Campania cProgramada = this.obtenerCampaniaProgramadaSMS();

		if (cProgramada != null) {			
			// obtiene la coleccion de numeros y id's afiliados
			String queryString  = cProgramada.getCriterios();
			System.out.println("--> " + queryString);
			List<Object[]> audienciaList = this.obtenerUdienciaSMSQuery(cProgramada.getCriterios());
			
			int cupoMensajes = this.smsService.obtenerMensajesDisponibles();
			
			Empresa emp = empresaService.obtenerEmpresaPorNit("900265730-0");
			Contrato contrato =  empresaService.obtenerUltimoContrato(emp);
			
			//si noy cupo de mensajes al momento del envio de la campania envia un email
			if( cupoMensajes > audienciaList.size() ){
				//Marca la campania como enviada
				cProgramada.setEstadocampania((byte) 1);	
				
				//actualiza el objeto campania
				this.updateCampania(cProgramada);
				
				// enviamos los mensajes				
				//el envio de SMS esta habilitado
				if( contrato.getEnviosms() == 1){
					for (Object[] e : audienciaList) {
						Integer id =  (Integer) e[0];
						// obtiene el afiliado por el documento desde la tupla
						Afiliado afTemp = this.afiliadoService.obtenerAfiliadoById(id );

						try {
							System.out.println("ENVIANDO SMS DE CAMPANIA");
							// accede al servicio de SMS y envio el mensaje con un dalay
							// de 0.04 segundos
							this.smsService.envioSMSCampania(afTemp, cProgramada.getContenidosms(), cProgramada);
							
							Thread.sleep(30);
						} catch (InterruptedException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}// final del catch
					}//fin del for
					
					
				}else{
					this.emailArlertas.emailErrorCampania(cProgramada, ""
							+ "No se pudo enviar una campania; La opcion de envios SMS esta deshabilitada");
				}//final del else valida opcion de envio sms
			}else{
				//envia un correo avisando
				this.emailArlertas.emailErrorCampania(cProgramada, 
						"No se pudo enviar una campania; Cupo insuficiente para el envio de la campania");
			}
		}//fin del if
	}
	
	
	
	public List<Object[]> obtenerUdienciaSMSQuery(String query){
		
		Query queryObject = em.createQuery(query);
		List<Object[]> list = null;
		try { 
			list= queryObject.getResultList();
			
		} catch (NoResultException e) {
			System.out.println("Campanaia no encontrado");			
		}		
		return list;
		
	}
	
	

}
