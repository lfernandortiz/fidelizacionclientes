package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.eis.TransaccionDao;

@Remote
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
	
	public Transaccion obtenerRedencionPorFacturaYAfiliado(String nroFactura, Afiliado afiliado) {
		System.out.println("Afiliado: " + afiliado.getNombres());
		System.out.println("Factura: " + nroFactura);
		Query query = em.createQuery("FROM Transaccion t WHERE t.nrofactura LIKE CONCAT('%', :nroFac, '%') and t.afiliado = :af and "
				+ "t.tipotransaccion.idtipotransaccion = 2");
		
		query.setParameter("nroFac", nroFactura);
		query.setParameter("af", afiliado);
		Transaccion temp = null;		
		try {
			temp = (Transaccion) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Factura no encontrada");			
		}		
		return temp;
	}
	
	
	public List<Transaccion> obtenerTxSinNotificacion(){
		
		List<Transaccion> txList = null;
		System.out.println("Consulta txs....");
		
		Query query = em.createQuery("FROM Transaccion t  WHERE  t.tipotransaccion.idtipotransaccion = 1 and "
				+ "t.envionotificacion = 0 and t.redimidos = 0 and t.afiliado.email != '' ");			
		try {
			txList = query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Factura no encontrada");			
		}		
		return txList;
	}

	
}
