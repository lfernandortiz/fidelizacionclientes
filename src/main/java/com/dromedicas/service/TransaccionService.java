package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.eis.TransaccionDao;

@Stateless
public class TransaccionService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	TransaccionDao dao;	
	
	public List<Transaccion> findAllTransaccions(){
		return dao.findAllTransaccions();
	}

	public Transaccion obtenerTransaccionById(Transaccion instance){
		return dao.obtenerTransaccionById(instance);
	}

	public void insertTransaccion(Transaccion instance){
		dao.insertTransaccion(instance);
	}

	public void updateTransaccion(Transaccion instance){
		dao.updateTransaccion(instance);
	}

	public void deleteTransaccion(Transaccion instance){
		dao.deleteTransaccion(instance);
	}
	
	
	public Transaccion obtenerTransaccionPorFactura(String nroFactura) {
		Query query = em.createQuery("FROM Transaccion t WHERE t.nrofactura = :nroFac");
		query.setParameter("nroFac", nroFactura);
		Transaccion temp = null;		
		try {
			temp = (Transaccion) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Factura no encontrada");
			
		}		
		return temp;
	}

}
