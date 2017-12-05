package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Afiliadopatologianucleo;

public interface AfiliadoPatologiaNucleoDao {
	
	public List<Afiliadopatologianucleo> findAllAfiliadopatologianucleos();
	
	public Afiliadopatologianucleo obtenerAfiliadopatologianucleoById(Afiliadopatologianucleo instance);
	
	public void insertAfiliadopatologianucleo(Afiliadopatologianucleo instance);
	
	public void updateAfiliadopatologianucleo(Afiliadopatologianucleo instance);
	
	public void deleteAfiliadopatologianucleo(Afiliadopatologianucleo instance);

}
