package com.dromedicas.eis;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Parametrosemail;

@Stateless
public class ParametrosemailDaoImpl implements ParametrosemailDao {

	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@Override
	public Parametrosemail obtenerParametrosemailById(Parametrosemail instance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertParametrosemail(Parametrosemail instance) {
		em.persist(instance);

	}

	@Override
	public void updateParametrosemail(Parametrosemail instance) {
		em.merge(instance);

	}

	@Override
	public void deleteParametrosemail(Parametrosemail instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
