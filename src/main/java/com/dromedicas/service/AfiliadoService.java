package com.dromedicas.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Referido;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.eis.AfiliadoDao;
import com.dromedicas.mailservice.EnviarEmailAlertas;

@Stateless
public class AfiliadoService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private EnviarEmailAlertas mailAlert;
	
	@EJB
	private TipoTransaccionService tipoTxService;
	
	@EJB
	private TransaccionService txService;
	
	@EJB	
	private AfiliadoDao afiliadoDao;
	
	@EJB
	private OperacionPuntosService calculoService;
	
	@EJB
	private ReferidoService referidoService;
	
	public List<Afiliado> findAllAfiliados(){
		return this.afiliadoDao.findAllAfiliados();
	}
	
	public Afiliado obtenerAfiliadoById(Afiliado instance){
		return this.afiliadoDao.obtenerAfiliadoById(instance);
	}
	
	public Afiliado obtenerAfiliadoByDocumento(Afiliado instance){
		return this.afiliadoDao.obtenerAfiliadoByDocumento(instance);
	}
	
	public Afiliado obtenerAfiliadoByDocumento(String documento){
		return this.afiliadoDao.obtenerAfiliadoByDocumento(documento);
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
			" OR upper( concat('%', replace(a.nombres,' ','%'), '%', replace(a.apellidos,' ','%')) ) like  upper('%" + 
			criterio.trim().replace(" ", "%") + "%') " +
			" OR upper( concat('%', replace(a.apellidos,' ','%'), '%', replace(a.nombres,' ','%')) ) like  upper('%"+ 
			criterio.trim().replace(" ", "%") + "%') " +
			" OR a.email like '%" + criterio.trim() + "%' " +
			" ORDER BY a.nombres, a.apellidos";
			
		System.out.println("QueryString:" + queryString);
		Query query = em.createQuery(queryString);
		return query.getResultList();
	}
	
	
	public Afiliado obtenerAfiliadoNacionalidad(Afiliado instance) {
		Query query = em.createQuery("FROM Afiliado a WHERE a.documento = :docu and a.nacionalidad = :nacionalidad");
		query.setParameter("docu", instance.getDocumento());
		query.setParameter("nacionalidad", instance.getNacionalidad());
		
		Afiliado temp = null;		
		try {
			temp = (Afiliado) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");
			
		}		
		return temp;
	}
	
	public Afiliado obtenerAfiliadoDocumentoNacionalidad(String documento, String nacionalidad) {
		System.out.println(">>>"+ documento +">>" + nacionalidad);
		Query query = em.createQuery("FROM Afiliado a WHERE a.documento = :docu and a.nacionalidad = :nacionalidad");
		query.setParameter("docu", documento);
		query.setParameter("nacionalidad", nacionalidad);
		
		Afiliado temp = null;		
		try {
			temp = (Afiliado) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");
			
		}		
		return temp;
	}
	
	public Afiliado obtenerAfiliadoUUID(String uuid){
		Query query = em.createQuery("FROM Afiliado a WHERE a.keycode = :uuid");
		query.setParameter("uuid", uuid);
		Afiliado temp = null;		
		try {
			temp = (Afiliado) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");
			
		}		
		return temp;
		
	}
	
	
	public void crearAfiliado(Afiliado instance) {

		// persiste el afialiado
		updateAfiliado(instance);
		
		//ser cargan los puntos iniciales del afiliado
		this.calculoService.puntosInicialesRegistro(instance);
		
		// 5 Envia correo de notificacion de afiliacion
		boolean enviado = false;
		if (instance.getEmail() != null && !instance.getEmail().equals("")) {
			enviado = mailAlert.enviarEmailAlertaVentas(instance);
		}

		if (enviado) {
			// -- Registro del Email para tracking
		}
	}
	
	
	public void actualizarAfiliado(Afiliado instance){
		updateAfiliado(instance);
		
	}
	
	
	
	
	
}
