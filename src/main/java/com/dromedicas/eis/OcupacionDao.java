package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Ocupacion;

public interface OcupacionDao {
	
	public List<Ocupacion> findAllOcupacion();
	
	public Ocupacion obtenerOcupacionById(Ocupacion instance);
	
	public void insertOcupacion(Ocupacion instance);
	
	public void updateOcupacion(Ocupacion instance);
	
	public void deleteOcupacion(Ocupacion instance);

}
