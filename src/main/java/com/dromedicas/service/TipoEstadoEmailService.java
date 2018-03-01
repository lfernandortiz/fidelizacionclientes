package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Tipoestaestadoemail;
import com.dromedicas.eis.TipoestaestadoemailDao;

@Stateless
public class TipoEstadoEmailService {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;
	
	@EJB
	private TipoestaestadoemailDao dao;


	public List<Tipoestaestadoemail> findAllTipoestaestadoemails() {
		return dao.findAllTipoestaestadoemails();
	}

	
	public Tipoestaestadoemail obtenerTipoestaestadoemailById(Tipoestaestadoemail instance) {
		return dao.obtenerTipoestaestadoemailById(instance);
	}

	
	public void insertTipoestaestadoemail(Tipoestaestadoemail instance) {
		dao.insertTipoestaestadoemail(instance);

	}

	
	public void updateTipoestaestadoemail(Tipoestaestadoemail instance) {
		dao.updateTipoestaestadoemail(instance);

	}

	
	public void deleteTipoestaestadoemail(Tipoestaestadoemail instance) {
		dao.deleteTipoestaestadoemail(instance);
	}
	
	
	public Tipoestaestadoemail obtenerTipoEstadoPorDescripcion(String descripcion){
		Query query = em.createQuery("FROM Tipoestaestadoemail t WHERE lower(t.descripcion) "
				+ "like lower(concat('%', :descripcion, '%'))");
		query.setParameter("descripcion", descripcion);
		Tipoestaestadoemail temp = null;		
		try {
			temp = (Tipoestaestadoemail) query.getSingleResult();
			
		} catch (Exception e) {
			System.out.println("Elemento no encontrado");
			throw new NoResultException("No hay ningun tipo de email encontradco");
		} 	
		return temp;
	}

}
