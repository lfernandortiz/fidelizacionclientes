package com.dromedicas.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Campania;
import com.dromedicas.domain.Smscampania;
import com.dromedicas.eis.SmscampaniaDao;

@Stateless
public class SmsCampaniaService {
	
	@PersistenceContext(name="PuntosFPU")
	EntityManager em;
	
	@EJB
	private SmscampaniaDao dao;
	
	
	public List<Smscampania> findAllSmscampanias() {
		return dao.findAllSmscampanias();
	}

	
	public Smscampania obtenerSmscampaniaById(Smscampania instance) {
		return dao.obtenerSmscampaniaById(instance);
	}

	
	public void insertSmscampania(Smscampania instance) {
		dao.insertSmscampania(instance);
	}

	
	public void updateSmscampania(Smscampania instance) {
		dao.updateSmscampania(instance);
	}

	
	public void deleteSmscampania(Smscampania instance) {
		dao.deleteSmscampania(instance);
	}
	
	/**
	 * Retorna las estadisticas de la campnia. Sms 
	 * rechazados y recibidos
	 * @param campania
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> obtenerStadisticasCampSMS( Campania campania ){
		
		
		System.out.println("----Id de campania: "+ campania.getIdcampania() );
		
		String queryString = "select 	sum(case when smsenvio.idestadosms = 0 then 1 else 0 end), "+
							"sum(case when smsenvio.idestadosms = 1 then 1 else 0 end) 	from " +
							"smscampania inner join smsenvio on (smscampania.idsmsenvio = smsenvio.idsmsenvio) "+
							"where smscampania.idcampania = " + campania.getIdcampania();

		System.out.println("QueryString: " + queryString );
		
		Query query = em.createNativeQuery(queryString);
		
		List<Object[]> result = null;
		try {
			result = query.getResultList();
			
		} catch (NoResultException e) {
			System.out.println("Error al obtener estadisticas de ampania SMS");
			e.printStackTrace();
		}
		return result;
	}

}
