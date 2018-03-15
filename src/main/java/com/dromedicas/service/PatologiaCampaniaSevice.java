package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Patologiacampania;
import com.dromedicas.eis.PatologiaCampaniaDao;


@Stateless
public class PatologiaCampaniaSevice {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private PatologiaCampaniaDao dao;


	@SuppressWarnings("unchecked")
	public List<Patologiacampania> findAllPatologiacampanias() {
		// TODO Auto-generated method stub
		return dao.findAllPatologiacampanias();
	}

	
	public Patologiacampania obtenerPatologiacampaniaById(Patologiacampania instance) {
		// TODO Auto-generated method stub
		return dao.obtenerPatologiacampaniaById(instance);
	}

	
	public void insertPatologiacampania(Patologiacampania instance) {
		dao.insertPatologiacampania(instance);

	}

	
	public void updatePatologiacampania(Patologiacampania instance) {
		dao.updatePatologiacampania(instance);

	}

	
	public void deletePatologiacampania(Patologiacampania instance) {
		dao.deletePatologiacampania(instance);
	}
	
	
	public Integer deltePatologiaByIdCampania(Integer id){
		
		Query query = em.createQuery("delete from Patologiacampania p where p.id.idcampania = :id");
		query.setParameter("id", id);
		
		Integer temp = null;
		
		try { 
			temp = query.executeUpdate();
			
		} catch (NoResultException e) {
			System.out.println("Afiliado no encontrado");			
		}		
		return temp;
	}
	

}
