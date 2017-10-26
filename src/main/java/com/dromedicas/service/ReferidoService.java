package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Referido;
import com.dromedicas.eis.ReferidoDao;

@Stateless
public class ReferidoService {
	
	@EJB
	ReferidoDao dao;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	public List<Referido> findAllReferidos(){
		return dao.findAllReferidos();
	}
	
	public Referido obtenerReferidoById(Referido instance){
		return dao.obtenerReferidoById(instance);
	}
	
	public Referido obtenerReferidoPorEmail(String email){
		Query query = em.createQuery("select r FROM Referido r where r.emailreferido = :email");
		query.setParameter("email", email);
		Referido temp = null;		
		try {
			temp = (Referido) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Referido No encontrado");			
		}		
		return temp;
	}
	
	public void insertReferido(Referido instance){
		dao.insertReferido(instance);
	}
	
	public void updateReferido(Referido instance){
		dao.updateReferido(instance);
	}
	
	public void deleteReferido(Referido instance){
		dao.deleteReferido(instance);
	}
	
}
