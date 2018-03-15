package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Campania;
import com.dromedicas.domain.Paremetroscampania;
import com.dromedicas.eis.ParametrosCampaniaDao;

@Stateless
public class ParametrosCampaniaSevice {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private ParametrosCampaniaDao dao;

	
	public List<Paremetroscampania> findAllParemetroscampanias() {
		// TODO Auto-generated method stub
		return dao.findAllParemetroscampanias();
	}

	
	public Paremetroscampania obtenerParemetroscampaniaById(Paremetroscampania instance) {
		// TODO Auto-generated method stub
		return dao.obtenerParemetroscampaniaById(instance);
	}
	
	public Paremetroscampania obtenerParemetroscampaniaByCampania(Campania instance){
		Query query = em.createQuery("FROM Paremetroscampania p WHERE p.campania = :camp");
		query.setParameter("camp", instance);
		Paremetroscampania temp = null;		
		try {
			temp = (Paremetroscampania) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println(" no encontrado");			
		}		
		return temp;
	}

	
	public void insertParemetroscampania(Paremetroscampania instance) {
		dao.insertParemetroscampania(instance);
		
	}

	
	public void updateParemetroscampania(Paremetroscampania instance) {
		System.out.println("CAMPANA RECIBIDA:  " +  instance.getCampania().getNombrecampania()  );
		
		dao.updateParemetroscampania(instance);
		
	}

	
	public void deleteParemetroscampania(Paremetroscampania instance) {
		dao.deleteParemetroscampania(instance);
		
	}

}
