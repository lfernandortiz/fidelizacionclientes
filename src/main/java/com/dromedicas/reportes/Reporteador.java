package com.dromedicas.reportes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.util.UtilidadesBD;
import com.dromedicas.view.beans.LoginBeanService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;


@Stateless
public class Reporteador {
	
	@EJB
	private UtilidadesBD udb;
	
	

	String path = "C:/ReportesGenerados/";

	public String generarReporteExcelElipsis(String nombreReporte, Object... params) {

		// Se establece el nombre del reporte
		// if(this.getNombrereporte() == null ){
		// this.setNombrereporte(nombreReporte);
		// }
		
		// Nombre y ubicacion del reporte a utlizar
		String documento = "/ReportesPuntosFarmanorte/" + nombreReporte + ".jasper";

		long inicio = System.currentTimeMillis();
		String doc = nombreReporte + inicio + ".xls";
		// Destino y nombre del reporte en el servidor
		String destFileNamePdf = path + doc;
		
		Map parameters = new HashMap();
		
		if( params.length != 0 ){			
			for (int i = 0; i < params.length; i++) {
				
				System.out.println("param" +  (i+1)  +": " + params[i]);
				parameters.put("param" + (i + 1), params[i]);
			}			
		}
		
		
		try {
			
			System.out.println("Creando Conexion...");
			Connection c = udb.obtenerConnection();
			
			System.out.println("Creando el reporte: " + documento);
			JasperPrint jasperPrint = JasperFillManager.fillReport(documento, parameters, c);
			
			System.out.println("Exportando el informe...");
			
			JRXlsExporter exporterXLS = new JRXlsExporter();

			exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,destFileNamePdf);
			exporterXLS.exportReport();	
			
			c.close();
			
			System.out.println("----Enviando MIME al cliente");
			udb.mostrarFormatoExcel(path, doc);//
			//this.setNombrereporte(null);
			System.out.println("----Abriendo informe");
			return destFileNamePdf;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		} catch (JRException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	
	
	
	
	
}
