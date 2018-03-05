package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Tipoestaestadoemail;
import com.dromedicas.domain.Tiposm;
import com.dromedicas.eis.TiposmDao;

@Stateless
public class TipoSmsService {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;
	
	@EJB
	private TiposmDao dao;
	
	
	public List<Tiposm> findAllTiposms() {
		return dao.findAllTiposms();
	}

	
	public Tiposm obtenerTiposmById(Tiposm instance) {
		return dao.obtenerTiposmById(instance);
	}

	
	public void insertTiposm(Tiposm instance) {
		dao.insertTiposm(instance);

	}

	
	public void updateTiposm(Tiposm instance) {
		dao.updateTiposm(instance);
	}

	
	public void deleteTiposm(Tiposm instance) {
		dao.deleteTiposm(instance);
	}
	
	public Tiposm obtenerTipoSMSPorDescripcion(String desc){
		Query query = em.createQuery("FROM Tiposms t WHERE lower(t.descripcion) "
				+ "like lower(concat('%', :descripcion, '%'))");
		query.setParameter("descripcion", desc);
		Tiposm temp = null;		
		try {
			temp = (Tiposm) query.getSingleResult();
			
		} catch (Exception e) {
			System.out.println("Elemento no encontrado");
			throw new NoResultException("No hay ningun tipo de SMS encontradco");
		} 	
		return temp;
	}
	
	

}
