package com.dromedicas.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.BanlancePuntos;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Transaccion;


/**
 * EJB Encargado de todas las operacion de Puntos
 * para un afiliado.
 * @author SOFTDromedicas
 *
 */
@Stateless
public class OperacionPuntosService {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private TipoTransaccionService tipoTxService;
	
	@EJB
	private TransaccionService txService;
	
	@EJB
	private SucursalService sucursalService;
	
	//Transaccion de puntos
	
	
	/**
	 * 
	 * @param codsucursal
	 * @param momento
	 * @param nrofactura
	 * @param valortx
	 * @param documento
	 * @param puntos
	 * @return
	 */
	public void registrarTransaccion(Sucursal sucursal, String momento, String nrofactura, Integer valortx,
			Afiliado afiliado, int puntos) {

		BanlancePuntos balance = new BanlancePuntos();

		// se reciben parametros y se crean los objetos necesarios		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date momentotx = new Date();

		try {
			momentotx = sdf.parse(momento);

			System.out.println("Fecha Recibida: " + momento);

		} catch (ParseException e) {
			e.printStackTrace();
		} // end catch

		int idTipoTx = 1;
		Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(idTipoTx);
		Transaccion tx = new Transaccion();
		tx.setAfiliado(afiliado);
		tx.setSucursal(sucursal);
		tx.setFechatransaccion(momentotx);
		tx.setNrofactura(nrofactura);
		tx.setValortotaltx(valortx);
		Date fechavencimientopuntos = addDays(momentotx, 365);//-> Cambiar (365) por paramatreo optenico de consulta  
		tx.setVencen(fechavencimientopuntos);
		tx.setTipotransaccion(tipoTx);
		//Aca se debe traer el parametros por consulta de base de datos del factor de acumulacion 
		int mathPuntos = (valortx/100);	//-> Cambiar (100) por paramatreo optenico de consulta  
		System.out.println("----Puntos acumulados: "+ mathPuntos);
				
		tx.setPuntostransaccion(mathPuntos);
		// graba los puntos iniciales
		txService.updateTransaccion(tx);
	}
	
	
	/**
	 * Metodo que procesa la redencion de puntos, y acumula el saldo
	 * restante de la Tx
	 * @param sucursal
	 * @param momento
	 * @param nrofactura
	 * @param valortx
	 * @param afiliado
	 * @param puntosARedimir
	 */
	public void redencionPuntos(Sucursal sucursal, String momento, String nrofactura, Integer valortx,
			Afiliado afiliado, int puntosARedimir){
		//comienza lo bueno :-P
		
		BanlancePuntos balance = null;
		//obtengo todas las transaccion conpuntos redimibles
		List<Transaccion> txList = this.getListransaccionesARedimir(afiliado);
		
		//Aca proceso de redencion de puntos
		//Se iteran todas las tx disponibles a redimir y se acumulan los puntos
		// en la variable total mientras que se compara con los puntos a redimir
		// en caso de exceder los puntos en una Tx se deja el excedente disponible
		// en el campo saldo
		int total =0;		
		for(Transaccion tx:  txList){
			//
			if( tx.getRedimidos() == 1 ){				
				total += tx.getSaldo();
			}else{
				total += tx.getPuntostransaccion();
			}					
			
			if( total <= puntosARedimir ){
				byte r = 1;
				if( tx.getRedimidos() == 1 ){
					tx.setRedimidos(r);
					tx.setSaldo(0);
					this.txService.updateTransaccion(tx);					
				}else{
					tx.setRedimidos(r);
					this.txService.updateTransaccion(tx);
				}	
			}else{
				int dif = total - puntosARedimir;
				byte r = 1;
				tx.setRedimidos(r);
				tx.setSaldo(dif);
				this.txService.updateTransaccion(tx);
				break;// cuando se llega al limite se interrumpe la iteracion
			}			
		}		
		//Graba la Transaccion de redencion
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date momentotx = new Date();

		try {
			momentotx = sdf.parse(momento);
			
			System.out.println("Fecha Recibida Redencion: " + momento);

		} catch (ParseException e) {
			e.printStackTrace();
		} // end catch

		//se persiste la nueva transaccion de redencion
		int idTipoTx = 2;//tipo 2 es redencion
		Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(idTipoTx);
		Transaccion tx = new Transaccion();
		tx.setAfiliado(afiliado);
		tx.setSucursal(sucursal);
		tx.setFechatransaccion(momentotx);
		tx.setNrofactura(nrofactura);
		tx.setValortotaltx(valortx);
		tx.setTipotransaccion(tipoTx);		
		tx.setPuntostransaccion(puntosARedimir);
		// graba la Tx de redencion
		txService.updateTransaccion(tx);
		
		//Proceso de acumulacion de puntos del saldo restante entre el total de la 
		//factura y los pesos redimidos en puntos
		int nuevoValorTx =  valortx - puntosARedimir;
		int mathPuntos = (nuevoValorTx/100);//-> Cambiar (100) por paramatreo optenico de consulta
		
		// llamada al metodo registrarTransaccion con el nuevo valor a acumular
		this.registrarTransaccion(sucursal, momento, nrofactura, nuevoValorTx, afiliado, mathPuntos);
		
	} 
	
	
	
	
	public void devolucionTx(Sucursal sucursal, String momento, String nrofactura, Integer valortx, Afiliado afiliado) {

		// Graba la Transaccion de redencion
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date momentotx = new Date();

		try {
			momentotx = sdf.parse(momento);

			System.out.println("Fecha Recibida Devolucion: " + momento);

		} catch (ParseException e) {
			e.printStackTrace();
		} // end catch

		int idTipoTx = 3; // tipo 3 es devolucion
		Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(idTipoTx);
		Transaccion tx = new Transaccion();
		tx.setAfiliado(afiliado);
		tx.setSucursal(sucursal);
		tx.setFechatransaccion(momentotx);
		tx.setNrofactura(nrofactura);
		tx.setValortotaltx(valortx);
		tx.setTipotransaccion(tipoTx);
		int mathPuntos = (valortx/100);//-> Cambiar (100) por paramatreo optenico de consulta
		tx.setPuntostransaccion(mathPuntos);
		// graba la Tx de redencion
		txService.updateTransaccion(tx);

		
	}
	
	
	public BanlancePuntos consultaPuntos( Afiliado afiliado){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		BanlancePuntos balance = new BanlancePuntos();
		balance.setAcumulados( this.getPuntosAcumulados(afiliado) );
		balance.setRedimidos( this.getPuntosRedemidos(afiliado));		
		balance.setVencidos(this.getPuntosVencidos(afiliado));		
		balance.setAvencer(this.getPuntosAVencer(afiliado));
		balance.setFechavencimiento(sdf.format(this.getFechaVencimiento(afiliado)));
		balance.setDisponiblesaredimir(this.getPuntosDisponibles(afiliado));
		
		return balance;
	}
	
	
	
	//Metodos auxiliares de balance
		
	/**
	 * Suma dias a una objeto Date
	 * @param date
	 * @param days
	 * @return
	 */
	public Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); 
        return cal.getTime();
    }
	
	
	
	private Integer getPuntosAcumulados(Afiliado instance){
		String queryString = "SELECT sum(t.puntostransaccion)  FROM Transaccion t WHERE t.afiliado.documento = :documento "
				+ " and t.tipotransaccion.idtipotransaccion <> 2  and t.tipotransaccion.idtipotransaccion <> 3";  
		
		Query query = em.createQuery( queryString );
		System.out.println("Documento Instance: " + instance.getDocumento());
		query.setParameter("documento", instance.getDocumento());
		Long puntos = 0L;		
		try {
			puntos =  (Long) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Puntos acumulados no encontrados...");
			return puntos.intValue();			
		}		
		return puntos != null ? puntos.intValue() : 0;
	}
	
	
	
	private int getPuntosRedemidos(Afiliado instance){
		String queryString = "SELECT sum(t.puntostransaccion) FROM Transaccion t WHERE t.afiliado.documento = :documento "
				+ "and t.tipotransaccion.idtipotransaccion = 2 or t.tipotransaccion.idtipotransaccion = 3";
		Query query = em.createQuery( queryString );
		query.setParameter("documento", instance.getDocumento());
		Long puntos = 0L;		
		try {
			puntos =  (Long) query.getSingleResult();
		} catch (Exception e) {			
			System.out.println("Puntos acumulados no encontrados...");
			return puntos.intValue();			
		}		
		return puntos != null ? puntos.intValue() : 0;
	}
	
	
	
	private int getPuntosVencidos(Afiliado instance){
		String queryStringA = "select sum(t.puntostransaccion) from transaccion t " +
								"where t.tipotx != 2  and " +
								"t.tipotx != 3 and " +
								"t.vencen < CURRENT_DATE  and t.redimidos = 0 and " +
								"t.idafiliado = " + instance.getIdafiliado();		
		
		String queryStringB = "select sum(t.saldo) from Transaccion t " +
				"where t.tipotx <> 2  and " +
				"t.tipotx <> 3 and " +
				"t.vencen < CURRENT_DATE  and t.redimidos = 1 and " +
				"t.idafiliado = " + instance.getIdafiliado();
				
		Query queryA = em.createNativeQuery( queryStringA );
		Query queryB = em.createNativeQuery( queryStringB );
		
		BigDecimal puntosA = new BigDecimal(0);	
		BigDecimal puntosB = new BigDecimal(0);	
		try {
			//System.out.println("---------Query: " + queryA.unwrap(org.hibernate.Query.class).getQueryString());
			
			puntosA =    (BigDecimal) queryA.getSingleResult();
		} catch (Exception e) {			
			System.out.println("Puntos vencidos no encontrados...");
			e.printStackTrace();
		}	
		try {
			puntosB =  (BigDecimal) queryB.getSingleResult();
		} catch (Exception e) {			
			System.out.println("Puntos vencidos Saldo no encontrados...");
			e.printStackTrace();
		}		
		BigDecimal total = new BigDecimal(0);
		if( puntosA != null){
			total = puntosA;
		}
		if( puntosB != null){
			total = total.add(puntosB);
		}
		return total.intValue();
		
	}

	
	
	private int getPuntosAVencer(Afiliado instance) {
		
		String queryStringA = "select sum(t.puntostransaccion) from transaccion t "
				+ "where t.tipotx <> 2  and t.tipotx <> 3 and " 
				+ "t.vencen >=  CURRENT_DATE  and "
				+ "t.vencen <= DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY) and t.redimidos = 0 and "
				+ "t.idafiliado = " + instance.getIdafiliado();

		String queryStringB = "select sum(t.saldo) from transaccion t " + "where t.tipotx <> 2  and "
				+ "t.tipotx <> 3 and "
				+ "t.vencen >=  CURRENT_DATE and  t.vencen <= DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY) and "
				+ "t.redimidos = 1 and t.idafiliado = " + instance.getIdafiliado();

		Query queryA = em.createNativeQuery(queryStringA);
		Query queryB = em.createNativeQuery(queryStringB);
		
		BigDecimal puntosA = new BigDecimal(0);	
		BigDecimal puntosB = new BigDecimal(0);	
		try {
			puntosA = (BigDecimal) queryA.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Puntos A vencer no encontrados...");
		}
		try {
			puntosB = (BigDecimal) queryB.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Puntos A vencer no encontrados...");
		}
		
		BigDecimal total = new BigDecimal(0);
		if( puntosA != null){
			total = puntosA;
		}
		if( puntosB != null){
			total = total.add(puntosB);
		}
		return total.intValue();		
	}
	
	
	
	private int getPuntosDisponibles(Afiliado instance) {
		String queryStringA = "select sum(t.puntostransaccion) from transaccion t "
				+ "where t.tipotx <> 2  and t.tipotx <> 3 and " 
				+ "t.vencen >= CURRENT_DATE  and t.redimidos = 0 and "
				+ "t.idafiliado = " + instance.getIdafiliado();

		String queryStringB = "select sum(t.saldo) from transaccion t " 
				+ "where t.tipotx <> 2  and t.tipotx <> 3 and "
				+ "t.vencen >= CURRENT_DATE  and t.redimidos = 1 and " + "t.idafiliado = " + instance.getIdafiliado();
		
		String queryStringC = "select sum(t.puntostransaccion) from transaccion t " 
				+ "where t.tipotx = 3 and "
				+ "t.idafiliado = " + instance.getIdafiliado();

		Query queryA = em.createNativeQuery(queryStringA);
		Query queryB = em.createNativeQuery(queryStringB);
		Query queryC = em.createNativeQuery(queryStringC);
		
		BigDecimal puntosA = new BigDecimal(0);	
		BigDecimal puntosB = new BigDecimal(0);	
		BigDecimal puntosC = new BigDecimal(0);	
		try {
			puntosA = (BigDecimal) queryA.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Puntos vencidos no encontrados...");
		}
		try {
			puntosB = (BigDecimal) queryB.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Puntos vencidos Saldo no encontrados...");
		}
		try {
			puntosC = (BigDecimal) queryC.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Puntos vencidos Devolucion encontrados...");
		}
		
		BigDecimal total = new BigDecimal(0);
		if( puntosA != null){
			total = puntosA;
		}
		if( puntosB != null){
			total = total.add(puntosB);
		}
		if( puntosC != null){
			total =  total.subtract(puntosC);
		}
		//-> Cambiar (8000) por paramatreo optenico de consulta  
		return total.intValue() >= 8000 ? total.intValue() : 0; 
	}
	
	
	
	private Date getFechaVencimiento(Afiliado instance){
		String queryString = "SELECT MIN(t.vencen) FROM Transaccion t WHERE t.afiliado.documento = :documento "
				+ "and t.vencen >= CURRENT_DATE and t.redimidos <> 1";
		Query query = em.createQuery( queryString );
		query.setParameter("documento", instance.getDocumento());
		Date date = null;		
		try {
			date =  (Date) query.getSingleResult();
		} catch (NoResultException e) {			
			System.out.println("Puntos acumulados no encontrados...");			
		}		
		return date; 
	}
	
	
	private List<Transaccion> getListransaccionesARedimir(Afiliado instance){
		//Se buscan todas la Tx's con puntos para redimir, incluyendo saldos de Tx anteriores
		//que aun esta vigentes
		String queryString = "from Transaccion t " +
				 "where (t.tipotransaccion.idtipotransaccion <> 2  and t.tipotransaccion.idtipotransaccion <> 3 and " +
				 "t.vencen >= CURRENT_DATE  and t.redimidos = 0 and "+
				 "t.afiliado.idafiliado = :idAf1 ) OR" +
				 "( t.tipotransaccion.idtipotransaccion <> 2  and t.tipotransaccion.idtipotransaccion <> 3 and " +
				 "t.vencen >= CURRENT_DATE  and "+
				 "t.redimidos = 1 and t.afiliado.idafiliado = :idAf2 ) order by t.vencen asc";
		
		Query query = em.createQuery( queryString );		
		query.setParameter("idAf1", instance.getIdafiliado());
		query.setParameter("idAf2", instance.getIdafiliado());		
		List<Transaccion> txList = null;	
		try {			
			txList =   query.getResultList();
		} catch (NoResultException e) {
			System.out.println("sin pupntos para redimir");
		}		
		return txList;
	}
	
	
	
	private Transaccion obtenerFacturaDevolucion(String nroFactura) {
		String queryString ="FROM Transaccion t WHERE t.nrofactura = :nroFac and t.redimidos = 0 and t.redimidos <> 1";
		Query query = em.createQuery(queryString);
		query.setParameter("nroFac", nroFactura);
		Transaccion temp = null;		
		try {
			temp = (Transaccion) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Factura no encontrada");			
		}		
		return temp;
	}
	

}
