package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Usuarioweb;
import com.dromedicas.eis.TipotransaccionDao;

@Stateless
public class TipoTransaccionService {
	
	@EJB
	TipotransaccionDao dao;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	public List<Tipotransaccion> findAllTipotransaccions(){
		return dao.findAllTipotransaccions();
	}

	public Tipotransaccion obtenerTipotransaccionById(Tipotransaccion instance){
		return dao.obtenerTipotransaccionById(instance);
	}
	
	public Tipotransaccion obtenerTipoTransaccioById( int id ){
		Query query = em.createQuery("select t FROM Tipotransaccion t where t.idtipotransaccion = :id");
		query.setParameter("id", id);
		Tipotransaccion temp = null;		
		try {
			temp = (Tipotransaccion) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Usuario No encontrado");			
		}		
		return temp;
	}

	public void insertTipotransaccion(Tipotransaccion instance){
		dao.insertTipotransaccion(instance);
	}

	public void updateTipotransaccion(Tipotransaccion instance){
		dao.updateTipotransaccion(instance);
	}

	public void deleteTipotransaccion(Tipotransaccion instance){
		dao.deleteTipotransaccion(instance);
	}


}
