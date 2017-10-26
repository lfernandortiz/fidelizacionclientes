package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Referido;

public interface ReferidoDao {
	
	public List<Referido> findAllReferidos();
	
	public Referido obtenerReferidoById(Referido instance);
	
	public Referido obtenerReferidoPorEmail(String emial);
	
	public void insertReferido(Referido instance);
	
	public void updateReferido(Referido instance);
	
	public void deleteReferido(Referido instance);

}
