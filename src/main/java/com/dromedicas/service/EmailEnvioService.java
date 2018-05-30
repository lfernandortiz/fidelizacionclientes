package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Emailenvio;
import com.dromedicas.domain.Smsenvio;
import com.dromedicas.eis.EmailenvioDao;

@Stateless
public class EmailEnvioService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private EmailenvioDao dao;
	
	
	public List<Emailenvio> findAllEmailenvios() {
		return dao.findAllEmailenvios();
	}

	
	public Emailenvio obtenerEmailenvioById(Emailenvio instance) {
		return dao.obtenerEmailenvioById(instance);
	}

	
	public void insertEmailenvio(Emailenvio instance) {
		dao.insertEmailenvio(instance);

	}

	
	public void updateEmailenvio(Emailenvio instance) {
		dao.updateEmailenvio(instance);

	}

	
	public void deleteEmailenvio(Emailenvio instance) {
		dao.deleteEmailenvio(instance);
	}
	
	
	public List<Emailenvio> obtenerEmailEnviadosAfiliado( int idafiliado){
		Query query = em.createQuery("from Emailenvio email where email.afiliado.idafiliado = :id");
		query.setParameter("id", idafiliado);
		List<Emailenvio> temp = null;
		try {
			temp =  query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Smsenvios No encontrado");			
		}		
		return temp;
		
	}


}
