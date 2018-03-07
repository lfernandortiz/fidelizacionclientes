package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Patologiacampania;

public interface PatologiaCampaniaDao {
	
	public List<Patologiacampania> findAllPatologiacampanias();
	
	public Patologiacampania obtenerPatologiacampaniaById(Patologiacampania instance);
	
	public void insertPatologiacampania(Patologiacampania instance);
	
	public void updatePatologiacampania(Patologiacampania instance);
	
	public void deletePatologiacampania(Patologiacampania instance);

}
