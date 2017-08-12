package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Tipodocumento;

@Stateless
public class TipodocumentoDaoImp implements TipodocumentoDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipodocumento> findAllTipodocumentos() {
		return em.createNamedQuery("Tipodocumento.findAll").getResultList();
	}

	@Override
	public Tipodocumento obtenerTipodocumentoById(Tipodocumento instance) {
		return em.find(Tipodocumento.class, instance.getIdtipodocumento());
	}

	@Override
	public void insertTipodocumento(Tipodocumento instance) {
		em.persist(instance);

	}

	@Override
	public void updateTipodocumento(Tipodocumento instance) {
		em.merge(instance);

	}

	@Override
	public void deleteTipodocumento(Tipodocumento instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
