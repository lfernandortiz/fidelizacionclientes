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
	
	public Usuarioweb obtenerUsuariowebById(int id){
		return dao.obtenerUsuariowebById(id);
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
		System.out.println("Nombre recivido: "  + nombre);
		String queryString = "SELECT u FROM Usuarioweb u WHERE u.usuario like CONCAT('%', :user, '%') or "
				+ "u.nombreusuario like  CONCAT('%', :username, '%') order by u.nombreusuario";
		Query query = em.createQuery(queryString);
		query.setParameter("user", nombre);
		query.setParameter("username", nombre);
		
		System.out.println("Query String: " + query.unwrap(org.hibernate.Query.class).getQueryString() );
		
		return query.getResultList();
	}

}
