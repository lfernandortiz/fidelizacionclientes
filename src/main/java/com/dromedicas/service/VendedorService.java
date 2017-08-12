package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Vendedor;
import com.dromedicas.eis.VendedorDao;

@Stateless
public class VendedorService {
	
	@EJB
	private VendedorDao daoVendedor;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	public List<Vendedor> findAllVendedors(){
		return this.daoVendedor.findAllVendedors();
	}

	public Vendedor obtenerVendedorById(Vendedor instance){
		return this.daoVendedor.obtenerVendedorById(instance);
	}

	public void insertVendedor(Vendedor instance){
		this.daoVendedor.insertVendedor(instance);
	}

	public void updateVendedor(Vendedor instance){
		this.daoVendedor.updateVendedor(instance);
	}

	public void deleteVendedor(Vendedor instance){
		this.daoVendedor.deleteVendedor(instance);
	}
	
	@SuppressWarnings("unchecked")
	public List<Vendedor> buscarVendedorPorCampo( String nombre, String dni){		
		
		String queryString = "SELECT v FROM Vendedor v WHERE 1=1 ";
		
		if( !"".equals(nombre) && nombre != null && !" ".equals(nombre) ){
			queryString += "and upper( concat( v.nombres, ' ', v.apellidos) )  like  upper('%"+ nombre.trim() + "%') "; 
		}
		if( !"".equals(dni) && dni != null && !" ".equals(dni)){
			queryString += "and v.docuvendedor  like '%"+ dni +"%'";
		}
		
		queryString += " order by v.nombres, v.apellidos";
		
		System.out.println("Query String: " + queryString);
		Query q = em.createQuery(queryString);
		return q.getResultList();
	}
	

}
