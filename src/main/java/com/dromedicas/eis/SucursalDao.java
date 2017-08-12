package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Sucursal;

public interface SucursalDao {
	
	public List<Sucursal> findAllSucursals();

	public Sucursal obtenerSucursalById(String id);
	
	public Sucursal obtenerSucursalById(Sucursal instance);

	public void insertSucursal(Sucursal instance);

	public void updateSucursal(Sucursal instance);

	public void deleteSucursal(Sucursal instance);

}
