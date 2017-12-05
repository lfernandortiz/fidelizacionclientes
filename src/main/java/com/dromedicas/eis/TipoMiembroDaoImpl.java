package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Tipomiembro;

@Stateless
public class TipoMiembroDaoImpl implements TipoMiembroDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;


	@SuppressWarnings("unchecked")
	@Override
	public List<Tipomiembro> findAllTipomiembros() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Tipomiembro.findAll").getResultList();
	}

	@Override
	public Tipomiembro obtenerTipomiembroById(Tipomiembro instance) {
		// TODO Auto-generated method stub
		return em.find(Tipomiembro.class, instance);
	}

	@Override
	public void insertTipomiembro(Tipomiembro instance) {
		em.persist(instance);

	}

	@Override
	public void updateTipomiembro(Tipomiembro instance) {
		em.merge(instance);

	}

	@Override
	public void deleteTipomiembro(Tipomiembro instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
