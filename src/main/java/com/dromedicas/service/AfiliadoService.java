package com.dromedicas.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
	
	
	public void crearAfiliado(Afiliado instance) {

		// persiste el afialiado
		updateAfiliado(instance);

		// Acumula los 100 puntos inciales del afiliado
		Afiliado afTemp = obtenerAfiliadoByDocumento(instance.getDocumento());

		int id = 4;
		Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(id);
		Transaccion tx = new Transaccion();
		tx.setAfiliado(afTemp);
		tx.setSucursal(instance.getSucursal());
		tx.setFechatransaccion(new Date());
		tx.setNrofactura("REGINI");
		tx.setValortotaltx(0);
		tx.setVencen(this.calculoService.addDays(new Date(), 365));
		tx.setTipotransaccion(tipoTx);
		tx.setPuntostransaccion(100);
		// graba los puntos iniciales
		txService.updateTransaccion(tx);

		// Se busca si el nuevo afiliado es un referido
		if (instance.getEmail() != null && !instance.getEmail().equals("")) {

			Referido ref = this.referidoService.obtenerReferidoPorEmail(instance.getEmail());

			// si el nuevo es un referido graba 100 puntos al afiliado que lo
			// refirio

			if (ref != null) {
				Afiliado afiReferente = ref.getAfiliado();

				int idTipo = 5;
				Tipotransaccion tipoTxRef = tipoTxService.obtenerTipoTransaccioById(idTipo);
				Transaccion txRef = new Transaccion();
				txRef.setAfiliado(afiReferente);
				txRef.setSucursal(instance.getSucursal());
				txRef.setFechatransaccion(new Date());
				txRef.setNrofactura("REGREF");
				txRef.setValortotaltx(0);
				txRef.setVencen(this.calculoService.addDays(new Date(), 365));
				txRef.setTipotransaccion(tipoTxRef);
				txRef.setPuntostransaccion(100);
				// graba los puntos iniciales
				txService.updateTransaccion(txRef);
			}
		} // end if validacion afiliado

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
