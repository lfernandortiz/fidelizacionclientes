package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Tiposm;

public interface TiposmDao {
	
	public List<Tiposm> findAllTiposms();

	public Tiposm obtenerTiposmById(Tiposm instance);

	public void insertTiposm(Tiposm instance);

	public void updateTiposm(Tiposm instance);

	public void deleteTiposm(Tiposm instance);
	

}
