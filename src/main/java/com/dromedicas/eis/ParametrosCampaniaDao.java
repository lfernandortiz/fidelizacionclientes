package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Paremetroscampania;

public interface ParametrosCampaniaDao {
	
	public List<Paremetroscampania> findAllParemetroscampanias();
	
	public Paremetroscampania obtenerParemetroscampaniaById(Paremetroscampania instance);
		
	public void insertParemetroscampania(Paremetroscampania instance);
	
	public void updateParemetroscampania(Paremetroscampania instance);
	
	public void deleteParemetroscampania(Paremetroscampania instance);

}
