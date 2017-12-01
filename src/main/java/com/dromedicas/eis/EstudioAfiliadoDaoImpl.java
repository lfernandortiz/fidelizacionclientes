package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Estudioafiliado;

@Stateless
public class EstudioAfiliadoDaoImpl implements EstudioAfiliadoDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Estudioafiliado> findAllEstudioafiliados() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Estudioafiliado.findAll").getResultList();	
	}

	@Override
	public Estudioafiliado obtenerEstudioafiliadoById(Estudioafiliado instance) {
		// TODO Auto-generated method stub
		return em.find(Estudioafiliado.class, instance.getIdestudioafiliado());
	}
	

	@Override
	public void insertEstudioafiliado(Estudioafiliado instance) {
		em.persist(instance);

	}

	@Override
	public void updateEstudioafiliado(Estudioafiliado instance) {
		em.merge(instance);

	}

	@Override
	public void deleteEstudioafiliado(Estudioafiliado instance) {
		em.merge(instance);
		em.remove(instance);	

	}

}
