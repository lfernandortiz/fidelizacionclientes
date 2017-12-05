package com.dromedicas.eis;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Afiliadopatologianucleo;

public class AfiliadoPatologiaNucleoDaoImpl implements AfiliadoPatologiaNucleoDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Afiliadopatologianucleo> findAllAfiliadopatologianucleos() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Afiliadopatologianucleo.findAll").getResultList();
	}

	@Override
	public Afiliadopatologianucleo obtenerAfiliadopatologianucleoById(Afiliadopatologianucleo instance) {
		// TODO Auto-generated method stub
		return em.find(Afiliadopatologianucleo.class, instance.getId());
	}

	@Override
	public void insertAfiliadopatologianucleo(Afiliadopatologianucleo instance) {
		em.persist(instance);

	}

	@Override
	public void updateAfiliadopatologianucleo(Afiliadopatologianucleo instance) {
		em.merge(instance);

	}

	@Override
	public void deleteAfiliadopatologianucleo(Afiliadopatologianucleo instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
