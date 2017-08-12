package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Contrato;

@Stateless
public class ContratoDaoImp implements ContratoDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Contrato> findAllContratos() {
		return em.createNamedQuery("Contrato.findAll").getResultList();
	}

	@Override
	public Contrato obtenerContratoById(Contrato instance) {
		return em.find(Contrato.class, instance.getIdcontrato());
	}

	@Override
	public void insertContrato(Contrato instance) {
		em.persist(instance);

	}

	@Override
	public void updateContrato(Contrato instance) {
		em.merge(instance);

	}

	@Override
	public void deleteContrato(Contrato instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
