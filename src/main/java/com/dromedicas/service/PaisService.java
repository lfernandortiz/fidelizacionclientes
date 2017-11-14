package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Pais;
import com.dromedicas.eis.PaisDao;
import com.dromedicas.eis.PaisDaoImpl;

@Stateless
public class PaisService {
	
	@EJB
	private PaisDao dao;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
		
	
	public List<Pais> findAllPais(){
		return dao.findAllPais();
	}
	
	public Pais obtenerPaisByIdString(Pais instance){
		return dao.obtenerPaisById(instance);
	}
	
	public Pais obtenerPaisPorNombre(String nombre){
		String queryString = "from Pais p where p.nombees = :nombre";
		Query query = em.createQuery(queryString);
		query.setParameter("nombre", nombre);
		return (Pais) query.getSingleResult();
	}
	
	public Pais obtenerPaisById(String id){
		String queryString = "from Pais p where p.idpaises = :id";
		Query query = em.createQuery(queryString);
		query.setParameter("id", Integer.parseInt(id));		
		return (Pais) query.getSingleResult();
	}
	

	public void insertPais(Pais instance){
		dao.insertPais(instance);
	}

	public void updatePais(Pais instance){
		dao.updatePais(instance);
	}

	public void deletePais(Pais instance){
		dao.deletePais(instance);
	}

}
