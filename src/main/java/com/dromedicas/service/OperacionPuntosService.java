package com.dromedicas.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.dromedicas.domain.BalancePuntos;
import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Empresa;
import com.dromedicas.domain.Referido;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.mailservice.EnviarEmailAlertas;


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
	
	@EJB
	private EnviarEmailAlertas mailAlert;
	
	@EJB
	private ReferidoService referidoService;
	
	@EJB
	private EmpresaService empresaService;
	
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
	public int registrarTransaccion(Sucursal sucursal, String momento, String nrofactura, Integer valortx,
			Afiliado afiliado) {

		BalancePuntos balance = new BalancePuntos();
		
		int acumuladosTxActual = 0;

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
		tx.setEnvionotificacion((byte)0);
		//Aca se debe traer el parametros por consulta de base de datos del factor de acumulacion 
		Contrato contrato =  empresaService.obtenerUltimoContrato(sucursal.getEmpresa());
		int baseAc = contrato.getBasegravable();
		
		int mathPuntos = (valortx / baseAc);	//-> Cambiar (100) por paramatreo optenico de consulta  
		System.out.println("----Puntos acumulados: "+ mathPuntos);
			
		acumuladosTxActual = mathPuntos;
		tx.setPuntostransaccion(mathPuntos);
		// graba los puntos iniciales
		txService.updateTransaccion(tx);
		
		
		return acumuladosTxActual;
		//se deja el envio de correo de tx a un scheduling
		//mailAlert.emailAcumulacionPuntos(afiliado, mathPuntos, this.consultaPuntos(afiliado));
		
		
	}
	
	/**
	 * Recibe un List de transacciones y los persiste en la base
	 * @param txList
	 */
	public void registrarListTransacciones(List<Transaccion> txList) {		
		try {
			for(Transaccion tx: txList){
				txService.updateTransaccion(tx);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public int redencionPuntos(Sucursal sucursal, String momento, String nrofactura, Integer valortx,
			Afiliado afiliado, int puntosARedimir){
		
		BalancePuntos balance = null;
		
		//obtengo todas las transaccion conpuntos redimibles
		List<Transaccion> txList = this.getListransaccionesARedimir(afiliado);
		
		//Aca es el proceso de redencion de puntos
		//Se iteran todas las tx disponibles a redimir y se acumulan los puntos
		// en la variable "total" mientras que se compara con los puntos a redimir
		// en caso de exceder los puntos en una Tx se deja el excedente disponible
		// en el campo "saldo"
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
		tx.setEnvionotificacion((byte)0);
		// graba la Tx de redencion
		txService.updateTransaccion(tx);
		
		//Proceso de acumulacion de puntos del saldo restante entre el total de la 
		//factura y los pesos redimidos en puntos
		int nuevoValorTx =  valortx - puntosARedimir;
		
		// llamada al metodo registrarTransaccion con el nuevo valor a acumular
		int pTxActual =  this.registrarTransaccion(sucursal, momento, nrofactura, nuevoValorTx, afiliado);
		
		//enviar email de auditoria
		this.mailAlert.notificacionRedencion(sucursal, momento, nrofactura, valortx, afiliado, puntosARedimir);
		
		return pTxActual;
		
	} 
	
	
	
	/***
	 * Metodo que procesa la devolucion de una compra.
	 * @param sucursal
	 * @param momento
	 * @param nrofactura
	 * @param valortx
	 * @param afiliado
	 * @return
	 */
	public int devolucionTx(Sucursal sucursal, String momento, String nrofactura, Integer valortx, Afiliado afiliado) {

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
		
		Contrato contrato =  empresaService.obtenerUltimoContrato(sucursal.getEmpresa());
		int baseAc = contrato.getBasegravable();
		
		int mathPuntos = (valortx/baseAc);//-> Cambiar (100) por paramatreo optenico de consulta
		System.out.println("----Puntos descontados: "+ mathPuntos);
		tx.setPuntostransaccion(mathPuntos);
		tx.setEnvionotificacion((byte)0);
		// graba la Tx de redencion
		txService.updateTransaccion(tx);
		
		return mathPuntos;
	}
	
	
	public void puntosInicialesRegistro(Afiliado instance) {
		// Acumula los 100 puntos inciales del afiliado
		Afiliado afTemp = afiliadoService.obtenerAfiliadoNacionalidad(instance);

		int id = 4;
		Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(id);
		Transaccion tx = new Transaccion();
		tx.setAfiliado(afTemp);
		tx.setSucursal(instance.getSucursal());
		tx.setFechatransaccion(new Date());
		tx.setNrofactura("REGINI");
		tx.setValortotaltx(0);
		tx.setVencen(addDays(new Date(), 365));
		tx.setTipotransaccion(tipoTx);
		tx.setPuntostransaccion(100); //-> Cambiar (100) por paramatreo optenico de consulta este es un nuevo parametro x crear
		tx.setEnvionotificacion((byte)0);
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
				txRef.setVencen(addDays(new Date(), 365));
				txRef.setTipotransaccion(tipoTxRef);
				txRef.setPuntostransaccion(100);
				txRef.setEnvionotificacion((byte)0);
				// graba los puntos iniciales
				txService.updateTransaccion(txRef);
			}
		} // end if validacion afiliado

	}
	
	/**
	 * Realiza una consulta del balance de puntos del afiliado.
	 * Retorna un objeto <code>BalancePuntos</BalancePuntos>.
	 * @param afiliado
	 * @return
	 */
	public BalancePuntos consultaPuntos( Afiliado afiliado){
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		BalancePuntos balance = new BalancePuntos();
		balance.setAcumulados( this.getPuntosAcumulados(afiliado) );
		balance.setRedimidos( this.getPuntosRedemidos(afiliado));		
		balance.setVencidos(this.getPuntosVencidos(afiliado));		
		balance.setAvencer(this.getPuntosAVencer(afiliado));
		Date tempDate = this.getFechaVencimiento(afiliado);
		balance.setFechavencimiento(tempDate != null ? sdf.format(tempDate) : "" );
		balance.setDisponiblesaredimir(this.getPuntosDisponibles(afiliado));
		balance.setTotalpuntosactual(this.getPuntosAcumuladosNoDisponibles(afiliado)); 
		
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
				+ "and (t.tipotransaccion.idtipotransaccion = 2 or t.tipotransaccion.idtipotransaccion = 3)";
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
	
	
	/**
	 * Obtiene los puntos disponibles para redimir del afiliado
	 * Tipo tx 2 = redencion, Tipo tx 3 = devolucion
	 * @param instance
	 * @return
	 */	
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
		//para obtener el valor minimo redimible del afiliado se obtiene:
		//La Sucursal donde fue vinculado -> La Empresa -> El contrato 
		//que es la entidad que tienen los parametros de redencion		
		Contrato contrato =  
				empresaService.obtenerUltimoContrato(instance.getSucursal().getEmpresa());
		
		int vrMinimoRedimible = contrato.getVrminimoredimir();
				
		return total.intValue() >= vrMinimoRedimible ? total.intValue() : 0; 
	}
	
	/**
	 * Acumulados no disponibles son aquellos puntos acumulados, que no suman
	 * el minimo redimible.
	 * @param instance
	 * @return
	 */                         
	private int getPuntosAcumuladosNoDisponibles(Afiliado instance) {
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
		
		return total.intValue() ; 
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
		String queryString ="FROM Transaccion t WHERE t.nrofactura = :nroFac "
				+ " and t.redimidos = 0 and t.redimidos <> 1";
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
	
	
	// ===========================================
    // = Metodos de informacion global de puntos =
    // ===========================================
	
	/**
	 * Retorna el total de puntos acumulados disponibles por 
	 * redimir en toda la plataforma
	 * @return
	 */
	@SuppressWarnings("unused")
	public int getTotalPuntosDisponibles() {
		
		String queryString = 
			"SELECT sum( x.totalpuntos) FROM ( select afiliado.documento, (sum(general.totalpuntos) + sum(general.totalsaldo) - sum(general.devolucion)) as totalpuntos "+
			"from (	select 	t.idafiliado as id, sum(t.puntostransaccion) as totalpuntos, 0 as totalsaldo,  0 as devolucion from  transaccion t " +
			"where t.tipotx <> 2 and t.tipotx <> 3 and  t.vencen >= current_date()  and  t.redimidos = 0 group by t.idafiliado " +
			"union all select t.idafiliado as id, 0 as totalpuntos, sum(t.saldo) as totalsaldo,  0 as devolucion from  transaccion t " +  
			"where t.tipotx <> 2 and t.tipotx <> 3 and t.vencen >= CURRENT_DATE and t.redimidos = 1 group by t.idafiliado " +
			"union all select  t.idafiliado as id, 0 as totalpuntos, 0 as totalsaldo, sum(t.puntostransaccion) as devolucion " +
			"from transaccion t  where t.tipotx = 3  group by t.idafiliado) as general INNER JOIN afiliado on (afiliado.idafiliado = general.id) "+
			"group by 1 having totalpuntos >= 8000 ) AS x ";
		
		Query query = em.createNativeQuery(queryString);
		BigDecimal total = new BigDecimal(0);	
		try {
			total = (BigDecimal) query.getSingleResult();
			System.out.println("Total disponible a redimir: " +  total);
		} catch (NoResultException e) {
			System.out.println("Total puntos no calculados");
		}
		return total.intValue();
	}
	
	
	/**
	 * Retorna el total de puntos redimidos en toda la plataforma
	 * @return
	 */
	public int getTotalPuntosRedemidos(){
		String queryString = "SELECT sum(t.puntostransaccion) FROM Transaccion t WHERE "
				+ "t.tipotransaccion.idtipotransaccion = 2";
		Query query = em.createQuery( queryString );			
		Long puntos = 0L;		
		try {
			puntos =  (Long) query.getSingleResult();
		} catch (Exception e) {			
			System.out.println("Total de puntos redimidos...");
			return puntos.intValue();			
		}		
		return puntos != null ? puntos.intValue() : 0;
	}
	
	
	/**
	 * Retorna el total de puntos acumulados en la plataforma
	 * puntos farmanorte
	 * @return
	 */
	public int getTotalPuntosAcumulados(){
		String queryStringA = "select sum(t.puntostransaccion) from transaccion t "
				+ "where t.tipotx <> 2  and t.tipotx <> 3 and " 
				+ "t.vencen >= CURRENT_DATE  and t.redimidos = 0  ";

		String queryStringB = "select sum(t.saldo) from transaccion t " 
				+ "where t.tipotx <> 2  and t.tipotx <> 3 and "
				+ "t.vencen >= CURRENT_DATE  and t.redimidos = 1  ";
		
		String queryStringC = "select sum(t.puntostransaccion) from transaccion t " 
				+ "where t.tipotx = 3 ";

		Query queryA = em.createNativeQuery(queryStringA);
		Query queryB = em.createNativeQuery(queryStringB);
		Query queryC = em.createNativeQuery(queryStringC);
		
		BigDecimal puntosA = new BigDecimal(0);	
		BigDecimal puntosB = new BigDecimal(0);	
		BigDecimal puntosC = new BigDecimal(0);	
		try {
			puntosA = (BigDecimal) queryA.getSingleResult();
		} catch (NoResultException e) {
			
		}
		try {
			puntosB = (BigDecimal) queryB.getSingleResult();
		} catch (NoResultException e) {
			
		}
		try {
			puntosC = (BigDecimal) queryC.getSingleResult();
		} catch (NoResultException e) {
			
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
		
		return total.intValue() ;
	}
	
	

	
	
	
	
	
	

}
