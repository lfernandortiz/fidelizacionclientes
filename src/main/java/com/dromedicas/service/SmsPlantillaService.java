package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Smsplantilla;
import com.dromedicas.eis.SmsplantillaDao;


@Stateless
public class SmsPlantillaService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;	
	
	@EJB
	private SmsplantillaDao dao;
	
	public List<Smsplantilla> findAllSmsplantillas(){
		return dao.findAllSmsplantillas();	
	}

	public Smsplantilla obtenerSmsplantillaById(Smsplantilla instance){
		return dao.obtenerSmsplantillaById(instance);
	}

	public void insertSmsplantilla(Smsplantilla instance){
		dao.insertSmsplantilla(instance);
	}

	public void updateSmsplantilla(Smsplantilla instance){
		dao.updateSmsplantilla(instance);
	}

	public void deleteSmsplantilla(Smsplantilla instance){
		dao.deleteSmsplantilla(instance);
	}
	
	/**
	 * Busqueda para el list
	 */

	@SuppressWarnings("unchecked")
	public List<Smsplantilla> bucarSMSByFields(String criterio){
		System.out.println("nombre recibido: " + criterio);
		String queryString = "from Smsplantilla s  where  s.descripcion like '%" + criterio.trim() + "%' " ;
			
		System.out.println("QueryString:" + queryString);
		Query query = em.createQuery(queryString);
		return query.getResultList();
	}
	
	

}
