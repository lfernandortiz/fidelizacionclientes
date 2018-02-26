package com.dromedicas.view.beans;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.OperacionPuntosService;

@ManagedBean(name="dashboardBean")
@ViewScoped
public class DashboardBean {
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	@EJB
	private OperacionPuntosService puntosService;
	
	@EJB
	private AfiliadoService afiliadoService;
	
	private String puntosAcumulados;
	private String puntosRedimidos;
	private String puntosAcumuladosTotal;
	private String totalAfiliados;
	private String totalEmailValidado;
	private String totalEmailRechazados;
	private String totalEmailSinValidar;
	
	private String totalAfiliaos;
	private String totalAcumulados;
	private String totalRedimidos;
	private String totalDevolucion;
	
	
	
	private List<Object[]> tableMainList;
	
	public DashboardBean(){
		
	}
	
	@PostConstruct
	public void init(){
		this.setDataTableMainList();
		
		
	}


	public String getPuntosAcumulados() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Acumulados que pueden ser redimidos.
		return nf.format(this.puntosService.getTotalPuntosDisponibles());
	}

	public void setPuntosAcumulados(String puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
	}

	public String getPuntosRedimidos() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Redimidos
		return nf.format(this.puntosService.getTotalPuntosRedemidos());
	}

	public void setPuntosRedimidos(String puntosRedimidos) {
		this.puntosRedimidos = puntosRedimidos;
	}
	
	public String getPuntosAcumuladosTotal() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Total puntos acumulados
		return nf.format(this.puntosService.getTotalPuntosAcumulados());
	}

	public void setPuntosAcumuladosTotal(String puntosAcumuladosTotal) {
		this.puntosAcumuladosTotal = puntosAcumuladosTotal;
	}

	public String getTotalAfiliados() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Redimidos por los afiliados
		return nf.format(this.afiliadoService.totalAfiliados());
	}

	public void setTotalAfiliados(String totalAfiliados) {
		this.totalAfiliados = totalAfiliados;
	}

	public String getTotalEmailValidado() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Total Email Validados
		return nf.format(this.afiliadoService.totalAfiliadosCorreoValidado());
	}

	public void setTotalEmailValidado(String totalEmailValidado) {
		this.totalEmailValidado = totalEmailValidado;
	}

	public String getTotalEmailRechazados() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Total Email Rechazados
		return nf.format(this.afiliadoService.totalAfiliadosCorreoRechazado());
	}

	public void setTotalEmailRechazados(String totalEmailRechazados) {
		this.totalEmailRechazados = totalEmailRechazados;
	}

	public String getTotalEmailSinValidar() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Total Email sin validar
		return nf.format(this.afiliadoService.totalAfiliadosCorreoSinValidar());
	}

	public void setTotalEmailSinValidar(String totalEmailSinValidar) {
		this.totalEmailSinValidar = totalEmailSinValidar;
	}

	public List<Object[]> getTableMainList() {
		return tableMainList;
	}

	public void setTableMainList(List<Object[]> tableMainList) {
		this.tableMainList = tableMainList;
	}


	public String getTotalAfiliaos() {
		return totalAfiliaos;
	}

	public void setTotalAfiliaos(String totalAfiliaos) {
		this.totalAfiliaos = totalAfiliaos;
	}

	public String getTotalAcumulados() {
		return totalAcumulados;
	}

	public void setTotalAcumulados(String totalAcumulados) {
		this.totalAcumulados = totalAcumulados;
	}

	public String getTotalRedimidos() {
		return totalRedimidos;
	}

	public void setTotalRedimidos(String totalRedimidos) {
		this.totalRedimidos = totalRedimidos;
	}

	public String getTotalDevolucion() {
		return totalDevolucion;
	}

	public void setTotalDevolucion(String totalDevolucion) {
		this.totalDevolucion = totalDevolucion;
	}
	
	
	public void setDataTableMainList(){
		//this.resetTotales();
		String queryString =
			"select totales.nombre, SUM(totales.afiliados), SUM(totales.acumulados), SUM(totales.redimidos), SUM(totales.devoluciones) " +
			"from (	select sucursal.nombre_sucursal as nombre, count( afiliado.documento ) as afiliados, 0 as acumulados, 0 as redimidos, "+
			"0 as devoluciones from sucursal inner join afiliado on ( afiliado.idsucursal = sucursal.idsucursal ) group by sucursal.nombre_sucursal "+ 
			"union all select sucursal.nombre_sucursal as nombre, 0 as afiliados, " +
			"sum( case when transaccion.tipotx = 1 or transaccion.tipotx = 4 or transaccion.tipotx = 5 then transaccion.puntostransaccion else 0 end ) "+
			"as acumulados, 0 as redimidos, 0 as devoluciones from sucursal inner join transaccion on ( transaccion.idsucursal = sucursal.idsucursal ) "+
			"group by sucursal.nombre_sucursal union all select sucursal.nombre_sucursal as nombre, 0 as afiliados, 0 as acumulados, "+
			"sum( case when transaccion.tipotx = 2  then transaccion.puntostransaccion else 0 end ) as redimidos, 0 as devoluciones from sucursal "+
			"inner join transaccion on ( transaccion.idsucursal = sucursal.idsucursal ) group by sucursal.nombre_sucursal union all select " +
			"sucursal.nombre_sucursal as nombre, 0 as afiliados, 0 as acumulados, 0 as redimidos, "+
			"sum( case when transaccion.tipotx = 3  then transaccion.puntostransaccion else 0 end ) as devoluciones from sucursal " +
			"inner join transaccion on ( transaccion.idsucursal = sucursal.idsucursal ) group by sucursal.nombre_sucursal ) as totales 	group by totales.nombre ";
				
		
		System.out.println("-----QueryString " + queryString);
		
		try {
			 Query query = em.createNativeQuery(queryString);
			 this.tableMainList = query.getResultList();
			 
			 //Establece los valores totales que aparecen en la vista.
			 this.establecerTotales(tableMainList);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	private void establecerTotales(List<Object[]> list) {
		int afiTemp = 0;
		int acuTemp = 0;
		int redTemp = 0;
		int devTemp = 0;

		for (Object[] e : list) {
			BigDecimal af = (BigDecimal) e[1];
			afiTemp += af.intValue();
			BigDecimal acu = (BigDecimal) e[2];
			acuTemp += acu.intValue();
			BigDecimal red = (BigDecimal) e[3];
			redTemp += red.intValue();
			BigDecimal dev = (BigDecimal) e[4];
			devTemp += dev.intValue();
		}
		
		this.setTotalAfiliados(new DecimalFormat("#,###").format(afiTemp));
		this.setTotalAcumulados(new DecimalFormat("#,###").format(acuTemp));
		this.setTotalRedimidos(new DecimalFormat("#,###").format(redTemp));
		this.setTotalDevolucion(new DecimalFormat("#,###").format(devTemp));

	}
	
	
	
	

	
	

}
