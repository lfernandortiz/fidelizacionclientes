package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.eis.SucursalDao;

@Stateless
public class SucursalService {
	
	@EJB
	private SucursalDao sucursalDao;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	public List<Sucursal> findAllSucursals(){
		return sucursalDao.findAllSucursals();
	}

	public Sucursal obtenerSucursalById(String id){
		return sucursalDao.obtenerSucursalById(id);
	}
	
	public Sucursal obtenerSucursalById(Sucursal instance){
		return sucursalDao.obtenerSucursalById(instance);
	}

	public void insertSucursal(Sucursal instance){
		sucursalDao.insertSucursal(instance);
	}

	public void updateSucursal(Sucursal instance){
		sucursalDao.updateSucursal(instance);
	}

	public void deleteSucursal(Sucursal instance){
		sucursalDao.deleteSucursal(instance);
	}
	
	@SuppressWarnings("unchecked")
	public List<Sucursal> bucarSucursalByFields(String sucursal, String nombreEmpresa){
		System.out.println("nombre recibido: " + sucursal);
		String queryString = "from Sucursal s where 1=1 ";
		if( sucursal != null && !"".equals(sucursal) ){
			queryString += " and s.nombreSucursal like '%" + sucursal.trim() + "%' ";
		}
		if( nombreEmpresa != null && !"".equals(nombreEmpresa)){
			queryString += " and s.empresa.nombreEmpresa like '%" + nombreEmpresa.trim() + "%' ";
		}
		
		System.out.println("QueryString:" + queryString);
		Query query = em.createQuery(queryString);
		return query.getResultList();
	}
	
	
	public Sucursal obtenerSucursalPorIdIterno( String id ){
		Query query = em.createQuery("select s FROM Sucursal s where s.codigointerno = :id");
		query.setParameter("id", id);
		Sucursal temp = null;		
		try {
			temp = (Sucursal) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Usuario No encontrado");			
		}		
		return temp;
	} 

}
