package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Tipoemail;
import com.dromedicas.eis.TipoemailDao;

@Stateless
public class TipoEmailService {
	
	@PersistenceContext(name="PuntoFPU")
	EntityManager em;
	
	@EJB
	private TipoemailDao tipoEmailDao;
	
	
	public List<Tipoemail> findAllTipoemails() {
		return this.tipoEmailDao.findAllTipoemails();
	}


	public Tipoemail obtenerTipoemailById(Tipoemail instance) {
		return this.tipoEmailDao.obtenerTipoemailById(instance);
	}

	
	public void insertTipoemail(Tipoemail instance) {
		this.tipoEmailDao.insertTipoemail(instance);

	}

	
	public void updateTipoemail(Tipoemail instance) {
		this.tipoEmailDao.updateTipoemail(instance);

	}

	
	public void deleteTipoemail(Tipoemail instance) {
		this.tipoEmailDao.deleteTipoemail(instance);
	}
	
	
	public Tipoemail obtenerTipoEmialPorDescripcion(String descripcion){
		Query query = em.createQuery("FROM Tipoemail t WHERE lower(t.descripcion) "
				+ "like lower(concat('%', :descripcion, '%'))");
		query.setParameter("descripcion", descripcion);
		Tipoemail temp = null;		
		try {
			temp = (Tipoemail) query.getSingleResult();
			
		} catch (Exception e) {
			System.out.println("Elemento no encontrado");
			throw new NoResultException("No hay ningun tipo de email encontradco");
		} 	
		return temp;
	}

}
