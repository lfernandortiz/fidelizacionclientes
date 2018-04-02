package com.dromedicas.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;



@Stateless
public class UtilidadesBD {
	
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
	
	
	
	public Connection obtenerConnection() throws SQLException {
		Connection connection = null;
		
	
		try {
			
			connection = em.unwrap(SessionImpl.class).connection();

			System.out.println("--------->" + connection);
			
			
		} catch (Exception e) {
			System.out.println("XXXXXXXXXX ERROR AL OBTENER LA CONEXION");
			e.printStackTrace();
			
		}
		return connection;
	

	}
	
	
	public void mostrarFormatoPdf(String path, String doc) throws IOException{
		String DOWNLOAD_PATH = path+doc;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext(); 
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		
		
		File file = new File(DOWNLOAD_PATH);		

		if(file != null)//fileURL != null) 
		{
			byte[] fileData = new byte[(int)file.length()];
			FileInputStream inputStream = new FileInputStream(file);
			inputStream.read(fileData);
			ServletOutputStream os = response.getOutputStream();	
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachement; filename="+doc+"");
			int readBytes = 0;
			//read from the file; write to the ServletOutputStream
			int i = 0;
			os.write(fileData);
			os.close();
			facesContext.responseComplete();
		}	
	}
	
	public void mostrarFormatoExcel(String path, String doc) throws IOException{
		String DOWNLOAD_PATH = path+doc;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext(); 
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		
		
		File file = new File(DOWNLOAD_PATH);		

		if(file != null)//fileURL != null) 
		{
			
			try {
				byte[] fileData = new byte[(int)file.length()];
				
				FileInputStream inputStream = new FileInputStream(file);
				inputStream.read(fileData);
				ServletOutputStream os = response.getOutputStream();	
				response.setContentType("application/xls");
				response.setHeader("Content-Disposition", "attachement; filename="+doc+"");
				int readBytes = 0;
				//read from the file; write to the ServletOutputStream
				int i = 0;
				
				os.write(fileData);
			
				os.close();
				
				facesContext.responseComplete();
			} catch (Exception e) {
				System.out.println("-------------Error al exportar el informe");
				e.printStackTrace();
			}
		}	
	}
	

}
