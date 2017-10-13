package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Empresa;
import com.dromedicas.eis.AfiliadoDao;

@Stateless
public class AfiliadoService {
	
	@EJB	
	private AfiliadoDao afiliadoDao;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	public List<Afiliado> findAllAfiliados(){
		return this.afiliadoDao.findAllAfiliados();
	}
	
	public Afiliado obtenerAfiliadoById(Afiliado instance){
		return this.afiliadoDao.obtenerAfiliadoById(instance);
	}
	
	public Afiliado obtenerAfiliadoByDocumento(Afiliado instance){
		return this.afiliadoDao.obtenerAfiliadoByDocumento(instance);
	}
	
	public void insertAfiliado(Afiliado instance){
		this.afiliadoDao.insertAfiliado(instance);
	}
	
	public void updateAfiliado(Afiliado instance){
		this.afiliadoDao.updateAfiliado(instance);
	}
	
	public void deleteAfiliado(Afiliado instance){
		this.afiliadoDao.deleteAfiliado(instance);
	}
	
	@SuppressWarnings("unchecked")
	public List<Afiliado> bucarAfiliadoByFields(String criterio){
		System.out.println("nombre recibido: " + criterio);
		String queryString = "from Afiliado a where  a.documento like '%" + criterio.trim() + "%' " +		
			" OR a.nombres like '%" + criterio.trim().toUpperCase() + "%' " +
			" OR a.apellidos like '%" + criterio.trim().toUpperCase() + "%' " +		
			" OR a.email like '%" + criterio.trim() + "%' " +
			" ORDER BY a.nombres, a.apellidos";
			
		System.out.println("QueryString:" + queryString);
		Query query = em.createQuery(queryString);
		return query.getResultList();
	}
	
}
