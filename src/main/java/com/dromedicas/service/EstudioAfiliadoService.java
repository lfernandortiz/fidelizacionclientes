package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Estudioafiliado;
import com.dromedicas.eis.EstudioAfiliadoDao;

@Stateless
public class EstudioAfiliadoService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private EstudioAfiliadoDao dao;

	
	public List<Estudioafiliado> findAllEstudioafiliados() {
		// TODO Auto-generated method stub
		return dao.findAllEstudioafiliados();
	}

	
	public Estudioafiliado obtenerEstudioafiliadoById(Estudioafiliado instance) {
		// TODO Auto-generated method stub
		return dao.obtenerEstudioafiliadoById(instance);
	}
	
	public Estudioafiliado obtenerEstudioafiliadoById(String id) {
		Query query = em.createQuery("FROM Estudioafiliado e where e.idestudioafiliado = :id");
		query.setParameter("id", id);
		Estudioafiliado temp = null;		
		try {
			temp = (Estudioafiliado) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");			
		}		
		return temp;
	}

	
	public void insertEstudioafiliado(Estudioafiliado instance) {
		dao.insertEstudioafiliado(instance);

	}

	
	public void updateEstudioafiliado(Estudioafiliado instance) {
		dao.updateEstudioafiliado(instance);

	}

	
	public void deleteEstudioafiliado(Estudioafiliado instance) {
		dao.deleteEstudioafiliado(instance);

	}

}
