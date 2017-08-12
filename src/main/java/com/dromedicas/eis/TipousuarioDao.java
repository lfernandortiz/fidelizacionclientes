package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Tipousuario;

public interface TipousuarioDao {
	
	public List<Tipousuario> findAllTipousuarios();

	public Tipousuario obtenerTipousuarioById(Tipousuario instance);
	
	public Tipousuario obtenerTipousuarioById(int id);

	public void insertTipousuario(Tipousuario instance);

	public void updateTipousuario(Tipousuario instance);

	public void deleteTipousuario(Tipousuario instance);

}
