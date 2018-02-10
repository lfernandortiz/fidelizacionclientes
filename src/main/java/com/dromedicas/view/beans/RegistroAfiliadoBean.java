package com.dromedicas.view.beans;

import java.io.Serializable;
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

@ManagedBean(name="registroAfiliadoBean")
@ViewScoped
public class RegistroAfiliadoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	private String nombreInforme;
	private Date fechaInicio;
	private Date fechaFin;
	
	private List<Object[]> afListVendedor;
	
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
		}		
	}
	
	public void cancelarList(){
		this.setFechaInicio(null);
		this.setFechaFin(null);
		
		if( this.getNombreInforme().equals("vendedor")){
			this.afListVendedor = null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void obtenerPorVendedor(){
		String queryString = "SELECT vendedor.codvende, "+
							"concat(vendedor.nombres,' ',vendedor.apellidos), "+
							"count(documento), sum(case when afiliado.email != '' then 1 else 0 end) , "+
							"sum(case when afiliado.emailvalidado = 1 then 1 else 0 end) , " +
							"sum(case when afiliado.emailrechazado = 1 then 1 else 0 end) " +
							"FROM afiliado inner join vendedor on ( afiliado.codvende = vendedor.codvende ) "+ 
							"where  afiliado.codvende is not null  " ;
		
		
		if( this.getFechaInicio() != null && this.fechaFin != null ){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			queryString += "and afiliado.momento >= '" + sdf.format(this.getFechaInicio())  + "' and " + 
							" afiliado.momento  <= '" + sdf.format(this.getFechaFin()) + "' ";
		}
		
		queryString += " group by 1 order by 4 desc";
		
		
		System.out.println("---------" + queryString);
		
		
		try {
			 Query query = em.createNativeQuery(queryString);
			 afListVendedor = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportarExcelVendedores(){
		
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
	
	
	
	

}
