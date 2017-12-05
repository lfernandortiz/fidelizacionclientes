package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Patologia;

public interface PatologiaDao {
	
	public List<Patologia> findAllPatologias();
	
	public Patologia obtenerPatologiaById(Patologia instance);
	
	public void insertPatologia(Patologia instance);
	
	public void updatePatologia(Patologia instance);
	
	public void deletePatologia(Patologia instance);

}
