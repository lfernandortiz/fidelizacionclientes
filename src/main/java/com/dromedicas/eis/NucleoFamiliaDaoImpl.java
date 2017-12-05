package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Nucleofamilia;

@Stateless
public class NucleoFamiliaDaoImpl implements NucleoFamiliaDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Nucleofamilia> findAllNucleofamilias() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Nucleofamilia.findAll").getResultList();
	}

	@Override
	public Nucleofamilia obtenerNucleofamiliaById(Nucleofamilia instance) {
		// TODO Auto-generated method stub
		return em.find(Nucleofamilia.class, instance.getId());
	}

	@Override
	public void insertNucleofamilia(Nucleofamilia instance) {
		em.persist(instance);

	}

	@Override
	public void updateNucleofamilia(Nucleofamilia instance) {
		em.merge(instance);

	}

	@Override
	public void deleteNucleofamilia(Nucleofamilia instance) {
		em.merge(instance);
		em.remove(instance);	

	}
	

}
