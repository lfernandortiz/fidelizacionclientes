package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dromedicas.domain.Empresa;

@Stateless
public class EmpresaDaoImpl implements EmpresaDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> findAllEmpresas() {
		return em.createNamedQuery("Empresa.findAll").getResultList();
	}

	@Override
	public Empresa obtenerEmpresaById(Empresa instance) {
		return em.find(Empresa.class, instance.getIdempresa());
	}
	
	
	@Override
	public Empresa obtenerEmpresaById(String instance) {
		return em.find(Empresa.class, Integer.valueOf(instance ));
	}

	@Override
	public void insertEmpresa(Empresa instance) {
		em.persist(instance);

	}

	@Override
	public void updateEmpresa(Empresa instance) {
		em.merge(instance);

	}

	@Override
	public void deleteEmpresa(Empresa instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
