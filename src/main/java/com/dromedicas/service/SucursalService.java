package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Empresa;
import com.dromedicas.domain.Sucursal;
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

}
