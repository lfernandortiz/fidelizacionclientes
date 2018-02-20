package com.dromedicas.view.beans;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.reportes.Reporteador;
import com.dromedicas.service.EmpresaService;

@ManagedBean(name="informePuntosBean")
@ViewScoped
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
	
	@PostConstruct
	public void init(){		
		System.out.println("-----Post Construct");
		
		try {
			this.fechaIniPuntos = new SimpleDateFormat("yyyy-MM-dd").parse("2017-09-01");
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
			if( this.getNombreInforme().equals("")){
				
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
		if( this.getNombreInforme().equals("")){			
			
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
		if( this.getNombreInforme().equals("")){
			for(Object[] e: list){					
				 
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
	
	
	
	
	

}
