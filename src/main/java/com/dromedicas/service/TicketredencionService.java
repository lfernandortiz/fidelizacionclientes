package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Ticketredencion;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.eis.TicketredencionDao;

@Stateless
public class TicketredencionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private TicketredencionDao dao;
	
	public List<Ticketredencion> findAllTicketredencions(){
		return dao.findAllTicketredencions();
	}
	
	public Ticketredencion obtenerTicketredencionById(Ticketredencion instance){
		return dao.obtenerTicketredencionById(instance);
	}
	
	public Ticketredencion obtenerTicketredencionByNroFactura(String nroFactura){
		Query query = em.createQuery("FROM Ticketredencion t where t.transaccion.nrofactura = :nroFac");
		query.setParameter("nroFac", nroFactura);
		Ticketredencion temp = null;		
		try {
			temp = (Ticketredencion) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Ticket no encontrado");			
		}		
		return temp;
	}
	
	public Ticketredencion obtenerTicketredencionByFactura(Transaccion tx){
		Query query = em.createQuery("FROM Ticketredencion t where t.transaccion = :tx");
		query.setParameter("tx", tx);
		Ticketredencion temp = null;		
		try {
			temp = (Ticketredencion) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Ticket no encontrado");			
		}		
		return temp;
	}
		
	
	public void insertTicketredencion(Ticketredencion instance){
		dao.insertTicketredencion(instance);
	}
	
	public void updateTicketredencion(Ticketredencion instance){
		dao.updateTicketredencion(instance);
	}
	
	public void deleteTicketredencion(Ticketredencion instance){
		dao.deleteTicketredencion(instance);
	}


}
