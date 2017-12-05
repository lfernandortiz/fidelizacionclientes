package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Afiliadopatologia;

@Stateless
public class AfiliadoPatologiaDaoImpl implements AfiliadoPatologiaDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
		

	@SuppressWarnings("unchecked")
	@Override
	public List<Afiliadopatologia> findAllAfiliadopatologias() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Afiliadopatologia.findAll").getResultList();
	}

	@Override
	public Afiliadopatologia obtenerAfiliadopatologiaById(Afiliadopatologia instance) {
		// TODO Auto-generated method stub
		return em.find(Afiliadopatologia.class, instance.getId());
	}

	@Override
	public void insertAfiliadopatologia(Afiliadopatologia instance) {
		em.persist(instance);

	}

	@Override
	public void updateAfiliadopatologia(Afiliadopatologia instance) {
		em.merge(instance);

	}

	@Override
	public void deleteAfiliadopatologia(Afiliadopatologia instance) {
		em.merge(instance);
		em.remove(instance);
	}

}
