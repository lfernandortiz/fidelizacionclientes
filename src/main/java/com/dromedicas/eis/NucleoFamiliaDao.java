package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Nucleofamilia;

public interface NucleoFamiliaDao {
	
	public List<Nucleofamilia> findAllNucleofamilias();
	
	public Nucleofamilia obtenerNucleofamiliaById(Nucleofamilia instance);
	
	public void insertNucleofamilia(Nucleofamilia instance);
	
	public void updateNucleofamilia(Nucleofamilia instance);
	
	public void deleteNucleofamilia(Nucleofamilia instance);	

}
