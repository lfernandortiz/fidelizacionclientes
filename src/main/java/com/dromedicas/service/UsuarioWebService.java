package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Usuarioweb;
import com.dromedicas.eis.UsuariowebDao;

@Stateless
public class UsuarioWebService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private UsuariowebDao dao;
	
	public List<Usuarioweb> findAllUsuariowebs(){
		return dao.findAllUsuariowebs();
	}
	

	public Usuarioweb obtenerUsuariowebById(Usuarioweb instance){
		return dao.obtenerUsuariowebById(instance);
	}

	public void insertUsuarioweb(Usuarioweb instance){
		dao.insertUsuarioweb(instance);
	}

	public void updateUsuarioweb(Usuarioweb instance){
		dao.updateUsuarioweb(instance);
	}

	public void deleteUsuarioweb(Usuarioweb instance){
		dao.deleteUsuarioweb(instance);
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuarioweb> buscarUsuarioPorCampo( String nombre ){		
		
		String queryString = "SELECT u FROM Usuarioweb u WHERE 1=1 ";
		
		if( !"".equals(nombre) && nombre != null && !" ".equals(nombre) ){
			queryString += "and u.usuario like  '%"+ nombre.trim() + "%' or "
					+ " u.nombreusuario like  '%"+ nombre.trim() + "%'"; 
		}
		
		queryString += " order by u.nombreusuario";
		
		System.out.println("Query String: " + queryString);
		Query q = em.createQuery(queryString);
		return q.getResultList();
	}

}
