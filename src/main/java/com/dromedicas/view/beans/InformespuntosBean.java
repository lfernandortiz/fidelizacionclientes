package com.dromedicas.view.beans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@ManagedBean(name="informePuntosBean")
@ViewScoped
public class InformespuntosBean {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	private String nombreInforme;
	private Date fechaInicio = new Date();
	private Date fechaFin = new Date();
	private int totalRedimidos;
	private int totalAcumulados;
	
	private List<Object[]> redimidosList;
	
	@PostConstruct
	public void init(){		
		System.out.println("-----Post Construct");
	}
	
	private boolean isPostBack(){
		return FacesContext.getCurrentInstance().isPostback();
	}
	
	
	public void establecerInforme(String informe){
		System.out.println("-----PreRender View");
		
		if( this.isPostBack() == false){
			this.setNombreInforme(informe);
			if( this.getNombreInforme().equals("")){
							
			}
			if( this.getNombreInforme().equals("")){
				
			}
		}		
	}
	
	public void cancelarList(){
		this.setFechaInicio(new Date());
		this.setFechaFin(new Date());
		
		if( this.getNombreInforme().equals("acumulados")){
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
			"afiliado.idafiliado IN ( SELECT distinct transaccion.idafiliado from transaccion where transaccion.tipotx = 2) " ;
		
		
		
		if( this.getFechaInicio() != null && this.fechaFin != null ){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			queryString += "and date(transaccion.fechatransaccion) >= '" + sdf.format(this.getFechaInicio())  + "' and " + 
							" date(transaccion.fechatransaccion)  <= '" + sdf.format(this.getFechaFin()) + "' ";
		}
		
		queryString += " GROUP BY afiliado.documento order by 5 desc ";
		
		
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
		if( this.getNombreInforme().equals("acumulados")){
			for(Object[] e: list){	
				 BigInteger acu = (BigInteger) e[3];
				 this.totalAcumulados += acu.intValue() ;
				 BigDecimal red = (BigDecimal) e[4];
				 this.totalRedimidos += red.intValue() != 0 ? red.intValue() : 0;				 
				 
			}
		}
		if( this.getNombreInforme().equals("")){
			for(Object[] e: list){					
				 
			}
		}
	}
	
	private void resetTotales(){
		this.setTotalAcumulados(0);
		this.setTotalRedimidos(0);
		
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

	public int getTotalRedimidos() {
		return totalRedimidos;
	}

	public void setTotalRedimidos(int totalRedimidos) {
		this.totalRedimidos = totalRedimidos;
	}

	public int getTotalAcumulados() {
		return totalAcumulados;
	}

	public void setTotalAcumulados(int totalAcumulados) {
		this.totalAcumulados = totalAcumulados;
	}
	
	
	
	

}
