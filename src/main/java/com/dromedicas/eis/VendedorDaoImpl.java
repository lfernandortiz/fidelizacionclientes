package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Vendedor;

@Stateless
public class VendedorDaoImpl implements VendedorDao {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendedor> findAllVendedors() {
		return em.createNamedQuery("Vendedor.findAll").getResultList();
	}

	@Override
	public Vendedor obtenerVendedorById(Vendedor instance) {
		return em.find(Vendedor.class, instance.getDocuvendedor());
	}

	@Override
	public void insertVendedor(Vendedor instance) {
		em.persist(instance);

	}

	@Override
	public void updateVendedor(Vendedor instance) {
		em.merge(instance);

	}

	@Override
	public void deleteVendedor(Vendedor instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
