package com.dromedicas.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Afiliadopatologia;
import com.dromedicas.domain.Emailenvio;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.eis.AfiliadoPatologiaDao;

@Stateless
public class AfiliadoPatologiaService implements Serializable {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private AfiliadoPatologiaDao dao;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Afiliadopatologia> findAllAfiliadopatologias(){
		return dao.findAllAfiliadopatologias();
	}
	
	public Afiliadopatologia obtenerAfiliadopatologiaById(Afiliadopatologia instance){
		return dao.obtenerAfiliadopatologiaById(instance);
	}
	
	public void insertAfiliadopatologia(Afiliadopatologia instance){
		dao.insertAfiliadopatologia(instance);
	}
	
	public void updateAfiliadopatologia(Afiliadopatologia instance){
		dao.updateAfiliadopatologia(instance);
	}
	
	public void deleteAfiliadopatologia(Afiliadopatologia instance){
		dao.deleteAfiliadopatologia(instance);
	}
	
	public void eliminarPatologiaDeAfilaido(Afiliado afiliado){
		System.out.println("Eliminando Patologias...");
		Query query = em.createQuery("delete from Afiliadopatologia afp where afp.afiliado.idafiliado = :id");		
		query.setParameter("id", afiliado.getIdafiliado());
		int temp = 0;
		try {
			temp =  query.executeUpdate();
		} catch (NoResultException e) {
			System.out.println("Error al eliminar las patologias");			
		}		
	}
	
	
	public List<Integer> obtenerPatologiasPorAfiliado(Afiliado afiliado){
		Query query = em.createQuery("SELECT pt.patologia.idpatologia FROM Afiliadopatologia pt WHERE pt.afiliado.documento = :doc");
		query.setParameter("doc", afiliado.getDocumento());
		
		List<Integer> temp = null;		
		try {
			temp = query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");
			
		}		
		return temp;
	}

}
