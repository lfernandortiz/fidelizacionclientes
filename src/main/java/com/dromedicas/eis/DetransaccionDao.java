package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Detransaccion;


public interface DetransaccionDao {
	
	public List<Detransaccion> findAllDetransaccions();
	
	public Detransaccion obtenerDetransaccionById(Detransaccion instance);
	
	public void insertDetransaccion(Detransaccion instance);
	
	public void updateDetransaccion(Detransaccion instance);
	
	public void deleteDetransaccion(Detransaccion instance);

}
