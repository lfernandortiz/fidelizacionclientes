package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Vendedor;

public interface VendedorDao {
	
	public List<Vendedor> findAllVendedors();

	public Vendedor obtenerVendedorById(Vendedor instance);

	public void insertVendedor(Vendedor instance);

	public void updateVendedor(Vendedor instance);

	public void deleteVendedor(Vendedor instance);

}
