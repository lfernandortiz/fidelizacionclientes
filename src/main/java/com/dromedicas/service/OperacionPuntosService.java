package com.dromedicas.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	public BanlancePuntos registrarTransaccion(Sucursal sucursal, String momento, String nrofactura, Integer valortx,
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
		Date fechavencimientopuntos = addDays(momentotx, 365);
		tx.setVencen(fechavencimientopuntos);
		tx.setTipotransaccion(tipoTx);
		//Aca se debe traer el parametros por 
		tx.setPuntostransaccion(puntos);
		// graba los puntos iniciales
		txService.updateTransaccion(tx);

		// Aca se obtiene el balance
		balance.setAcumulados( this.getPuntosAcumulados(afiliado) );
		balance.setRedimidos( this.getPuntosRedemidos(afiliado));		
		balance.setVencidos(this.getPuntosVencidos(afiliado));
		balance.setAvencer(this.getPuntosAVencer(afiliado));
		balance.setFechavencimiento(sdf.format(this.getFechaVencimiento(afiliado)));
		balance.setDisponibles(this.getPuntosDisponibles(afiliado));

		return balance;
	}
	
	
	public BanlancePuntos redencionPuntos(){
		return null;
	} 
	
	public BanlancePuntos consultaPuntos( Afiliado afiliado){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		BanlancePuntos balance = new BanlancePuntos();
		balance.setAcumulados( this.getPuntosAcumulados(afiliado) );
		balance.setRedimidos( this.getPuntosRedemidos(afiliado));		
		balance.setVencidos(this.getPuntosVencidos(afiliado));
		balance.setAvencer(this.getPuntosAVencer(afiliado));
		balance.setFechavencimiento(sdf.format(this.getFechaVencimiento(afiliado)));
		balance.setDisponibles(this.getPuntosDisponibles(afiliado));

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
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	
	
	private Integer getPuntosAcumulados(Afiliado instance){
		String queryString = "SELECT sum(t.puntostransaccion) FROM Transaccion t WHERE t.afiliado.documento = :documento "
				+ " and t.puntostransaccion > 0 ";
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
				+ "and t.puntostransaccion < 0 and t.tipotransaccion.idtipotransaccion = 2";
		Query query = em.createQuery( queryString );
		query.setParameter("documento", instance.getDocumento());
		Long puntos = 0L;		
		try {
			puntos =  (Long) query.getSingleResult();
		} catch (Exception e) {			
			System.out.println("Puntos acumulados no encontrados...");
			return puntos.intValue();			
		}		
		System.out.println("-------------||------ " + puntos );
		return puntos != null ? (puntos.intValue()*-1) : 0;
	}
	
	
	
	private int getPuntosVencidos(Afiliado instance){
		String queryStringA = "select sum(t.puntostransaccion) from transaccion t " +
								"where t.puntostransaccion > 0 and " +
								"t.vencen < CURRENT_DATE  and t.redimidos = 0 and " +
								"t.idafiliado = " + instance.getIdafiliado();		
		
		String queryStringB = "select sum(t.saldo) from transaccion t " +
				"where t.saldo > 0 and " +
				"t.vencen < CURRENT_DATE  and t.redimidos = 1 and " +
				"t.idafiliado = " + instance.getIdafiliado();
		
		Query queryA = em.createNativeQuery( queryStringA );
		Query queryB = em.createNativeQuery( queryStringB );
		
		BigDecimal puntosA = new BigDecimal(0);	
		BigDecimal puntosB = new BigDecimal(0);	
		try {
			puntosA =    (BigDecimal) queryA.getSingleResult();
		} catch (NoResultException e) {			
			System.out.println("Puntos vencidos no encontrados...");
		}	
		try {
			puntosB =  (BigDecimal) queryB.getSingleResult();
		} catch (NoResultException e) {			
			System.out.println("Puntos vencidos Saldo no encontrados...");
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
				+ "where t.puntostransaccion > 0 and " + "t.vencen >=  CURRENT_DATE  and "
				+ "t.vencen <= DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY) and t.redimidos = 0 and "
				+ "t.idafiliado = " + instance.getIdafiliado();

		String queryStringB = "select sum(t.saldo) from transaccion t " + "where t.saldo > 0 and "
				+ "t.vencen >=  CURRENT_DATE and  t.vencen <= DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY) and "
				+ "t.redimidos = 1 and " + "t.idafiliado = " + instance.getIdafiliado();

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
				+ "where t.puntostransaccion > 0 and " + "t.vencen >= CURRENT_DATE  and t.redimidos = 0 and "
				+ "t.idafiliado = " + instance.getIdafiliado();

		String queryStringB = "select sum(t.saldo) from transaccion t " + "where t.saldo > 0 and "
				+ "t.vencen >= CURRENT_DATE  and t.redimidos = 1 and " + "t.idafiliado = " + instance.getIdafiliado();

		Query queryA = em.createNativeQuery(queryStringA);
		Query queryB = em.createNativeQuery(queryStringB);

		BigDecimal puntosA = new BigDecimal(0);	
		BigDecimal puntosB = new BigDecimal(0);	
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
		
		BigDecimal total = new BigDecimal(0);
		if( puntosA != null){
			total = puntosA;
		}
		if( puntosB != null){
			total = total.add(puntosB);
		}
		return total.intValue() >= 3000 ? total.intValue() : 0; 
	}
	
	
	
	private Date getFechaVencimiento(Afiliado instance){
		String queryString = "SELECT MIN(t.vencen) FROM Transaccion t WHERE t.afiliado.documento = :documento "
				+ "and t.vencen >= CURRENT_DATE";
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
	
	

}
