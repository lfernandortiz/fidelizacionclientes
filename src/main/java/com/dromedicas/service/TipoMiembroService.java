package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Estudioafiliado;
import com.dromedicas.domain.Referido;
import com.dromedicas.domain.Tipomiembro;
import com.dromedicas.eis.TipoMiembroDao;

@Stateless
public class TipoMiembroService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private TipoMiembroDao dao;
	
	public List<Tipomiembro> findAllTipomiembros(){
		return dao.findAllTipomiembros();
	}
	
	public Tipomiembro obtenerTipomiembroById(Tipomiembro instance){
		return dao.obtenerTipomiembroById(instance);
	}
	
	public Tipomiembro obtenerTipomiembroById(int id){
		Query query = em.createQuery("FROM Tipomiembro e where e.idtipomiembro = :id");
		query.setParameter("id", id);
		Tipomiembro temp = null;		
		try {
			temp = (Tipomiembro) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");			
		}		
		return temp;
	}
	
	public void insertTipomiembro(Tipomiembro instance){
		dao.insertTipomiembro(instance);
	}
	
	public void updateTipomiembro(Tipomiembro instance){
		dao.updateTipomiembro(instance);
	}
	
	public void deleteTipomiembro(Tipomiembro instance){
		dao.deleteTipomiembro(instance);
	}


}
