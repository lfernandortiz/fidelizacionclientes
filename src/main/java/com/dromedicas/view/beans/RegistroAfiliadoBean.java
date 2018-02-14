package com.dromedicas.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

@ManagedBean(name="registroAfiliadoBean")
@ViewScoped
public class RegistroAfiliadoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private Reporteador report;
	
	private String nombreInforme;
	private Date fechaInicio = new Date();
	private Date fechaFin = new Date();
	
	private List<Object[]> afListVendedor;
	private List<Object[]> afListSucursal;
	
	private int totalRegistrados;
	private int totalEmail;
	private int totalValidado;
	private int totalRechazados;
	
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
			if( this.getNombreInforme().equals("vendedor")){
				this.obtenerPorVendedor();				
			}
			if( this.getNombreInforme().equals("sucursal")){
				System.out.println("-----PreRender View sucursal");
				this.obtenerPorSucursal();
			}
		}		
	}
	
	public void cancelarList(){
		this.setFechaInicio(new Date());
		this.setFechaFin(new Date());
		
		if( this.getNombreInforme().equals("vendedor")){
			this.afListVendedor = null;
			this.resetTotales();
		}
		if( this.getNombreInforme().equals("sucursal")){
			this.afListVendedor = null;
			this.resetTotales();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void obtenerPorVendedor(){
		this.resetTotales();
		String queryString = "SELECT vendedor.codvende, "+
							"concat(vendedor.nombres,' ',vendedor.apellidos), "+
							"count(documento), sum(case when afiliado.email != '' then 1 else 0 end) , "+
							"sum(case when afiliado.emailvalidado = 1 then 1 else 0 end) , " +
							"sum(case when afiliado.emailrechazado = 1 then 1 else 0 end) " +
							"FROM afiliado inner join vendedor on ( afiliado.codvende = vendedor.codvende ) "+ 
							"where  afiliado.codvende is not null  " ;
		
		
		if( this.getFechaInicio() != null && this.fechaFin != null ){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			queryString += "and date(afiliado.momento) >= '" + sdf.format(this.getFechaInicio())  + "' and " + 
							" date(afiliado.momento)  <= '" + sdf.format(this.getFechaFin()) + "' ";
		}
		
		queryString += " group by 1 order by 3 desc ";
		
		
		System.out.println("-----QueryString " + queryString);
		
		try {
			 Query query = em.createNativeQuery(queryString);
			 afListVendedor = query.getResultList();
			 
			 //Establece los valores totales que aparecen en la vista.
			 this.establecerTotales(afListVendedor);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void obtenerPorSucursal(){
		this.resetTotales();
		String queryString = "SELECT sucursal.codigointerno, sucursal.nombre_sucursal as sucursal," +
							 "count(documento) as registrados, sum(case when afiliado.email != '' then 1 else 0 end) as email, "+
							 "sum(case when afiliado.emailvalidado = 1 then 1 else 0 end) as emailvalidado, " +
							 "sum(case when afiliado.emailrechazado = 1 then 1 else 0 end) as emailrechazado " +
							 "FROM afiliado inner join sucursal on ( afiliado.idsucursal = sucursal.idsucursal) " +
							 "WHERE afiliado.codvende IS NOT NULL ";
						
		
		if( this.getFechaInicio() != null && this.fechaFin != null ){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			queryString += "and date(afiliado.momento) >= '" + sdf.format(this.getFechaInicio())  + "' and " + 
							" date(afiliado.momento)  <= '" + sdf.format(this.getFechaFin()) + "' ";
		}
		
		queryString += " group by 1 order by 3 desc";
		
		System.out.println("-----QueryString " + queryString);
		
		
		try {
			 Query query = em.createNativeQuery(queryString);
			 afListSucursal = query.getResultList();
			 this.establecerTotales(afListSucursal);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void resetTotales(){
		this.setTotalRegistrados(0);
		this.setTotalEmail(0);
		this.setTotalValidado(0);			
		this.setTotalRechazados(0);
		
	}
	
	
	
	private void establecerTotales(List<Object[]> list){
		if( this.getNombreInforme().equals("vendedor")){
			for(Object[] e: list){	
				 BigInteger reg = (BigInteger) e[2];
				 this.totalRegistrados += reg.intValue() ;
				 BigDecimal em = (BigDecimal) e[3];
				 this.totalEmail += em.intValue() != 0 ? em.intValue() : 0;
				 BigDecimal va = (BigDecimal) e[4];
				 this.totalValidado += va.intValue() != 0 ? va.intValue() : 0;
				 BigDecimal rec = (BigDecimal) e[5];
				 this.totalRechazados += rec.intValue() != 0 ? rec.intValue() : 0;
				 
			}
		}
		if( this.getNombreInforme().equals("sucursal")){
			for(Object[] e: list){					
				 BigInteger reg = (BigInteger) e[2];
				 this.totalRegistrados += reg.intValue() ;
				 BigDecimal em = (BigDecimal) e[3];
				 this.totalEmail += em.intValue() != 0 ? em.intValue() : 0;
				 BigDecimal va = (BigDecimal) e[4];
				 this.totalValidado += va.intValue() != 0 ? va.intValue() : 0;
				 BigDecimal rec = (BigDecimal) e[5];
				 this.totalRechazados += rec.intValue() != 0 ? rec.intValue() : 0;
			}
		}
	}
	
	
	
	/**
	 * Exporta a excel la consulta actual afiliados registrados por 
	 * vendedores
	 */
	public void exportarExcelVendedores(){				
	    
	    try {
	    	report.generarReporteExcelElipsis("registroafiliadovendedor", this.getFechaInicio(), this.getFechaFin());
			
		} catch (Exception e) {
	    	System.out.println("Error al exportar");
	    	e.printStackTrace();
		}		
	}
	
	/**
	 * Exporta la consulta acutla de afiliados registrados por
	 * sucursal
	 */
	public void exportarExcelSucursal(){				
	    
	    try {
	    	report.generarReporteExcelElipsis("registroafiliadosucursal", this.getFechaInicio(), this.getFechaFin());
			
		} catch (Exception e) {
	    	System.out.println("Error al exportar");
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

	public List<Object[]> getAfListVendedor() {
		return afListVendedor;
	}

	public void setAfListVendedor(List<Object[]> afListVendedor) {
		this.afListVendedor = afListVendedor;
	}

	public List<Object[]> getAfListSucursal() {
		return afListSucursal;
	}
	

	public void setAfListSucursal(List<Object[]> afListSucursal) {
		this.afListSucursal = afListSucursal;
	}

	public int getTotalRegistrados() {
		return totalRegistrados;
	}

	public void setTotalRegistrados(int totalRegistrados) {
		this.totalRegistrados = totalRegistrados;
	}

	public int getTotalEmail() {
		return totalEmail;
	}

	public void setTotalEmail(int totalEmail) {
		this.totalEmail = totalEmail;
	}

	public int getTotalValidado() {
		return totalValidado;
	}

	public void setTotalValidado(int totalValidado) {
		this.totalValidado = totalValidado;
	}

	public int getTotalRechazados() {
		return totalRechazados;
	}

	public void setTotalRechazados(int totalRechazados) {
		this.totalRechazados = totalRechazados;
	}
	
	
	

}
