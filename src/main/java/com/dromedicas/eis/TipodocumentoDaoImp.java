package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
		
	public Tipodocumento obtenerTipodocumentoByIdString(String id) {
		String queryString = "from Tipodocumento d where d.idtipodocumento='" + id + "'";
		Query query = em.createQuery(queryString);
		Tipodocumento temp = null;		
		try {
			temp = (Tipodocumento) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");
		}
		
		return temp;
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
