package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Tipotransaccion;

public interface TipotransaccionDao {
	
	public List<Tipotransaccion> findAllTipotransaccions();

	public Tipotransaccion obtenerTipotransaccionById(Tipotransaccion instance);

	public void insertTipotransaccion(Tipotransaccion instance);

	public void updateTipotransaccion(Tipotransaccion instance);

	public void deleteTipotransaccion(Tipotransaccion instance);

}
