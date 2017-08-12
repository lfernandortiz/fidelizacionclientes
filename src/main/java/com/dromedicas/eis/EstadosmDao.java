package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Estadosm;

public interface EstadosmDao {

	public List<Estadosm> findAllEstadosms();

	public Estadosm obtenerEstadosmById(Estadosm instance);

	public void insertEstadosm(Estadosm instance);

	public void updateEstadosm(Estadosm instance);

	public void deleteEstadosm(Estadosm instance);
}
