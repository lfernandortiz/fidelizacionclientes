package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Afiliado;

public interface AfiliadoDao {
	
	public List<Afiliado> findAllAfiliados();
	
	public Afiliado obtenerAfiliadoById(Afiliado instance);
	
	public Afiliado obtenerAfiliadoByDocumento(Afiliado instance);
	
	public void insertAfiliado(Afiliado instance);
	
	public void updateAfiliado(Afiliado instance);
	
	public void deleteAfiliado(Afiliado instance);

}
