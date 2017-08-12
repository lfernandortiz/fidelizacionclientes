package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Emailenvio;


@Stateless
public class EmailenvioDaoImpl implements EmailenvioDao {

	@PersistenceContext(name="PuntosFPU")
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Emailenvio> findAllEmailenvios() {
		return em.createNamedQuery("Emailenvio.findAll").getResultList();
	}

	@Override
	public Emailenvio obtenerEmailenvioById(Emailenvio instance) {
		return em.find(Emailenvio.class, instance.getIdemailenvio());
	}

	@Override
	public void insertEmailenvio(Emailenvio instance) {
		em.persist(instance);

	}

	@Override
	public void updateEmailenvio(Emailenvio instance) {
		em.merge(instance);

	}

	@Override
	public void deleteEmailenvio(Emailenvio instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
