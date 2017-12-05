package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Tipomiembro;

public interface TipoMiembroDao {
	
	public List<Tipomiembro> findAllTipomiembros();
	
	public Tipomiembro obtenerTipomiembroById(Tipomiembro instance);
	
	public void insertTipomiembro(Tipomiembro instance);
	
	public void updateTipomiembro(Tipomiembro instance);
	
	public void deleteTipomiembro(Tipomiembro instance);

}
