package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Tipoestaestadoemail;

@Stateless
public class TipoestaestadoemailDaoImpl implements TipoestaestadoemailDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipoestaestadoemail> findAllTipoestaestadoemails() {
		return em.createNamedQuery("Tipoestaestadoemail.findAll").getResultList();
	}

	@Override
	public Tipoestaestadoemail obtenerTipoestaestadoemailById(Tipoestaestadoemail instance) {
		return em.find(Tipoestaestadoemail.class, instance.getIdtipoestaestadoemail());
	}

	@Override
	public void insertTipoestaestadoemail(Tipoestaestadoemail instance) {
		em.persist(instance);

	}

	@Override
	public void updateTipoestaestadoemail(Tipoestaestadoemail instance) {
		em.merge(instance);

	}

	@Override
	public void deleteTipoestaestadoemail(Tipoestaestadoemail instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
