package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Ocupacion;

@Stateless
public class OcupacionDaoImpl implements OcupacionDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Ocupacion> findAllOcupacion() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Ocupacion.findAll").getResultList();
	}

	@Override
	public Ocupacion obtenerOcupacionById(Ocupacion instance) {
		// TODO Auto-generated method stub
		return em.find(Ocupacion.class, instance.getIdocupacion());
	}

	@Override
	public void insertOcupacion(Ocupacion instance) {
		em.persist(instance);

	}

	@Override
	public void updateOcupacion(Ocupacion instance) {
		em.merge(instance);

	}

	@Override
	public void deleteOcupacion(Ocupacion instance) {
		em.merge(instance);
		em.remove(instance);	
	}

}
