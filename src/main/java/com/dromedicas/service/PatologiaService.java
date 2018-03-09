package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Patologia;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.eis.EstudioAfiliadoDao;
import com.dromedicas.eis.PatologiaDao;

@SuppressWarnings("serial")
@Stateless 
public class PatologiaService  implements Serializable{
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private PatologiaDao dao;
	
	public List<Patologia> findAllPatologias(){
		return dao.findAllPatologias();
	}
	
	public Patologia obtenerPatologiaById(Patologia instance){
		return dao.obtenerPatologiaById(instance);
	}
	
	public Patologia obtenerPatologiaById(int id){
		Query query = em.createQuery("select p FROM Patologia p where p.idpatologia = :id");
		query.setParameter("id", id);
		Patologia temp = null;		
		try {
			temp = (Patologia) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No encontrado");			
		}		
		return temp;
	}
	
	public Patologia obtenerPatologiaPorDescripcion(String descripcion){
		Query query = em.createQuery("select p FROM Patologia p where p.drescripcion = :des");
		query.setParameter("des", descripcion);
		Patologia temp = null;		
		try {
			temp = (Patologia) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Patologia no encontrada No encontrada");			
		}		
		return temp;
	}

	
	public void insertPatologia(Patologia instance){
		dao.insertPatologia(instance);
	}
	
	public void updatePatologia(Patologia instance){
		dao.updatePatologia(instance);
	}
	
	public void deletePatologia(Patologia instance){
		dao.deletePatologia(instance);
	}

}
