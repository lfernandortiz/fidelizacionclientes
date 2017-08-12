package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Campania;

public interface CampaniaDao {
	
	public List<Campania> findAllCampanias();
	
	public Campania obtenerCampaniaById(Campania instance);
	
	public void insertCampania(Campania instance);
	
	public void updateCampania(Campania instance);
	
	public void deleteCampania(Campania instance);

}
