package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Empresa;
import com.dromedicas.eis.ContratoDao;
import com.dromedicas.eis.EmpresaDao;


@Stateless
public class EmpresaService {
	
	@EJB
	private EmpresaDao empresaDao ;
	
	@EJB
	private ContratoDao contratoDao;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
		
	
	public List<Empresa> buscarEmpresas() {
		return empresaDao.findAllEmpresas();
	}

	
	public Empresa obtenerEmpresaById(Empresa instance) {
		return empresaDao.obtenerEmpresaById(instance);
	}
	
	public Empresa obtenerEmpresaById(String instance) {
		return empresaDao.obtenerEmpresaById(instance);
	}

	
	public void insertEmpresa(Empresa instance) {
		//persistencia en la capa JPA DaoImpl		
		empresaDao.insertEmpresa(instance);

	}

	
	public void updateEmpresa(Empresa instance) {
		empresaDao.updateEmpresa(instance);

	}

	
	public void deleteEmpresa(Empresa instance) {
		empresaDao.deleteEmpresa(instance);

	}	
	
	public void insertarContrato(Contrato instance){
		contratoDao.insertContrato(instance);
	}
	
	
	public void updateContrato(Contrato instance){
		contratoDao.updateContrato(instance);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Empresa> bucarEmpresaByFields(String nit, String nombre, String dir, String tel){
		System.out.println("nombre recibido: " + nombre);
		String queryString = "from Empresa p where 1=1 ";
		if( nit != null && !"".equals(nit) ){
			queryString += " and p.nit like '%" + nit.trim() + "%' ";
		}
		if( nombre != null && !"".equals(nombre)){
			queryString += " and p.nombreEmpresa like '%" + nombre.trim() + "%' ";
		}
		if( dir != null && !"".equals(dir) ){
			queryString += " and p.direccion like '%" + dir.trim() + "%' ";
		}
		if( tel != null && !"".equals(tel) ){
			queryString += " and p.telefono like '%" + tel.trim() + "%' ";
		}
		System.out.println("QueryString:" + queryString);
		Query query = em.createQuery(queryString);
		return query.getResultList();
	}
	
	
	public Empresa obtenerEmpresaPorNombre(Empresa instance){
		String queryString = "from Empresa p where p.nombreEmpresa = :nombre";
		Query query = em.createQuery(queryString);
		query.setParameter("nombre", instance.getNombreEmpresa().trim().toUpperCase());
		return (Empresa) query.getSingleResult();
	}
	
	public Empresa obtenerEmpresaPorNit(String nit){
		String queryString = "from Empresa p where p.nit = :nit";
		Query query = em.createQuery(queryString);
		query.setParameter("nit", nit);
		return (Empresa) query.getSingleResult();
	}
	
	
	public Contrato obtenerUltimoContrato(Empresa instance){	
		String queryString = "from Contrato c where current_date between c.fechainicio and c.fechafin "
				+ "and c.empresa.idempresa =  '" + instance.getIdempresa() +"'" ;
		Query query = em.createQuery(queryString);
		Contrato c = (Contrato) query.getSingleResult();		
		return c;
		
	}
	
	
}
