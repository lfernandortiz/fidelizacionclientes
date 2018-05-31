package com.dromedicas.view.beans;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import com.dromedicas.reportes.Reporteador;
import com.dromedicas.service.EmpresaService;

@ManagedBean(name="informePuntosBean")
@SessionScoped
public class InformespuntosBean {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private Reporteador report;
	
	@EJB
	private EmpresaService empresaService;
	
	private String nombreInforme;
	private Date fechaInicio = new Date();
	private Date fechaIniPuntos ;
	private Date fechaFin = new Date();
	private String totalRedimidos;
	private String totalAcumulados;
	
	private List<Object[]> redimidosList;
	private List<Object[]> redimidosSucursalList;
	private List<Object[]> redimidosDetalladoList;
	
	
	
	@PostConstruct
	public void init(){		
		System.out.println("-----Post Construct");
		
		try {
			this.fechaIniPuntos = new SimpleDateFormat("yyyy-MM-dd").parse("2017-09-01");
		    this.resetTotales();
		    this.redimidosDetalladoList = null;
		    this.redimidosList = null;
			
		} catch (Exception e) {
			System.out.println("ERROR AL ESTABLECER LA FECHA INICIO");
			e.printStackTrace();
		}
	}
	
	private boolean isPostBack(){
		return FacesContext.getCurrentInstance().isPostback();
	}
	
	
	public void establecerInforme(String informe){
		System.out.println("-----PreRender View");
		
		if( this.isPostBack() == false){
			this.setNombreInforme(informe);
			if( this.getNombreInforme().equals("redimidos")){
				this.puntosRedimidosAfiliados();	
			}
			if( this.getNombreInforme().equals("redimidosdetallado")){
				this.puntosRedimidosDetallado();
				
			}
		}		
	}
	
	public void cancelarList(){
		this.setFechaInicio(new Date());
		this.setFechaFin(new Date());
		
		if( this.getNombreInforme().equals("redimidos")){
			this.resetTotales();
			this.redimidosList = null;
		}
		if( this.getNombreInforme().equals("redimidosdetallado")){			
			this.resetTotales();
			this.redimidosDetalladoList = null;
		}
		
		if( this.getNombreInforme().equals("detalladosucursal")){			
			this.resetTotales();
			this.redimidosDetalladoList = null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void puntosRedimidosAfiliados(){
		//this.resetTotales();
		String queryString = 	
			"SELECT afiliado.documento, concat( afiliado.nombres, ' ' , afiliado.apellidos), "+
			"CASE WHEN sucursal.nombre_sucursal = 'DROMEDICAS DEL ORIENTE' THEN 'PAGINA WEB' else sucursal.nombre_sucursal end, " +
			"SUM( CASE WHEN 	transaccion.`tipotx` = 1 or transaccion.`tipotx` = 4 or transaccion.`tipotx` = "+
			"5 then transaccion.`puntostransaccion` else 0  end) AS PUNTOS_ACUMULADOS, "+
			"sum( case when (transaccion.tipotx = 2 ) then transaccion.puntostransaccion else 0 end ) as redimidos "+  
			"FROM transaccion INNER JOIN afiliado ON (transaccion.idafiliado = afiliado.idafiliado) "+
			"INNER JOIN sucursal ON (afiliado.idsucursal = sucursal.idsucursal) WHERE " +
			"1 = 1 " ;
		
		if( this.getFechaInicio() != null && this.fechaFin != null ){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			queryString += "and date(transaccion.fechatransaccion) >= '" + sdf.format(this.getFechaIniPuntos())  + "' and " + 
							" date(transaccion.fechatransaccion)  <= '" + sdf.format(this.getFechaFin()) + "' ";
		}		
		queryString += " GROUP BY afiliado.documento HAVING redimidos > 0 ORDER BY 5 desc ";		
		
		System.out.println("-----QueryString " + queryString);
		
		try {
			 Query query = em.createNativeQuery(queryString);
			 this.redimidosList = query.getResultList();
			 
			 //Establece los valores totales que aparecen en la vista.
			 this.establecerTotales(redimidosList);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void puntosRedimidosDetallado(){
		//this.resetTotales();
		String queryString = 	
			"SELECT  transaccion.fechatransaccion,  afiliado.documento, concat( afiliado.nombres, ' ' , afiliado.apellidos), " +
			"sucursal.nombre_sucursal, transaccion.nrofactura,  " +
			"CASE WHEN ticketredencion.idticketredencion IS NULL THEN 'NO' ELSE 'SI' END as Ticket, transaccion.puntostransaccion "+
			"FROM transaccion INNER JOIN afiliado ON (transaccion.idafiliado = afiliado.idafiliado) "+
			"INNER JOIN sucursal ON (transaccion.idsucursal = sucursal.idsucursal) "+
			"LEFT JOIN ticketredencion ON ( transaccion.idtransaccion = ticketredencion.idtransaccion) "+
			"WHERE  transaccion.tipotx = 2 " ;
			
			
		if( this.getFechaInicio() != null && this.fechaFin != null ){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			queryString += "and date(transaccion.fechatransaccion) >= '" + sdf.format(this.getFechaIniPuntos())  + "' and " + 
							" date(transaccion.fechatransaccion)  <= '" + sdf.format(this.getFechaFin()) + "' ";
		}		
		queryString += " order by 1 asc";		
		
		System.out.println("-----QueryString " + queryString);
		
		try {
			 Query query = em.createNativeQuery(queryString);
			 this.redimidosDetalladoList = query.getResultList();
			 
			 //Establece los valores totales que aparecen en la vista.
			 this.establecerTotales(redimidosDetalladoList);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void detalladoSucursal(){
		//this.resetTotales();
		String queryString = 	
				"SELECT sucursal.idsucursal, sucursal.nombre_sucursal, " +
				"(SUM( CASE WHEN (t.tipotx <> 2  and t.tipotx <> 3 and  DATE( t.vencen ) >= CURRENT_DATE  and t.redimidos = 0 )then t.puntostransaccion else 0  end) +  " +
				"SUM( CASE WHEN (t.tipotx <> 2  and t.tipotx <> 3 and DATE( t.vencen) >= CURRENT_DATE  and t.redimidos = 1 ) then t.saldo else 0  end) - " +
				"SUM( CASE WHEN (t.tipotx = 3 ) then t.puntostransaccion else 0  end)) AS ACUMULADOS, " + 
				"SUM( CASE WHEN t.tipotx = 2  then t.puntostransaccion else 0 end ) as REDIMIDOS  " +
				"FROM transaccion t LEFT OUTER JOIN sucursal ON (t.idsucursal = sucursal.idsucursal) " +
				"INNER JOIN tipotransaccion ON (t.tipotx = tipotransaccion.idtipotransaccion) " +
				"WHERE 1 = 1 "; 				 
		
		if( this.getFechaInicio() != null && this.fechaFin != null ){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			queryString += "and DATE( t.fechatransaccion)  >= '" + sdf.format(this.getFechaIniPuntos())  + "' and " + 
							" DATE( t.fechatransaccion )  <= '" + sdf.format(this.getFechaFin()) + "' ";
		}		
		queryString += " GROUP BY sucursal.idsucursal order by 2 ASC ";			
		System.out.println("-----QueryString " + queryString);
		
		try {
			 Query query = em.createNativeQuery(queryString);
			 this.redimidosSucursalList = query.getResultList();
			 
			 //Establece los valores totales que aparecen en la vista.
			 this.establecerTotales(redimidosSucursalList);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	
	private void establecerTotales(List<Object[]> list){
		if( this.getNombreInforme().equals("redimidos")){
			int acuTemp = 0;
			int redTemp = 0;
			
			for(Object[] e: list){	
				 BigDecimal acu = (BigDecimal) e[3];
				 acuTemp += acu.intValue() ;
				 BigDecimal red = (BigDecimal) e[4];
				 redTemp += red.intValue();	
			}
			
			this.setTotalAcumulados(new DecimalFormat("#,###").format(acuTemp));
			this.setTotalRedimidos(new DecimalFormat("#,###").format(redTemp));
		}
		
		if( this.getNombreInforme().equals("redimidosdetallado")){			
			int redTemp = 0;
			for(Object[] e: list){	
				Integer red = (Integer) e[6];
				redTemp += red.intValue();	
				this.setTotalRedimidos(new DecimalFormat("#,###").format(redTemp));
			}
		}
		
		
	}
	
	private void resetTotales(){
		this.setTotalAcumulados("");
		this.setTotalRedimidos("");
		
	}
	

	public void exportarExcelRedimidos(){
		try {
	    	report.generarReporteExcelElipsis("clpuntosredimidos", this.getFechaIniPuntos(), this.getFechaFin());
			
		} catch (Exception e) {
	    	System.out.println("Error al exportar informe de puntos redimidos");
	    	e.printStackTrace();
		}	
	}
	
	
	public void exportarExcelRedimidosDetallado(){
		try {
	    	report.generarReporteExcelElipsis("redimidosdetalle", this.getFechaIniPuntos(), this.getFechaFin());
			
		} catch (Exception e) {
	    	System.out.println("Error al exportar informe de puntos redimidos detallado");
	    	e.printStackTrace();
		}	
	}
	
	public String getNombreInforme() {
		return nombreInforme;
	}

	public void setNombreInforme(String nombreInforme) {
		this.nombreInforme = nombreInforme;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Object[]> getRedimidosList() {
		return redimidosList;
	}

	public void setRedimidosList(List<Object[]> redimidosList) {
		this.redimidosList = redimidosList;
	}

	public String getTotalRedimidos() {
		return totalRedimidos;
	}

	public void setTotalRedimidos(String totalRedimidos) {
		this.totalRedimidos = totalRedimidos;
	}

	public String getTotalAcumulados() {
		return totalAcumulados;
	}

	public void setTotalAcumulados(String totalAcumulados) {
		this.totalAcumulados = totalAcumulados;
	}

	public Date getFechaIniPuntos() {
		return fechaIniPuntos;
	}

	public void setFechaIniPuntos(Date fechaIniPuntos) {
		
		if( fechaIniPuntos.compareTo(this.fechaIniPuntos) != 0){
			this.fechaIniPuntos = fechaIniPuntos;
		}	
	}

	public List<Object[]> getRedimidosDetalladoList() {
		return redimidosDetalladoList;
	}

	public void setRedimidosDetalladoList(List<Object[]> redimidosDetalladoList) {
		this.redimidosDetalladoList = redimidosDetalladoList;
	}

	public List<Object[]> getRedimidosSucursalList() {
		return redimidosSucursalList;
	}

	public void setRedimidosSucursalList(List<Object[]> redimidosSucursalList) {
		this.redimidosSucursalList = redimidosSucursalList;
	}

	
	

}
