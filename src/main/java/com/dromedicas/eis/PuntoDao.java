package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Punto;

public interface PuntoDao {
	
	public List<Punto> findAllPuntos();

	public Punto obtenerPuntoById(Punto instance);

	public void insertPunto(Punto instance);

	public void updatePunto(Punto instance);

	public void deletePunto(Punto instance);

}
