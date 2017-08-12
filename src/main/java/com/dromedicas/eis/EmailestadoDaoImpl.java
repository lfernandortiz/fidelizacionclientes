package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Emailestado;

@Stateless
public class EmailestadoDaoImpl implements EmailestadoDao {
	
	@PersistenceContext(name="PuntoFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Emailestado> findAllEmailestados() {
		return em.createNamedQuery("Emailestado.findAll").getResultList();
	}

	@Override
	public Emailestado obtenerEmailestadoById(Emailestado instance) {
		return em.find(Emailestado.class, instance.getId());
	}

	@Override
	public void insertEmailestado(Emailestado instance) {
		em.persist(instance);

	}

	@Override
	public void updateEmailestado(Emailestado instance) {
		em.merge(instance);

	}

	@Override
	public void deleteEmailestado(Emailestado instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
