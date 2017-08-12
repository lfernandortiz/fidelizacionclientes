package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Tipoemail;

@Stateless
public class TipoemailDaoImpl implements TipoemailDao {
	
	@PersistenceContext(name="PuntoFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipoemail> findAllTipoemails() {
		return em.createNamedQuery("Tipoemail.findAll").getResultList();
	}

	@Override
	public Tipoemail obtenerTipoemailById(Tipoemail instance) {
		return em.find(Tipoemail.class, instance.getIdtipoemail());
	}

	@Override
	public void insertTipoemail(Tipoemail instance) {
		em.persist(instance);

	}

	@Override
	public void updateTipoemail(Tipoemail instance) {
		em.merge(instance);

	}

	@Override
	public void deleteTipoemail(Tipoemail instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
