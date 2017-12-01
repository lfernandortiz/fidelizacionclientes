package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Estudioafiliado;
import com.dromedicas.domain.Estudioafiliado;

public interface EstudioAfiliadoDao {
	
public List<Estudioafiliado> findAllEstudioafiliados();
	
	public Estudioafiliado obtenerEstudioafiliadoById(Estudioafiliado instance);
	
	public void insertEstudioafiliado(Estudioafiliado instance);
	
	public void updateEstudioafiliado(Estudioafiliado instance);
	
	public void deleteEstudioafiliado(Estudioafiliado instance);

}
