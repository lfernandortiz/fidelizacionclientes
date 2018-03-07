package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Patologiacampania;

@Stateless
public class PatologiaCampaniaDaoImpl implements PatologiaCampaniaDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;


	@SuppressWarnings("unchecked")
	@Override
	public List<Patologiacampania> findAllPatologiacampanias() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Patologiacampania.findAll").getResultList();				
	}

	@Override
	public Patologiacampania obtenerPatologiacampaniaById(Patologiacampania instance) {
		// TODO Auto-generated method stub
		return em.find(Patologiacampania.class, instance.getId());
	}

	@Override
	public void insertPatologiacampania(Patologiacampania instance) {
		em.persist(instance);

	}

	@Override
	public void updatePatologiacampania(Patologiacampania instance) {
		em.merge(instance);

	}

	@Override
	public void deletePatologiacampania(Patologiacampania instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
