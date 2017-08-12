package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Transaccion;

@Stateless
public class TransaccionDaoImpl implements TransaccionDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaccion> findAllTransaccions() {
		return em.createNamedQuery("Transaccion.findAll").getResultList();
	}

	@Override
	public Transaccion obtenerTransaccionById(Transaccion instance) {
		return em.find(Transaccion.class, instance.getIdtransaccion());
	}

	@Override
	public void insertTransaccion(Transaccion instance) {
		em.persist(instance);

	}

	@Override
	public void updateTransaccion(Transaccion instance) {
		em.merge(instance);

	}

	@Override
	public void deleteTransaccion(Transaccion instance) {
		em.merge(instance);
		em.remove(instance);	

	}

}
