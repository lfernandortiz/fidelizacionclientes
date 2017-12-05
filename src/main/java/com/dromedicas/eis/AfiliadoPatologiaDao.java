package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Afiliadopatologia;

public interface AfiliadoPatologiaDao {

	public List<Afiliadopatologia> findAllAfiliadopatologias();
	
	public Afiliadopatologia obtenerAfiliadopatologiaById(Afiliadopatologia instance);
	
	public void insertAfiliadopatologia(Afiliadopatologia instance);
	
	public void updateAfiliadopatologia(Afiliadopatologia instance);
	
	public void deleteAfiliadopatologia(Afiliadopatologia instance);
}
