package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Referido;

@Stateless
public class ReferidoDaoImpl implements ReferidoDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Referido> findAllReferidos() {		
		return em.createNamedQuery("Referido.findAll").getResultList();
	}

	@Override
	public Referido obtenerReferidoById(Referido instance) {
		return em.find(Referido.class, instance.getIdreferido());
	}
	
	
	@Override
	public Referido obtenerReferidoPorEmail(String email) {
		Query queryString = em.createQuery("FROM Referido r WHERE r.emailreferido = :email");
		queryString.setParameter("email", email);
		return (Referido) queryString.getSingleResult();
	}

	
	@Override
	public void insertReferido(Referido instance) {
		em.persist(instance);
		
	}

	@Override
	public void updateReferido(Referido instance) {
		em.merge(instance);
		
	}

	@Override
	public void deleteReferido(Referido instance) {
		em.merge(instance);
		em.remove(instance);		
	}

	
}
