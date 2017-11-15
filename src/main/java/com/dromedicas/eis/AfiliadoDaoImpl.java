 package com.dromedicas.eis;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;


@Stateless
public class AfiliadoDaoImpl implements AfiliadoDao {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Afiliado> findAllAfiliados() {
		return em.createNamedQuery("Afiliado.findAll").getResultList();				
	}

	@Override
	public Afiliado obtenerAfiliadoById(Afiliado instance) {
		return em.find(Afiliado.class, instance.getIdafiliado());
	}

	@Override
	public Afiliado obtenerAfiliadoByDocumento(Afiliado instance) {
		Query queryString = em.createQuery("FROM Afiliado a WHERE a.documento = :docu");
		queryString.setParameter("docu", instance.getDocumento());
		return (Afiliado) queryString.getSingleResult();
	}
	
	@Override
	public Afiliado obtenerAfiliadoByDocumento(String documento) {
		Query query = em.createQuery("FROM Afiliado a WHERE a.documento = :docu");
		query.setParameter("docu", documento);
		Afiliado temp = null;		
		try {
			temp = (Afiliado) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");			
		}		
		return temp;
	}
	

	@Override
	public void insertAfiliado(Afiliado instance) {
		em.persist(instance);

	}

	@Override
	public void updateAfiliado(Afiliado instance) {
		em.merge(instance);

	}

	@Override
	public void deleteAfiliado(Afiliado instance) {
		em.merge(instance);
		em.remove(instance);

	}

}
