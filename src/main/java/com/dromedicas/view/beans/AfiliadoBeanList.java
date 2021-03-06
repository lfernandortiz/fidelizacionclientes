package com.dromedicas.view.beans;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Empresa;
import com.dromedicas.reportes.Reporteador;
import com.dromedicas.service.AfiliadoService;

@ManagedBean(name="afiliadoBeanList")
@ViewScoped
public class AfiliadoBeanList implements Serializable{
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AfiliadoService afiliadoService; 
	
	@EJB
	private Reporteador report;
	
	@EJB
	private LoginBeanService loginService;
	
	
	private List<Afiliado> afiliadoList;
	private String valorABuscar; // permite buscar por cedula, nombre, apellido, email.
	private Integer totalAfiliados;
	
	public AfiliadoBeanList(){}// constructor por defecto
	
	@PostConstruct
	public void init(){
		System.out.println("Function PostConstruct");
		this.afiliadoList = afiliadoService.findAllAfiliadosMenor();		
		this.totalAfiliados = afiliadoService.totalAfiliados();
	}

	public List<Afiliado> getAfiliadoList() {
		return afiliadoList;
	}

	public void setAfiliadoList(List<Afiliado> afiliadoList) {
		this.afiliadoList = afiliadoList;
	}

	public String getValorABuscar() {
		return valorABuscar;
	}

	public void setValorABuscar(String valorABuscar) {
		this.valorABuscar = valorABuscar;
	}
	
	//metodos para botones del list
	
	public void cancelarList() {
		//Reset campos formulario de busqueda
		this.setValorABuscar("");

		// Reset DataTable Object (reestablecer la paginacion)
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formppal:afiliadoDetail");
		dataTable.reset();
		
		//Consulta nuevamente el List
		this.afiliadoList = this.afiliadoService.findAllAfiliadosMenor();
		this.totalAfiliados = this.afiliadoService.totalAfiliados();
	}
	
	public String crearAfiliado(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("afiliadoBeanEdit");
		return "afiliadocrear?faces-redirect=true";	
	}
	
	public void buscarAfiliado(){
		if( "".equals(this.valorABuscar) || " ".equals(this.valorABuscar) || this.valorABuscar == null ){
			this.afiliadoList = afiliadoService.findAllAfiliadosMenor();		
			this.totalAfiliados = afiliadoService.totalAfiliados();
		}else{
			this.afiliadoList = afiliadoService.bucarAfiliadoByFields(this.valorABuscar.replace("'", ""));
			this.totalAfiliados = this.afiliadoList.size();
		}
	}
	

	public Integer getTotalAfiliados() {
		return totalAfiliados;
	}

	public void setTotalAfiliados(Integer totalAfiliados) {
		this.totalAfiliados = totalAfiliados;
	}
	
	public void crearUUIDPendiente(){
		
		List<Afiliado> afiliadoListx = this.afiliadoService.obtenerAfiliadosSinUUID();
		System.out.println(!afiliadoListx.isEmpty());
		if( !afiliadoListx.isEmpty() ){
			for(Afiliado a: afiliadoListx){
				System.out.println("Afiliado documento: " + a.getDocumento());
				UUID uniqueKey = UUID.randomUUID(); // codigo usado para validaciones web
				a.setKeycode(uniqueKey.toString().replace("-", ""));
				this.afiliadoService.actualizarAfiliado(a);
			}
			FacesContext.getCurrentInstance().addMessage("localmessage", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Codigos UUID creados", "Se asignaron codigos UUID a los afiliados que no lo tenian"));
			
		}else{
			FacesContext.getCurrentInstance().addMessage("localmessage", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"No hay registros. ", " No hay afiliados pendientes de codigo UUID "));
		}
	}
	
	
	
	public void exportarExcelEmail(){
		try {
			
			System.out.println("Parametro 2: " + loginService.getNombreUsuario() );
			
			
	    	report.generarReporteExcelElipsis( "reporteafiliadosemail" );
			
		} catch (Exception e) {
	    	System.out.println("Error al exportar informe de puntos redimidos detallado");
	    	e.printStackTrace();
		}	
	}
	
	
	
}
