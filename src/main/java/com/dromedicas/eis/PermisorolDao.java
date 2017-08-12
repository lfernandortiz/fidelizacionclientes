package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Permisorol;

public interface PermisorolDao {
	
	public List<Permisorol> findAllPermisorols();

	public Permisorol obtenerPermisorolById(Permisorol instance);

	public void insertPermisorol(Permisorol instance);

	public void updatePermisorol(Permisorol instance);

	public void deletePermisorol(Permisorol instance);

}
