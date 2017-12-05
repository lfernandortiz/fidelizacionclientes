package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Estudioafiliado;
import com.dromedicas.domain.Ocupacion;
import com.dromedicas.eis.OcupacionDao;

@Stateless
public class OcupacionService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private OcupacionDao dao;
	
	public List<Ocupacion> findAllOcupacion(){
		return dao.findAllOcupacion();
	}
	
	public Ocupacion obtenerOcupacionById(Ocupacion instance){
		return dao.obtenerOcupacionById(instance);
	}
	
	public Ocupacion obtenerOcupacionById(String id){
		Query query = em.createQuery("FROM Ocupacion o where o.idocupacion = :id");
		query.setParameter("id", id);
		Ocupacion temp = null;		
		try {
			temp = (Ocupacion) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");			
		}		
		return temp;
	}
	
	public void insertOcupacion(Ocupacion instance){
		dao.insertOcupacion(instance);
	}
	
	public void updateOcupacion(Ocupacion instance){
		dao.updateOcupacion(instance);
	}
	
	public void deleteOcupacion(Ocupacion instance){
		dao.deleteOcupacion(instance);
	}

}
