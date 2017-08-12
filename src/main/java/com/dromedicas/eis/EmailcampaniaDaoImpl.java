package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Emailcampania;

@Stateless
public class EmailcampaniaDaoImpl implements EmailcampaniaDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Emailcampania> findAllEmailcampanias() {
		return em.createNamedQuery("Emailcampania.findAll").getResultList();
	}

	@Override
	public Emailcampania obtenerEmailcampaniaById(Emailcampania instance) {
		return em.find(Emailcampania.class, instance.getId());
	}

	@Override
	public void insertEmailcampania(Emailcampania instance) {
		em.persist(instance);

	}

	@Override
	public void updateEmailcampania(Emailcampania instance) {
		em.merge(instance);

	}

	@Override
	public void deleteEmailcampania(Emailcampania instance) {
		em.merge(instance);
		em.remove(instance);
	}

}
