package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Smscampania;
import com.dromedicas.domain.Smsenvio;
import com.dromedicas.eis.SmsenvioDao;


@Stateless
public class SmsEnvioService {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@EJB
	private SmsenvioDao dao;
	
	
	public List<Smsenvio> findAllSmsenvios() {
		return dao.findAllSmsenvios();
	}

	
	public Smsenvio obtenerSmsenvioById(Smsenvio instance) {
		return dao.obtenerSmsenvioById(instance);
	}

	
	public void insertSmsenvio(Smsenvio instance) {
		dao.insertSmsenvio(instance);

	}

	
	public Integer updateSmsenvio(Smsenvio instance) {
		return dao.updateSmsenvio(instance);

	}

	
	public void deleteSmsenvio(Smsenvio instance) {
		dao.deleteSmsenvio(instance);
	}
	
	public Smsenvio obtenerSmscampaniaById( int id ){
		Query query = em.createQuery("from Smsenvio sms where sms.idsmsenvio = :id");
		query.setParameter("id", id);
		Smsenvio temp = null;
		try {
			temp =  (Smsenvio) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Smsenvio No encontrado");			
		}		
		return temp;
	}
	
	public List<Smsenvio> obtenerSmsEnviadosAfiliado( int idafiliado){
		System.out.println("ID RECIBIDO: " +  idafiliado);
		Query query = em.createQuery("from Smsenvio sms where sms.afiliado.idafiliado = :id");
		query.setParameter("id", idafiliado);
		List<Smsenvio> temp = null;
		try {
			temp =  query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Smsenvios No encontrado");			
		}		
		return temp;
		
		
	}
	

}
