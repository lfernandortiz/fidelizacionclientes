package com.dromedicas.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Afiliado;
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
	
	@EJB
	private RegistroNotificacionesService registroNotificacion;
	
	
	public List<Afiliado> findAllAfiliados(){
		return this.afiliadoDao.findAllAfiliados();
	}
	
	public Afiliado obtenerAfiliadoById(Afiliado instance){
		return this.afiliadoDao.obtenerAfiliadoById(instance);
	}
	
	public Afiliado obtenerAfiliadoByDocumento(Afiliado instance){
		return this.afiliadoDao.obtenerAfiliadoByDocumento(instance);
	}
	

	// ======================================
    // =               CRUD                 =
    // ======================================
	
	public void insertAfiliado(Afiliado instance){
		this.afiliadoDao.insertAfiliado(instance);
	}
	
	public void updateAfiliado(Afiliado instance){
		this.afiliadoDao.updateAfiliado(instance);
	}
	
	public void deleteAfiliado(Afiliado instance){
		this.afiliadoDao.deleteAfiliado(instance);
	}
	
	public void actualizarAfiliado(Afiliado instance){
		updateAfiliado(instance);
	}
	
	public void crearAfiliado(Afiliado instance) {

		// persiste el afialiado
		updateAfiliado(instance);
		
		//ser cargan los puntos iniciales del afiliado
		this.calculoService.puntosInicialesRegistro(instance);
		
		// 5 Envia correo de notificacion de afiliacion
		String enviado = null;
		if (instance.getEmail() != null && !instance.getEmail().trim().equals("") ) {
			enviado = mailAlert.enviarEmailAlertaVentas(instance);
		}
		
		if (enviado != null) {
			//se graba el auditor del correo
			this.registroNotificacion.auditarEmailEnviado(instance, enviado, "Bienvenida al programa");
		}
	}
	
	
	// ======================================
    // =     Consultas Personalizadas       =
    // ======================================
	
	/**
	 * Retorna un list con todas las transacciones del afiliado
	 * @param afiliado
	 * @return
	 */
	public List<Transaccion> obtenerTodasTransacciones(Afiliado afiliado){
		Query query = em.createQuery("FROM Transaccion t  where t.afiliado.idafiliado = " + afiliado.getIdafiliado() + " order by fechatransaccion desc");
		//query.setMaxResults(500);
		List<Transaccion> temp = null;
		try {
			temp =  query.getResultList();
			
			System.out.println("----------TAMANIO TXS: " + temp.size());
			
		} catch (NoResultException e) {
			System.out.println("Usuario No encontrado");			
		}		
		return temp;
	}
	
	/**
	 * Retorna un List con las ultimas 
	 * @param afiliado
	 * @return
	 */
	public List<Transaccion> obtenerUltimasTransacciones(Afiliado afiliado){
		Query query = em.createQuery("FROM Transaccion t  where t.afiliado.idafiliado = " + afiliado.getIdafiliado() + " "
				+ " order by t.fechatransaccion DESC");
		query.setMaxResults(10);
		List<Transaccion> temp = null;
		try {
			temp =  query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Usuario No encontrado");			
		}		
		return temp;
	}
	
	
	/**
	 * Retorna los ultimos 500 afiliados registrados.
	 * Usada para vistas.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Afiliado> findAllAfiliadosMenor(){
		Query query = em.createQuery("SELECT a FROM Afiliado a  ORDER BY a.momento DESC");
		query.setMaxResults(500);
		List<Afiliado> temp = null;
		try {
			temp =  query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Usuario No encontrado");			
		}		
		return temp;
	}
	
	
	/**
	 * Retorna un afiliado con base es sus credenciales de acceso
	 * al perfil web
	 * @param documento
	 * @param clave
	 * @return
	 */
	public Afiliado obtenerAfiliadoByCredenciales(String documento, String clave){	
		System.out.println("Buscando Afiliado por credenciales...");
		
		Query query = em.createQuery("FROM Afiliado a WHERE a.documento = :docu and a.claveweb = :clave");
		query.setParameter("docu", documento);
		query.setParameter("clave", clave);
		Afiliado temp = null;	
		try { 
			temp = (Afiliado) query.getSingleResult();
			
		} catch (NoResultException e) {
			System.out.println("Afiliado no encontrado");			
		}		
		return temp;
	}	
	
	
	public Afiliado obtenerAfiliadoById(Integer id){	
		System.out.println("Buscando Afiliado por id...");
		
		Query query = em.createQuery("FROM Afiliado a WHERE a.idafiliado = :id");
		query.setParameter("id", id);
		Afiliado temp = null;	
		try { 
			temp = (Afiliado) query.getSingleResult();
			
		} catch (NoResultException e) {
			System.out.println("Afiliado no encontrado");			
		}		
		return temp;
	}	
	
	
	/**
	 * Retorna un afiliado recibiendo como parametro su documento
	 * @param documento
	 * @return
	 */
	//@TransactionAttribute(value=REQUIRES_NEW)
	public Afiliado obtenerAfiliadoByDocumento(String documento) {
		System.out.println(">>"+documento+"<<");
		Query query = em.createQuery("FROM Afiliado a WHERE a.documento = :docu");
		query.setParameter("docu", documento);
		Afiliado temp = null;		
		try {
			temp = (Afiliado) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Afiliado "+ documento +" no encontrado");			
		}		
		return temp;
	}
	
	
	/**
	 * Retorna un List de afiliados con base en un criterio, 
	 * que es buscado, en documento, nombres, apellidos o email	
	 * @param criterio
	 * @return
	 */
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
	
	
	/**
	 * Retorna un afiliado con base en su nacionalidad
	 * Recibe como parametro una instancia de este objeto
	 * @param instance
	 * @return
	 */
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
	
	
	/**
	 * Retorna un afiliado con base en su documento y nacionalidad
	 * @param documento
	 * @param nacionalidad
	 * @return
	 */
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
	
	
	/**
	 * Retorna un objeto afiliado con base en su codigo UUID	
	 * @param uuid
	 * @return
	 */
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
	
	/**
	 * Retorna una instancia de afiliado con base en un email
	 * @param email
	 * @return
	 */
	public Afiliado obtenerAfiliadoByEmail(String email)  {
		Query query = em.createQuery("FROM Afiliado a WHERE a.email = :email");
		query.setParameter("email", email);
		Afiliado temp = null;		
		try {
			temp = (Afiliado) query.getSingleResult();
			
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");
			throw new NoResultException("No hay ningun afiliado con esta cuenta de correo.");
		} catch (NonUniqueResultException e){
			throw new NonUniqueResultException("Hay mas de un afiliado con esta cuenta de correo.");
		}		
		return temp;
		
	}
	
	
	/**
	 * Retorn un List de afiliados, registrados los ultimos 7 dias
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Afiliado> obtenerUltimosAfiliadosRegistrados(){
		//resta 7 dias a la fecha actual
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		Date menosSiete = cal.getTime();
		
		Query query = em.createQuery("SELECT a FROM Afiliado a where date(a.momento) >= :menossiete order by a.idafiliado desc");	
		query.setParameter("menossiete", menosSiete);
		List<Afiliado> temp = null;
		try {
			temp =  query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Ultimos afiliados no encontrados");			
		}		
		return temp;
	}	

	
	/**
	 * Retorna un entero con el total de afiliados registrados
	 * @return
	 */
	public Integer totalAfiliados(){
		Query query = em.createQuery("SELECT COUNT(a.idafiliado) FROM Afiliado a");		
		Long temp = null;		
		try {
			temp =  (Long) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");
		}		
		return temp.intValue(); 
	}	
	
	
	/**
	 * Retorna un entero con el total de afiliados que 
	 * tienen el correo validado
	 * @return
	 */
	public Integer totalAfiliadosCorreoValidado(){
		Query query = em.createQuery("SELECT COUNT(a.idafiliado) FROM Afiliado a where a.emailvalidado = 1 ");		
		Long temp = null;		
		try {
			temp =  (Long) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado");
		}		
		return temp.intValue(); 
	}
	
	
	/**
	 * Retorna un entero con el total de afiliados con el 
	 * correo rechazado
	 * @return
	 */
	public Integer totalAfiliadosCorreoRechazado(){
		Query query = em.createQuery("SELECT COUNT(a.idafiliado) FROM Afiliado a where a.emailrechazado = 1 ");		
		Long temp = null;		
		try {
			temp =  (Long) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado email rechazado general");
		}		
		return temp.intValue(); 
	}
	
	/**
	 * Retorna un entero con el total de afiliados con el correo sin validar
	 * pero que no son rechazados
	 * @return
	 */
	public Integer totalAfiliadosCorreoSinValidar(){
		Query query = em.createQuery("SELECT COUNT(a.idafiliado) FROM Afiliado a "
				+ "where a.emailrechazado = 0 and a.emailvalidado = 0 and a.email != '' ");		
		Long temp = null;		
		try {
			temp =  (Long) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Elemento no encontrado email rechazado general");
		}		
		return temp.intValue(); 
	}
	
	
	/**
	 * Retorna una list de afiliados sin codigo UUID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Afiliado> obtenerAfiliadosSinUUID(){
		String queryString = "SELECT a FROM Afiliado a where a.keycode is null";	
		
		System.out.println("QueryString:" + queryString);
		Query query = em.createQuery(queryString);
		return query.getResultList();			
	}


	
	/**
	 * Retorna un List de afiliados que cumplen anios en la fecha actual
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Afiliado> obtenerAfiliadosPorCumpleanos(){		
		
		List<Afiliado> cumpleList = new ArrayList<Afiliado>();		
		
		String queryString = "select a from Afiliado a where "
				+ "month(a.fechanacimiento) = month(current_date()) and "
				+ "day(a.fechanacimiento) = day(current_date()) order by a.nombres, a.apellidos asc";	
		
		System.out.println("QueryString:" + queryString);
		
		Query query = em.createQuery(queryString);
		
		try {
			cumpleList = query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Cumpleneros no encontrados");
		}
		return cumpleList;
	}
	
	
	/**
	 * Retorna un List de afiliados que cumplen anios en la fecha actual
	 * con numero de celular valido
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Afiliado> obtenerAfiliadosCumpleanosCelular(){		
		
		List<Afiliado> cumpleList = this.obtenerAfiliadosPorCumpleanos();
		List<Afiliado> smsList = new ArrayList<Afiliado>();
		
		for( Afiliado af:  cumpleList){
			
			String nroCel = !af.getCelular().equals("") ? af.getCelular() : "" ;
			
			//validacion sencilla del nro celular
			if( nroCel.length() == 10 &&  nroCel.substring(0,1).equals("3")){
				smsList.add( af );
			}
		}
		return smsList;
	}
	
	
	
	/**
	 * Actualiza la edad de una coleccion de afiliados
	 * @param cumpleanosList
	 */
	public void actualizarCumpleanosList( List<Afiliado> cumpleanosList ){
		
		int edadActual = 0;
		
		for( Afiliado el : cumpleanosList ){
			edadActual  = this.getAge(el.getFechanacimiento());
			el.setEdad(edadActual);
			this.updateAfiliado(el);
		}
		
	}
	
	
	

	// ======================================
    // =            Utilidades              =
    // ======================================
	
	/**
	 * Metodo de utilidad para obtener la edad a 
	 * partir de un objeto date 
	 * @param dateOfBirth
	 * @return
	 */
	public int getAge(Date dateOfBirth) {
	    Calendar today = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();
	    birthDate.setTime(dateOfBirth);
	    if (birthDate.after(today)) {
	        throw new IllegalArgumentException("You don't exist yet");
	    }
	    int todayYear = today.get(Calendar.YEAR);
	    int birthDateYear = birthDate.get(Calendar.YEAR);
	    int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
	    int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
	    int todayMonth = today.get(Calendar.MONTH);
	    int birthDateMonth = birthDate.get(Calendar.MONTH);
	    int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
	    int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
	    int age = todayYear - birthDateYear;

	    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
	    if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)){
	        age--;
	    
	    // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
	    } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)){
	        age--;
	    }
	    return age;
	}
	
	
	
	
	
	
}
